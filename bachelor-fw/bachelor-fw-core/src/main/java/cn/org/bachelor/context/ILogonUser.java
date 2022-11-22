package cn.org.bachelor.context;


/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2022/11/10
 */
public interface ILogonUser {

    String getId();

    String getCode();

    String getOrgId();

    String getDeptId();

    String getAccessToken();

    String getTenantId();

    boolean isAdministrator();

    String getAreaId();
}
