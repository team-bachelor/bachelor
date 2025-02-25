package cn.org.bachelor.context;


import java.util.Map;

/**
 * 用户信息接口
 * @author liuzhuo
 */
public interface IUser {

    /**
     * 获取用户ID
     * @return
     */
    String getId();

    /**
     *
     * @return
     */
    String getName();

    /**
     *
     * @return
     */
    String getCode();

    /**
     *
     * @return
     */
    String getOrgId();

    /**
     *
     * @return
     */
    String getOrgName();

    /**
     *
     * @return
     */
    String getDeptId();

    /**
     *
     * @return
     */
    String getDeptName();

    /**
     *
     * @return
     */
    String getAccessToken();

    /**
     *
     * @return
     */
    String getTenantId();

    /**
     *
     * @return
     */
    boolean isAdministrator();

    /**
     *
     * @return
     */
    String getAreaId();

    /**
     *
     * @return
     */
    String getAreaName();

    /**
     *
     * @return
     */
    Map<String, Object> getExtendInfo();
}
