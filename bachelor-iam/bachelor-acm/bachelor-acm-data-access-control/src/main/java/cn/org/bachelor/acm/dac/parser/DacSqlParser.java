package cn.org.bachelor.acm.dac.parser;

import cn.org.bachelor.acm.dac.util.StringUtil;
import com.sun.xml.internal.bind.v2.TODO;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;

import java.util.*;

/**
 * sql解析类，提供更智能的count查询sql
 *
 * @author liuzh
 */
public class DacSqlParser {
    private static final Alias TABLE_ALIAS;

    //<editor-fold desc="聚合函数">
    private final Set<String> skipFunctions = Collections.synchronizedSet(new HashSet<String>());
    private final Set<String> falseFunctions = Collections.synchronizedSet(new HashSet<String>());

    /**
     * 聚合函数，以下列函数开头的都认为是聚合函数
     */
    private static final Set<String> AGGREGATE_FUNCTIONS = new HashSet<String>(Arrays.asList(
            ("APPROX_COUNT_DISTINCT," +
            "ARRAY_AGG," +
            "AVG," +
            "BIT_," +
            "BOOL_," +
            "CHECKSUM_AGG," +
            "COLLECT," +
            "CORR," +
            "COUNT," +
            "COVAR," +
            "CUME_DIST," +
            "DENSE_RANK," +
            "EVERY," +
            "FIRST," +
            "GROUP," +
            "JSON_," +
            "LAST," +
            "LISTAGG," +
            "MAX," +
            "MEDIAN," +
            "MIN," +
            "PERCENT_," +
            "RANK," +
            "REGR_," +
            "SELECTIVITY," +
            "STATS_," +
            "STD," +
            "STRING_AGG," +
            "SUM," +
            "SYS_OP_ZONE_ID," +
            "SYS_XMLAGG," +
            "VAR," +
            "XMLAGG").split(",")));

    static {
        TABLE_ALIAS = new Alias("table_count");
        TABLE_ALIAS.setUseAs(false);
    }

    /**
     * 添加到聚合函数，可以是逗号隔开的多个函数前缀
     *
     * @param functions
     */
    public static void addAggregateFunctions(String functions){
        if(StringUtil.isNotEmpty(functions)){
            String[] funs = functions.split(",");
            for (int i = 0; i < funs.length; i++) {
                AGGREGATE_FUNCTIONS.add(funs[i].toUpperCase());
            }
        }
    }

    /**
     * 获取智能的countSql
     *
     * @param sql
     * @return
     */
    public String getSmartDacSql(String sql) {
        //解析SQL
        Statement stmt = null;
        try {
            stmt = CCJSqlParserUtil.parse(sql);
        } catch (Throwable e) {
            //无法解析的用一般方法返回count语句
            return getSimpleCountSql(sql);
        }
        Select select = (Select) stmt;
        SelectBody selectBody = select.getSelectBody();
        try {
            //处理body-去order by
            processSelectBody(selectBody);
        } catch (Exception e) {
            //当 sql 包含 group by 时，不去除 order by
            return getSimpleCountSql(sql);
        }
        //处理with-去order by
        processWithItemsList(select.getWithItemsList());
        //处理为count查询
        sqlToCount(select);
        String result = select.toString();
        return result;
    }

    /**
     * 获取普通的Count-sql
     *
     * @param sql 原查询sql
     * @return 返回count查询sql
     */
    //TODO 无法解析时候怎么办？
    public String getSimpleCountSql(final String sql) {
        StringBuilder stringBuilder = new StringBuilder(sql.length() + 40);
        stringBuilder.append("select count(");
        stringBuilder.append("0");
        stringBuilder.append(") from ( \n");
        stringBuilder.append(sql);
        stringBuilder.append("\n ) tmp_count");
        return stringBuilder.toString();
    }

    /**
     * 将sql转换为count查询
     *
     * @param select
     */
    public void sqlToCount(Select select) {
        SelectBody selectBody = select.getSelectBody();
        // 是否能简化count查询
        List<SelectItem> COUNT_ITEM = new ArrayList<SelectItem>();
        COUNT_ITEM.add(new SelectExpressionItem(new Column("count(0)")));
        if (selectBody instanceof PlainSelect && isSimpleCount((PlainSelect) selectBody)) {
            ((PlainSelect) selectBody).setSelectItems(COUNT_ITEM);
        } else {
            PlainSelect plainSelect = new PlainSelect();
            SubSelect subSelect = new SubSelect();
            subSelect.setSelectBody(selectBody);
            subSelect.setAlias(TABLE_ALIAS);
            plainSelect.setFromItem(subSelect);
            plainSelect.setSelectItems(COUNT_ITEM);
            select.setSelectBody(plainSelect);
        }
    }

    /**
     * 是否可以用简单的count查询方式
     *
     * @param select
     * @return
     */
    public boolean isSimpleCount(PlainSelect select) {
        //包含group by的时候不可以
        if (select.getGroupBy() != null) {
            return false;
        }
        //包含distinct的时候不可以
        if (select.getDistinct() != null) {
            return false;
        }
        //#606,包含having时不可以
        if (select.getHaving() != null) {
            return false;
        }
        for (SelectItem item : select.getSelectItems()) {
            //select列中包含参数的时候不可以，否则会引起参数个数错误
            if (item.toString().contains("?")) {
                return false;
            }
            //如果查询列中包含函数，也不可以，函数可能会聚合列
            if (item instanceof SelectExpressionItem) {
                Expression expression = ((SelectExpressionItem) item).getExpression();
                if (expression instanceof Function) {
                    String name = ((Function) expression).getName();
                    if (name != null) {
                        String NAME = name.toUpperCase();
                        if(skipFunctions.contains(NAME)){
                            //go on
                        } else if(falseFunctions.contains(NAME)){
                            return false;
                        } else {
                            for (String aggregateFunction : AGGREGATE_FUNCTIONS) {
                                if (NAME.startsWith(aggregateFunction)) {
                                    falseFunctions.add(NAME);
                                    return false;
                                }
                            }
                            skipFunctions.add(NAME);
                        }
                    }
                } else if (expression instanceof Parenthesis && ((SelectExpressionItem) item).getAlias() != null) {
                    //#555，当存在 (a+b) as c 时，c 如果出现了 order by 或者 having 中时，会找不到对应的列，
                    // 这里想要更智能，需要在整个SQL中查找别名出现的位置，暂时不考虑，直接排除
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 处理selectBody去除Order by
     *
     * @param selectBody
     */
    public void processSelectBody(SelectBody selectBody) {
        if (selectBody != null) {
            if (selectBody instanceof PlainSelect) {
                processPlainSelect((PlainSelect) selectBody);
            } else if (selectBody instanceof WithItem) {
                WithItem withItem = (WithItem) selectBody;
                if (withItem.getSubSelect() != null) {
                    processSelectBody(withItem.getSubSelect().getSelectBody());
                }
            } else {
                SetOperationList operationList = (SetOperationList) selectBody;
                if (operationList.getSelects() != null && operationList.getSelects().size() > 0) {
                    List<SelectBody> plainSelects = operationList.getSelects();
                    for (SelectBody plainSelect : plainSelects) {
                        processSelectBody(plainSelect);
                    }
                }
                if (!orderByHashParameters(operationList.getOrderByElements())) {
                    operationList.setOrderByElements(null);
                }
            }
        }
    }

    /**
     * 处理PlainSelect类型的selectBody
     *
     * @param plainSelect
     */
    public void processPlainSelect(PlainSelect plainSelect) {
        if (!orderByHashParameters(plainSelect.getOrderByElements())) {
            plainSelect.setOrderByElements(null);
        }
        if (plainSelect.getFromItem() != null) {
            processFromItem(plainSelect.getFromItem());
        }
        if (plainSelect.getJoins() != null && plainSelect.getJoins().size() > 0) {
            List<Join> joins = plainSelect.getJoins();
            for (Join join : joins) {
                if (join.getRightItem() != null) {
                    processFromItem(join.getRightItem());
                }
            }
        }
    }

    /**
     * 处理WithItem
     *
     * @param withItemsList
     */
    public void processWithItemsList(List<WithItem> withItemsList) {
        if (withItemsList != null && withItemsList.size() > 0) {
            for (WithItem item : withItemsList) {
                if (item.getSubSelect() != null) {
                    processSelectBody(item.getSubSelect().getSelectBody());
                }
            }
        }
    }

    /**
     * 处理子查询
     *
     * @param fromItem
     */
    public void processFromItem(FromItem fromItem) {
        if (fromItem instanceof SubJoin) {
            SubJoin subJoin = (SubJoin) fromItem;
            if (subJoin.getJoinList() != null && subJoin.getJoinList().size() > 0) {
                for (Join join : subJoin.getJoinList()) {
                    if (join.getRightItem() != null) {
                        processFromItem(join.getRightItem());
                    }
                }
            }
            if (subJoin.getLeft() != null) {
                processFromItem(subJoin.getLeft());
            }
        } else if (fromItem instanceof SubSelect) {
            SubSelect subSelect = (SubSelect) fromItem;
            if (subSelect.getSelectBody() != null) {
                processSelectBody(subSelect.getSelectBody());
            }
        } else if (fromItem instanceof ValuesList) {

        } else if (fromItem instanceof LateralSubSelect) {
            LateralSubSelect lateralSubSelect = (LateralSubSelect) fromItem;
            if (lateralSubSelect.getSubSelect() != null) {
                SubSelect subSelect = lateralSubSelect.getSubSelect();
                if (subSelect.getSelectBody() != null) {
                    processSelectBody(subSelect.getSelectBody());
                }
            }
        }
        //Table时不用处理
    }

    /**
     * 判断Orderby是否包含参数，有参数的不能去
     *
     * @param orderByElements
     * @return
     */
    public boolean orderByHashParameters(List<OrderByElement> orderByElements) {
        if (orderByElements == null) {
            return false;
        }
        for (OrderByElement orderByElement : orderByElements) {
            if (orderByElement.toString().contains("?")) {
                return true;
            }
        }
        return false;
    }

    public void process(Select select) {
        processWithItemsList(select.getWithItemsList());
        processSelectBody(select.getSelectBody());
    }
}
