package org.bachelor.facade.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.bachelor.auth.common.AuthConstant;
import org.bachelor.auth.domain.Role;
import org.bachelor.auth.service.IAuthRoleUserService;
import org.bachelor.context.common.ContextConstant;
import org.bachelor.context.service.IVLService;
import org.bachelor.dao.DaoConstant;
import org.bachelor.dao.vo.PageVo;
import org.bachelor.facade.service.IContextService;
import org.bachelor.gv.domain.GlobalEnum;
import org.bachelor.gv.domain.GlobalVariable;
import org.bachelor.gv.service.IGVService;
import org.bachelor.gv.service.IGlobalEnumService;
import org.bachelor.org.domain.User;
import org.bachelor.ps.domain.ProjectProperty;
import org.bachelor.ps.service.IProjectPropertyService;

@Service
public class ContextServiceImpl implements IContextService{
	
	@Autowired
	private IVLService vlService;
	
	@Autowired
	private  IProjectPropertyService ppService;
	
	@Autowired
	private  IAuthRoleUserService authRoleUserService;
	
	@Autowired
	private IGVService gvService;
	
	@Autowired
	private IGlobalEnumService geService;
	
	/**
	 * 取得工程上下文名称
	 * 
	 * @return 工程上下文名称
	 */
	@Override
	public String getContextName() {
		
		return (String) vlService.getGloableAttribute(ContextConstant.WEB_CONTEXT_NAME);
	}
	
	/**
	 * 取得当前登录用户拥有的角色信息
	 * 
	 * @return 当前登录用户拥有的角色信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getLoginRoles() {
		List<Role> roles = (ArrayList<Role>)vlService.getSessionAttribute(AuthConstant.LOGIN_USER_ROLE);
		return roles;
	}

	/**
	 * 取得当前登录用户信息
	 * 
	 * @return 当前登录用户信息
	 */
	@Override
	public User getLoginUser() {
		User user = (User)vlService.getSessionAttribute(AuthConstant.LOGIN_USER);
		return user;
	}

	/**
	 * 取得分页信息
	 * 
	 * @return 分页信息
	 */
	@Override
	public PageVo getPageVo() {
		PageVo pageVo = (PageVo)vlService.getRequestAttribute(DaoConstant.PAGE_INFO);
		return pageVo;
	}

	/**
	 * 取得项目属性信息
	 * 
	 * @return 项目属性信息
	 */
	@Override
	public ProjectProperty getProjectProperty() {
		
		return ppService.get();
	}

	/**
	 * 取得服务器物理路径
	 * 注意：路径末尾没有路径分隔符
	 * 
	 * @return 服务器物理路径
	 */
	@Override
	public String getServerRealPath() {
		
		return  (String) vlService.getGloableAttribute(ContextConstant.WEB_DOC_ROOT);
	}

	/**
	 * 判断当前用户是否具有指定的角色
	 * 如果参数是多个角色Id，那么当前用户只要具有其中一个角色就返回True，否则返回False。
	 * 
	 * @param roleIds 角色Id的数组
	 * 
	 * @return 当前用户只要具有其中一个角色就返回True，否则返回False。
	 */
	@Override
	public boolean hasRoles(String... roleIds) {
		User user = getLoginUser();
		return authRoleUserService.hasRoles(user.getId(), roleIds);
	} 
	
	/**
	 * 设置全局属性值
	 */
	public void setGloableAttribute(String key, Object value){
		
		vlService.setGloableAttribute(key, value);
	}
	/**
	 * 得到全局属性值
	 */
	public Object getGloableAttribute(String key){
		
		return vlService.getGloableAttribute(key);
	}
	/**
	 * 全局里面删除属性值
	 */
	public Object removeGloableAttribute(String key){
		
		return vlService.removeGloableAttribute(key);
	}
	
	public void setSessionAttribute(String key, Object value){
		
		vlService.setSessionAttribute(key, value);
	}
	
	public Object getSessionAttribute(String key){
		
		return vlService.getSessionAttribute(key);
	}
	
	public Object removeSessionAttribute(String key){
		
		return vlService.removeSessionAttribute(key);
	}
	
	public void setRequestAttribute(String key, Object value){
		
		vlService.setRequestAttribute(key, value);
	}
	
	public Object getRequestAttribute(String key){
		
		return vlService.getRequestAttribute(key);
	}
	
	public Object removeRequestAttribute(String key){
		
		return vlService.removeRequestAttribute(key);
	}

	/**
	 * 项目首页全路径
	 */
	@Override
	public String getProjectIndexPath(boolean includeContext) {
		 
		return getProjectProperty().getProjectIndexPath(includeContext);
	}

	/**
	 * 项目登陆显示路径
	 */
	@Override
	public String getProjectLoginShowPath(boolean includeContext) {
		 
		return getProjectProperty().getProjectLoginShowPath(includeContext);
	}

	/**
	 * 项目错误页路径
	 */
	@Override
	public String getProjectErrorPath(boolean includeContext) {
		 
		return getProjectProperty().getProjectErrorPath(includeContext);
	}

	/**
	 * 项目登陆验证路径
	 */
	@Override
	public String getProjectLoginAuthPath(boolean includeContext) {
		 
		return getProjectProperty().getProjectLoginAuthPath(includeContext);
	}
	
	/**
	 * 根据ID查询全局变量
	 */
	@Override
	public GlobalVariable findGVById(String id) {
	 
		return gvService.findById(id);
	}
	
	/**
	 * 根据名称查询全局变量
	 */
	@Override
	public GlobalVariable findGVByName(String name) {
		 
		return gvService.findByName(name);
	}
	
	/**
	 * 保存全局变量
	 */
	@Override
	public void saveGV(GlobalVariable gv) {
		 
		gvService.save(gv);
	}

	/**
	 * 更新全局变量
	 */
	@Override
	public void updateGV(GlobalVariable gv) {
		 
		gvService.update(gv);
	}

	/**
	 * 保存或者更新全局变量
	 */
	@Override
	public void saveOrUpdateGV(GlobalVariable gv) {
		 
		gvService.saveOrUpdate(gv);
	}

	/**
	 * 根据ID查询枚举信息
	 */
	@Override
	public GlobalEnum findGEById(String id) {
		 
		return geService.findById(id);
	}

	/**
	 * 根据名称查询枚举信息
	 */
	@Override
	public List<GlobalEnum> findGEByName(String enumName) {
	 
		return geService.findByEnumName(enumName);
	}

	/**
	 * 根据ID删除枚举信息
	 */
	@Override
	public void deleteGEById(String id) {
		 
		geService.deleteById(id);
	}
	
	/**
	 * 保存 枚举信息
	 */
	@Override
	public void saveGE(GlobalEnum globalEnum) {
	 
		geService.save(globalEnum);
	}

	/**
	 *  更新枚举信息
	 */
	@Override
	public void updateGE(GlobalEnum globalEnum) {

		geService.update(globalEnum);
	}

	/**
	 * 保存或者更新枚举信息
	 */
	@Override
	public void saveOrUpdateGE(GlobalEnum globalEnum) {
	 
		geService.saveOrUpdate(globalEnum);
	}
}
