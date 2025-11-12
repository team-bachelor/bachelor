package cn.org.bachelor.context;


import java.util.Map;

/**
 * @author liuzhuo
 */
@InjectDeny
public interface IUser {

    String getId();

    String getName();

    String getCode();

    String getOrgId();

    String getOrgName();

    String getDeptId();

    String getDeptName();

    String getAccessToken();

    String getRefreshToken();

    String getTenantId();

    boolean isAdministrator();

    String getAreaId();

    String getAreaName();

    Map<String, Object> getExtendInfo();
}
