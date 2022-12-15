package cn.org.bachelor.acm.dac.parser;

import cn.org.bachelor.acm.dac.DacFieldConfig;
import cn.org.bachelor.acm.dac.util.StringUtil;
import cn.org.bachelor.context.IUser;
import cn.org.bachelor.exception.SystemException;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import tk.mybatis.mapper.entity.Example;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    /**
     * 需要进行数据权限拦截的表
     */
    private List<String> dacTables = new ArrayList<>(0);
    /**
     * 要进行数据权限拦截的字段
     */
    private List<DacFieldConfig> dacFieldConfigs = new ArrayList<>(0);
    /**
     * 进行权限匹配的用户
     */
    private IUser user;
    /**
     * 要进行数据权限拦截的字段
     */
    private Map<DacFieldConfig, Object> fieldAndValueMap = new HashMap<DacFieldConfig, Object>(0);

    public DacSqlParser(List<String> dacTables, List<DacFieldConfig> dacFieldConfigs, IUser user) {
        this.dacTables = dacTables;
        this.dacFieldConfigs = dacFieldConfigs;
        this.user = user;
        this.fieldAndValueMap = getFieldValueMap(this.dacFieldConfigs);
    }

    /**
     * 处理Example型条件
     *
     * @param e example
     */
    public void getDacExample(Example e) {
        fieldAndValueMap.keySet().forEach(key -> {
            Object value = fieldAndValueMap.get(key);
            String targetFieldName = getTarget(e.getEntityClass(), key.getName());
            if (targetFieldName == null) {
                throw new SystemException("目标拦截对象：["
                        + e.getEntityClass().getTypeName() +
                        "]不包含属性：[" + key.getName() + "]" +
                        ",建议在类定义中增加对应的属性。");
            }
//                key.setDeep(true);
            if (key.isDeep() && value instanceof String && value != null && key.getPattern() != null && key.getPattern().length() == value.toString().length()) {
                // TODO 先不用dialect
                int index = getMatchIndex(key, value);
                String condition = value.toString().substring(0, index + 1) + "%";
                e.and(e.createCriteria().andLike(targetFieldName, condition));
            } else {
                e.and(e.createCriteria().andEqualTo(targetFieldName, value));
            }
        });
    }

    /**
     * 获取目标类型
     *
     * @param entityClass
     * @param key
     * @return
     */
    private String getTarget(Class<?> entityClass, String key) {
        Field[] fs = entityClass.getDeclaredFields();
        key = StringUtil.toUppercaseIgnoreUnderscore(key);
        for (int i = 0; i < fs.length; i++) {
            String fn = StringUtil.toUppercaseIgnoreUnderscore(fs[i].getName());
            if (fn.equals(key)) {
                return fs[i].getName();
            }
        }
        return null;
    }

    /**
     * 模糊匹配功能
     *
     * @param key
     * @param value
     * @return
     */
    private int getMatchIndex(DacFieldConfig key, Object value) {
        char[] patternArray = key.getPattern().toCharArray();
        char[] valueArray = value.toString().toCharArray();
        int i = patternArray.length - 1;
        for (; i >= 0; i--) {
            char p = patternArray[i];
            char v = valueArray[i];
            if (p != v) {
                return i;
            }
        }
        return patternArray.length - 1;
    }

    /**
     * -----------以上是example相关处理，下面是SQL的处理--------------
     */
    private Map<DacFieldConfig, Object> getFieldValueMap(List<DacFieldConfig> dacFieldConfigs) {
        Map<DacFieldConfig, Object> colAndValue = new HashMap<>(dacFieldConfigs.size());
        if (user == null) {
            return colAndValue;
        }
        Class<?> clazz = user.getClass();
        Map<String, Field> fields = getClassFieldsMap(clazz.getDeclaredFields());
        Map<String, Method> methods = getClassGetMethodMap(clazz.getDeclaredMethods());
        for (int i = 0; i < dacFieldConfigs.size(); i++) {
            DacFieldConfig dacFieldConfig = dacFieldConfigs.get(i);
            String dfName = dacFieldConfig.getName();
            if (dfName == null || dfName.isEmpty()) {
                continue;
            }
            Object value = getValue(user, dacFieldConfig, methods, fields);
            colAndValue.put(dacFieldConfig, value);
        }
        return colAndValue;
    }

    private Map<String, Method> getClassGetMethodMap(Method[] declaredMethods) {
        Map<String, Method> map = new HashMap<>(declaredMethods.length);
        for (int i = 0; i < declaredMethods.length; i++) {
            String mName = StringUtil.toUppercaseIgnoreUnderscore(declaredMethods[i].getName());
            if (mName.startsWith("GET")) {
                map.put(mName, declaredMethods[i]);
            }
        }
        return map;
    }

    private Map<String, Field> getClassFieldsMap(Field[] declaredFields) {
        Map<String, Field> map = new HashMap<>(declaredFields.length);
        for (int i = 0; i < declaredFields.length; i++) {
            map.put(StringUtil.toUppercaseIgnoreUnderscore(declaredFields[i].getName()), declaredFields[i]);
        }
        return map;
    }

    private Object getValue(IUser user, DacFieldConfig dacFieldConfig, Map<String, Method> methods, Map<String, Field> fields) {
        String dfName = dacFieldConfig.getName();
        dfName = StringUtil.toUppercaseIgnoreUnderscore(dfName);
        String dfGetMethodName = "GET" + dfName;
        Method m = methods.get(dfGetMethodName);
        if (m != null) {
            m.setAccessible(true);
            try {
                return m.invoke(user);
            } catch (IllegalAccessException e) {
                throw new SystemException("方法不能访问：" +
                        m.getDeclaringClass().getTypeName() +
                        ":" + m.getName());
            } catch (InvocationTargetException e) {
                throw new SystemException("方法访问错误：" +
                        m.getDeclaringClass().getTypeName() +
                        ":" + m.getName() +
                        "-" + e);
            }
        }
        Field f = fields.get(dfName);
        if (f != null) {
            f.setAccessible(true);
            try {
                return f.get(user);
            } catch (IllegalAccessException e) {
                throw new SystemException("属性访问错误：" +
                        m.getDeclaringClass().getTypeName() +
                        ":" + m.getName() +
                        "-" + e);
            }
        }
        return null;
    }

    public static void main(String[] args) throws JSQLParserException {
//        String sql = "select * from DRILL_YEAR_PLAN where 1=1 \n" +
//                " and NAME like ? \n" +
//                " and YEAR = ? \n" +
//                " and MAKE_UNIT like ? \n" +
//                " order by UPDATE_TIME desc ";
//        String sql = "SELECT DISTINCT " +
//                "rm.MENU_CODE as MENU_CODE FROM cmn_acm_role_menu as rm " +
//                "JOIN cmn_acm_user_role ur ON " +
//                "rm.ROLE_CODE = ur.ROLE_CODE AND ur.USER_CODE = ? and 1=1";
        String sql = "SELECT " +
                " `t_tmp`.`scenariosId` AS `scenariosId`, " +
                " ifnull( `t1`.`planCount`, 0 ) AS `planCount`, " +
                " ifnull( `t2`.`recordCount`, 0 ) AS `recordCount` " +
                "FROM " +
                " (((( " +
                "  SELECT " +
                "     `SCENARIOS_ID` AS `scenariosId` " +
                "   FROM " +
                "     `DRILL_DETAIL_PLAN` " +
                "   WHERE " +
                "     ( `SCENARIOS_ID` IS NOT NULL )) UNION (" +
                "  SELECT " +
                "    `SCENARIOS_ID` AS `scenariosId` " +
                "   FROM " +
                "    `DRILL_DRILL_RECORD` " +
                "   WHERE " +
                "    ( `SCENARIOS_ID` IS NOT NULL ))) `t_tmp` " +
                "   LEFT JOIN ( SELECT `SCENARIOS_ID` AS `scenariosId`, count( 1 ) AS `planCount` FROM `DRILL_DETAIL_PLAN` GROUP BY `SCENARIOS_ID` ) `t1` ON (( " +
                "     `t_tmp`.`scenariosId` = `t1`.`scenariosId` " +
                "    ))) " +
                "  LEFT JOIN ( SELECT `SCENARIOS_ID` AS `scenariosId`, count( 1 ) AS `recordCount` FROM `DRILL_DRILL_RECORD` GROUP BY `SCENARIOS_ID` ) `t2` ON (( " +
                "   `t_tmp`.`scenariosId` = `t2`.`scenariosId` " +
                " ))) ";
        List<String> dacTables = new ArrayList<>(1);
        dacTables.add("DRILL_DRILL_RECORD");
        dacTables.add("DRILL_DETAIL_PLAN");
        List<DacFieldConfig> dacFieldConfigs = new ArrayList<>(1);
        DacFieldConfig field = new DacFieldConfig();
        field.setName("area_id");
        field.setDeep(false);
        dacFieldConfigs.add(field);
        field = new DacFieldConfig();
        field.setName("tenant_id");
        field.setDeep(true);
        dacFieldConfigs.add(field);
        System.out.print("原SQL：");
        System.out.println(sql);
        DacSqlParser parser = new DacSqlParser(dacTables, dacFieldConfigs, new IUser() {
            @Override
            public String getId() {
                return "getId";
            }

            @Override
            public String getCode() {
                return "getCode";
            }

            @Override
            public String getOrgId() {
                return "getOrgId";
            }

            @Override
            public String getDeptId() {
                return "getDeptId";
            }

            @Override
            public String getAccessToken() {
                return "getAccessToken";
            }

            @Override
            public String getTenantId() {
                return "11111100";
            }

            @Override
            public boolean isAdministrator() {
                return false;
            }

            @Override
            public String getAreaId() {
                return "22000000";
            }
        });
        System.out.print("隔离后：");
        sql = parser.getSmartDacSql(sql);
        System.out.println(sql);
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
            log.error("sql处理失败，将返回全部数据", e);
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

    private Expression appendAndExpression(Expression where, String addWhere) {
        try {
            Expression additionWhere = CCJSqlParserUtil.parseCondExpression(addWhere);
            if (where == null) {
                where = additionWhere;
            } else {
                where = new AndExpression(where, additionWhere);
            }
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
        return where;
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
            if (!isDacTable(t)) {
                return;
            }
            if (node instanceof PlainSelect) {
                PlainSelect select = (PlainSelect) node;
                select.setWhere(appendDacConditions(t, select.getWhere()));
            } else if (node instanceof Join) {
                Join join = (Join) node;
                join.addOnExpression(appendDacConditions(t, null));
            } else if (node instanceof SubJoin) {
                SubJoin subJoin = (SubJoin) node;
                subJoin.getJoinList().forEach(join -> {
                    processFromItem(join, join.getRightItem());
                });
            }
        }
    }

    private Expression appendDacConditions(Table t, Expression where) {
        String alias = aliasPrefix(t.getAlias());
        for (DacFieldConfig key : fieldAndValueMap.keySet()) {
            Object value = fieldAndValueMap.get(key);
            StringBuilder addWhere = new StringBuilder();
            addWhere
                    .append(alias)
                    .append(key.getName());
            String condition = value == null ? null : value.toString();
            if (key.isDeep() && value instanceof String && value != null && key.getPattern() != null && key.getPattern().length() == value.toString().length()) {
                // TODO 先不用dialect
                int index = getMatchIndex(key, value);
                condition = value.toString().substring(0, index + 1) + "%";
                addWhere.append(" like ");
            } else {
                addWhere.append(" = ");
            }
            addWhere.append("'")
                    .append(condition)
                    .append("'");
            where = appendAndExpression(where, addWhere.toString());
        }
        return where;
    }

    private String aliasPrefix(Alias alias) {
        return alias == null? "" : StringUtil.isEmpty(alias.getName()) ? "" : alias + ".";
    }

    private boolean isDacTable(Table t) {
        String tableName = t.getName();
        tableName = tableName.replace("`", "");
        boolean isDac = dacTables.contains(tableName.toLowerCase(Locale.ENGLISH));
        if (isDac) {
            return true;
        }
        isDac = dacTables.contains(tableName.toUpperCase(Locale.ENGLISH));
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
