package org.bachelor.facade.service;

import java.util.List;

import org.bachelor.auth.domain.Role;
import org.bachelor.dao.vo.PageVo;
import org.bachelor.gv.domain.GlobalEnum;
import org.bachelor.gv.domain.GlobalVariable;
import org.bachelor.org.domain.User;
import org.bachelor.ps.domain.ProjectProperty;

/**
 * 系统上下文服务
 * 
 * @author user
 *
 */
public interface IContextService {

	/**
	 * 取得当前登录用户信息
	 * 
	 * @return 当前登录用户信息
	 */
	public User getLoginUser();
	
	/**
	 * 取得当前登录用户拥有的角色信息
	 * 
	 * @return 当前登录用户拥有的角色信息
	 */
	@SuppressWarnings("unchecked")
	public List<Role> getLoginRoles();
	
	/**
	 * 取得分页信息
	 * 
	 * @return 分页信息
	 */
	public PageVo getPageVo();
	
	/**
	 * 判断当前用户是否具有指定的角色
	 * 如果参数是多个角色Id，那么当前用户只要具有其中一个角色就返回True，否则返回False。
	 * 
	 * @param roleIds 角色Id的数组
	 * 
	 * @return 当前用户只要具有其中一个角色就返回True，否则返回False。
	 */
	public boolean hasRoles(String...roleIds);
	
	/**
	 * 取得项目属性信息
	 * 
	 * @return 项目属性信息
	 */
	public ProjectProperty getProjectProperty();
	
	/**
	 * 取得工程上下文名称
	 * 
	 * @return 工程上下文名称
	 */
	public String getContextName();
	
	/**
	 * 取得服务器物理路径
	 * 注意：路径末尾没有路径分隔符
	 * 
	 * @return 服务器物理路径
	 */
	public String getServerRealPath();
	
	/**
	 * 设置全局属性
	 * @param key
	 * @param value
	 */
	public void setGloableAttribute(String key, Object value);
	/**
	 * 通过设置的KEY，得到全局属性
	 * @param key
	 * @param value
	 */
	public Object getGloableAttribute(String key);
	/**
	 * 通过设置的KEY，删除全局属性
	 * @param key
	 * @param value
	 */
	public Object removeGloableAttribute(String key);
	
	/**
	 * 设置Session属性
	 * @param key
	 * @param value
	 */
	public void setSessionAttribute(String key, Object value);
	/**
	 * 根据设置的KEY，得到Session属性
	 * @param key
	 * @param value
	 */
	public Object getSessionAttribute(String key);
	/**
	 * 根据设置的KEY，删除Session属性
	 * @param key
	 * @param value
	 */
	public Object removeSessionAttribute(String key);
	
	/**
	 * 设置Request属性
	 * @param key
	 * @param value
	 */
	public void setRequestAttribute(String key, Object value);
	/**
	 * 根据设置的KEY，得到Request属性
	 * @param key
	 * @param value
	 */
	public Object getRequestAttribute(String key);
	/**
	 * 根据设置的KEY，删除Request属性
	 * @param key
	 * @param value
	 */
	public Object removeRequestAttribute(String key);
	
	/**
	 * 项目首页全路径
	 */
	public String getProjectIndexPath(boolean includeContext);
	
	/**
	 * 项目登陆显示路径
	 */
	public String getProjectLoginShowPath(boolean includeContext);
	
	/**
	 * 项目错误页路径
	 */
	public String getProjectErrorPath(boolean includeContext);
	
	/**
	 * 项目登陆验证路径
	 */
	public String getProjectLoginAuthPath(boolean includeContext);
	
	/**
	 * 根据ID查询全局变量信息
	 * @param id
	 * @return
	 */
	public GlobalVariable findGVById(String id);
	/**
	 * 根据名称查询全局变量信息
	 * @param id
	 * @return
	 */
	public GlobalVariable findGVByName(String name);
	/**
	 * 保存全局变量信息
	 * @param id
	 * @return
	 */
	public void saveGV(GlobalVariable gv);
	/**
	 * 更新全局变量信息
	 * @param id
	 * @return
	 */
	public void updateGV(GlobalVariable  gv);
	/**
	 * 保存或者更新全局变量信息
	 * @param id
	 * @return
	 */
	public void saveOrUpdateGV(GlobalVariable  gv);
 
	
	/**
	 * 根据ID查询枚举信息
	 * @param id
	 * @return
	 */
	public GlobalEnum findGEById(String id);
	/**
	 * 根据名称查询枚举信息
	 * @param id
	 * @return
	 */
	public List<GlobalEnum> findGEByName(String enumName);
	/**
	 * 根据ID删除枚举信息
	 * @param id
	 * @return
	 */
	public void deleteGEById(String id);
	/**
	 * 保存枚举信息
	 * @param id
	 * @return
	 */
	public void saveGE(GlobalEnum globalEnum);
	/**
	 * 更新枚举信息
	 * @param id
	 * @return
	 */
	public void updateGE(GlobalEnum globalEnum);
	/**
	 * 保存或者更新枚举信息
	 * @param id
	 * @return
	 */
	public void saveOrUpdateGE(GlobalEnum globalEnum);
}
