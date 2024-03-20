package cn.org.bachelor.context;


/**
 * @author liuzhuo
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
