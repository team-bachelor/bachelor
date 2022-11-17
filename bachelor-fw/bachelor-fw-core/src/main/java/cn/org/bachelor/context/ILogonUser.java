package cn.org.bachelor.context;


/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2018/11/1
 */
public interface ILogonUser {

    String getId();

    String getCode();

    String getOrgId();

    String getDeptId();

    String getAccessToken();

    String getTenantId();

    boolean isAdministrator();

    String getAdministrativeLevel();
}
