/*
 * @(#)ContextHelper.java	May 23, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.facade.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Service;

import org.bachelor.auth.common.AuthConstant;
import org.bachelor.auth.domain.Role;
import org.bachelor.context.common.ContextConstant;
import org.bachelor.context.service.IVLService;
import org.bachelor.dao.DaoConstant;
import org.bachelor.dao.vo.PageVo;
import org.bachelor.org.domain.User;
import org.bachelor.ps.domain.ProjectProperty;

/**
 * 此工具类不在维护，请使用IContextService接口。
 * 
 * @author Team Bachelor
 *
 */
@Service
@Deprecated
public class ContextHelper implements BeanFactoryPostProcessor {
	
	private static IVLService vlService;
//	private static INoticeService noticeService;
	/*private static IProjectPropertyService ppService;*/
	private static SessionFactory sessionFactory;
	/**
	 * 功能: 得到登录用户信息
	 * 作者 曾强 2013-5-28下午05:58:46
	 * @return
	 */
	public static User getLoginUser(){
		User user = (User)vlService.getSessionAttribute(AuthConstant.LOGIN_USER);
		return user;
	}
	
	/**
	 * 功能: 得到登录角色集合
	 * 作者 曾强 2013-5-28下午05:58:27
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static  List<Role> getLoginRoles(){
		List<Role> roles = (ArrayList<Role>)vlService.getSessionAttribute(AuthConstant.LOGIN_USER_ROLE);
		return roles;
	} 
	
	public static PageVo getPageVo(){
		PageVo pageVo = (PageVo)vlService.getRequestAttribute(DaoConstant.PAGE_INFO);
		return pageVo;
	}
	
	/**
	 * 判断当前用户是否具有指定的角色
	 * 如果参数是多个角色Id，那么当前用户只要具有其中一个角色就返回True，否则返回False。
	 * 
	 * @param roleIds 角色Id的数组
	 * 
	 * @return 当前用户只要具有其中一个角色就返回True，否则返回False。
	 */
	public static boolean hasRoles(String...roleIds){
		//TODO:待实现
		return false;
	}
//	
//	/**
//	 * 创建新通知，创建者是当前用户
//	 * 将通知发送给指定的用户
//	 * 
//	 * @param title 通知标题
//	 * @param content 通知内容
//	 * @param userIdList 接收通知的用户ID的List
//	 * @param creatorId 创建者Id
//	 * @return 通知实体
//	 */
//	public Notice publish(String title, String content, List<String> userIdList){
//		//TODO:待实现
//		return null;
//	}
//
//	/**
//	 * 创建新通知，创建者是当前用户
//	 * 将通知发送给指定公司和角色所包含的用户。
//	 * roleIdList和companyIdList中的参数必须是一一对应。
//	 * 如果是发送给全局的角色，那么对应的companyIdList的值设置为null。
//	 * 
//	 * @param title 通知标题
//	 * @param content 通知内容
//	 * @param Url 关联Url
//	 * @param roleIdList 接收通知的角色ID的List
//	 * @param companyIdList 接收通知的公司ID的List
//	 * @return 通知实体
//	 */
//	public Notice publish(String title, String content, String Url,  List<String> roleIdList, List<String> companyIdList){
//		//TODO:待实现
//		return null;
//	}
//	
//	/**
//	 * 将通知设置为已阅,已阅者是当前用户
//	 * 如果通知已经是已阅，则此方法不做任何操作
//	 * 
//	 * @param noticeId 通知Id
//	 */
//	public void updateToRead(String noticeId){
//		//TODO:待实现
//	}
//	
//	/**
//	 * 将通知设置为已办，已办者是当前用户
//	 * 如果通知已经是已办，则此方法不做任何操作
//	 * 
//	 * @param noticeId 通知Id
//	 */
//	public void updateToDone(String noticeId){
//		//TODO:待实现
//	}
//	
//	/**
//	 * 查询当前用户以及所具有角色相关的通知
//	 * 
//	 * @param userId 用户Id
//	 * @return 通知实体List
//	 */
//	public List<Notice> queryNotice(){
//		//TODO:待实现
//		return null;
//	}
	
	/**
	 * 返回项目属性实体
	 * 
	 * @return 项目属性实体
	 */
	public static ProjectProperty getProjectProperty(){
		
		return null;//ppService.get();
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory factory)
			throws BeansException {
		vlService =  factory.getBean(IVLService.class);
//		noticeService = factory.getBean(INoticeService.class);
		/*ppService = factory.getBean(IProjectPropertyService.class);*/
		sessionFactory = factory.getBean(SessionFactory.class);
	}
	
	/**
	 * 实体对象
	 * @param obj
	 */
	public static void detachEntity(Object obj){
		
		sessionFactory.getCurrentSession().evict(obj);
	}
	
	/**
	 * 获取工程部署路径
	 * @return
	 */
	public static String achieveWebPath(){
		
		return (String) vlService.getGloableAttribute(ContextConstant.WEB_DOC_ROOT);
	}
}
