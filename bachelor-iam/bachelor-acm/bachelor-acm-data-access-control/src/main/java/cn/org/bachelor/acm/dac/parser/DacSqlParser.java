package cn.org.bachelor.acm.dac.parser;

import cn.org.bachelor.acm.dac.DacField;
import cn.org.bachelor.acm.dac.util.StringUtil;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

import java.util.*;

/**
 * sql解析类，提供更智能的数据访问权限查询sql
 *
 * @author liuzh
 */
public class DacSqlParser {
    private static final Log log = LogFactory.getLog(DacSqlParser.class);

    //<editor-fold desc="聚合函数">
//    private final Set<String> skipFunctions = Collections.synchronizedSet(new HashSet<String>());
//    private final Set<String> falseFunctions = Collections.synchronizedSet(new HashSet<String>());

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

    private List<String> dacTables = new ArrayList<>(0);
    private List<DacField> dacFields = new ArrayList<>(0);
    public DacSqlParser(List<String> dacTables, List<DacField> dacFields){
        this.dacTables = dacTables;
        this.dacFields = dacFields;
    }

    public static void main(String[] args) throws JSQLParserException {
//        String sql = "select * from DRILL_YEAR_PLAN where 1=1 \n" +
//                " and NAME like ? \n" +
//                " and YEAR = ? \n" +
//                " and MAKE_UNIT like ? \n" +
//                " order by UPDATE_TIME desc ";
        String sql = "SELECT DISTINCT " +
                "rm.MENU_CODE as MENU_CODE FROM cmn_acm_role_menu as rm " +
                "JOIN cmn_acm_user_role ur ON " +
                "rm.ROLE_CODE = ur.ROLE_CODE AND ur.USER_CODE = ?";
//        String sql = "SELECT " +
//                " `t_tmp`.`scenariosId` AS `scenariosId`, " +
//                " ifnull( `t1`.`planCount`, 0 ) AS `planCount`, " +
//                " ifnull( `t2`.`recordCount`, 0 ) AS `recordCount` " +
//                "FROM " +
//                " (((( " +
//                "  SELECT " +
//                "     `SCENARIOS_ID` AS `scenariosId` " +
//                "   FROM " +
//                "     `DRILL_DETAIL_PLAN` " +
//                "   WHERE " +
//                "     ( `SCENARIOS_ID` IS NOT NULL )) UNION (" +
//                "  SELECT " +
//                "    `SCENARIOS_ID` AS `scenariosId` " +
//                "   FROM " +
//                "    `DRILL_DRILL_RECORD` " +
//                "   WHERE " +
//                "    ( `SCENARIOS_ID` IS NOT NULL ))) `t_tmp` " +
//                "   LEFT JOIN ( SELECT `SCENARIOS_ID` AS `scenariosId`, count( 1 ) AS `planCount` FROM `DRILL_DETAIL_PLAN` GROUP BY `SCENARIOS_ID` ) `t1` ON (( " +
//                "     `t_tmp`.`scenariosId` = `t1`.`scenariosId` " +
//                "    ))) " +
//                "  LEFT JOIN ( SELECT `SCENARIOS_ID` AS `scenariosId`, count( 1 ) AS `recordCount` FROM `DRILL_DRILL_RECORD` GROUP BY `SCENARIOS_ID` ) `t2` ON (( " +
//                "   `t_tmp`.`scenariosId` = `t2`.`scenariosId` " +
//                " ))) ";
//        System.out.print("原SQL：");
//        System.out.println(sql);
//        DacSqlParser parser = new DacSqlParser(dacTables, dacFields);
//        System.out.print("隔离后：");
//        sql = parser.getSmartDacSql(sql);
//        System.out.println(sql);
    }

    /**
     * 添加到聚合函数，可以是逗号隔开的多个函数前缀
     *
     * @param functions
     */
    public static void addAggregateFunctions(String functions) {
        if (StringUtil.isNotEmpty(functions)) {
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
            log.error("sql解析失败，将返回全部数据", e);
            return sql;
        }
        Select select = (Select) stmt;
        SelectBody selectBody = select.getSelectBody();
        try {
            //处理body-去order by
            processSelectBody(selectBody);
        } catch (Exception e) {
            log.warn("sql处理失败，将返回全部数据");
            return sql;
        }
        //处理with-去order by
        processWithItemsList(select.getWithItemsList());
//        //处理为count查询
//        sqlToCount(select);
        String result = select.toString();
        return result;
    }


    /**
     * 处理selectBody
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
            }
        }
    }

    /**
     * 处理PlainSelect类型的selectBody
     *
     * @param plainSelect
     */
    public void processPlainSelect(PlainSelect plainSelect) {

        if (plainSelect.getFromItem() != null) {
            processFromItem(plainSelect, plainSelect.getFromItem());
        }

        if (plainSelect.getJoins() != null && plainSelect.getJoins().size() > 0) {
            List<Join> joins = plainSelect.getJoins();
            for (Join join : joins) {
                if (join.getRightItem() != null) {
                    processFromItem(join, join.getRightItem());
                }
            }
        }
    }

    private void appendAndWhere(PlainSelect plainSelect, String addWhere) {
        try {
            Expression where = plainSelect.getWhere();
            Expression additionWhere = CCJSqlParserUtil.parseCondExpression(addWhere);
            if (where == null) {
                where = additionWhere;
            } else {
                where = new AndExpression(where, additionWhere);
            }
            plainSelect.setWhere(where);
        } catch (JSQLParserException e) {
            e.printStackTrace();
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
    private void processFromItem(Object node, FromItem fromItem) {
        if (fromItem instanceof SubJoin) {
            SubJoin subJoin = (SubJoin) fromItem;
            if (subJoin.getJoinList() != null && subJoin.getJoinList().size() > 0) {
                for (Join join : subJoin.getJoinList()) {
                    if (join.getRightItem() != null) {
                        processFromItem(join, join.getRightItem());
                    }
                }
            }
//            new SubSelect().getSelectBody()
//            CCJSqlParserUtil.parseCondExpression()
//            subJoin.setl
            if (subJoin.getLeft() != null) {
                processFromItem(subJoin, subJoin.getLeft());
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
        } else if (fromItem instanceof Table) {
            Table t = (Table) fromItem;
//            fromItem.getAlias().getName();
//            String addWhere = "rm.tenant_id=''";
//            appendAndWhere(plainSelect, addWhere);
            if (!isDacTable(t)) {
                return;
            }
            if (node instanceof PlainSelect) {
                PlainSelect select = (PlainSelect) node;

            } else if (node instanceof Join) {
                Join join = (Join) node;
            } else if (node instanceof SubJoin) {
                SubJoin subJoin = (SubJoin) node;
            }
        }
    }

    private boolean isDacTable(Table t) {
        boolean isDac = dacTables.contains(t.getName());
        if (isDac) {
            return true;
        }
        for (String dacTable : dacTables) {
            String[] tableArray = dacTable.split(".");
            if (tableArray.length > 1 && tableArray[1].equals(t.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断Order by是否包含参数，有参数的不能去
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
