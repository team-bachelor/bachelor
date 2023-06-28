package cn.org.bachelor.context;


/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2022/11/10
 */
public interface IUser {

    String getId();

    String getName();

    String getCode();

    String getOrgId();

    String getOrgName();

    String getDeptId();

    String getDeptName();

    String getAccessToken();

    String getTenantId();

    boolean isAdministrator();

    String getAreaId();

    String getAreaName();
}
