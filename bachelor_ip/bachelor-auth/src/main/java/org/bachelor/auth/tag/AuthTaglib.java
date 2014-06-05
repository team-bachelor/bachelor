package org.bachelor.auth.tag;


import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import org.bachelor.auth.common.AuthConstant;
import org.bachelor.auth.domain.Role;
import org.bachelor.org.domain.Org;
import org.bachelor.org.domain.User;

@Service
public class AuthTaglib extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8808610577895031513L;
	
	/** 不存在角色名称之内**/
	private String notHasRoles;
	/** 存在公司信息**/
	private String company;
	/** 存在角色名称之内**/
	private String hasRoles;
	/** 不存在公司信息**/
	private String notCompany;
 
	public String getHasRoles() {
		return hasRoles;
	}

	public void setHasRoles(String hasRoles) {
		this.hasRoles = hasRoles;
	}

	public String getNotCompany() {
		return notCompany;
	}

	public void setNotCompany(String notCompany) {
		this.notCompany = notCompany;
	}

	public String getNotHasRoles() {
		return notHasRoles;
	}

	public void setNotHasRoles(String notHasRoles) {
		this.notHasRoles = notHasRoles;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int doStartTag() throws JspException {
		
		/** 传入参数值都为空时，执行标签范围中的内容 **/
		if(isEmptyForParam()){
			
			return Tag.EVAL_BODY_INCLUDE;
		}
		
		/** 从session 中获取角色信息 **/
		List<Role> role_list = (List<Role>)pageContext.getSession().getAttribute(AuthConstant.LOGIN_USER_ROLE);
		User user = (User)pageContext.getSession().getAttribute(AuthConstant.LOGIN_USER);
		
		/** 传入参数存在于session角色及机构信息，或者角色及机构为空时，执行标签范围中的内容**/
		if(isExistsRolesAndCompanys(role_list,user)){
			
			return Tag.EVAL_BODY_INCLUDE;
		}
		 
		/** 跳过标签范围的内容 **/
		return Tag.SKIP_BODY;
	} 
	
	/**
	 * 传入的参数是否存在于角色集合及二级单位信息
	 * @param roles
	 * @param org
	 * @return
	 */
	public boolean  isExistsRolesAndCompanys(List<Role> roles,User user){
		if(roles==null || roles.size()<=0 || user==null || 
				user.getOwnerOrg() == null || user.getOwnerOrg().getCompanyName()==null){
			
			return true;
		}
		
		/** session中角色和机构都没有包涵传入的参数值 **/
		if(this.notHasRoles!=null && isExistsForLoginSession(roles,user,notHasRoles.split(","),notCompany.split(","))==false){
			
			return true;
		}
		/**session中角色和机构包涵传入的参数值**/
		if(this.hasRoles!=null && isExistsForLoginSession(roles,user,hasRoles.split(","),company.split(","))){
			
			return true;
		}
		
		return false;
	}
	
	/** 标签传入参数值，是否在session范围内 **/
	public boolean isExistsForLoginSession(List<Role> roles,User user,String roleArray[],String orgArray[]){
		/** 角色标识**/
		boolean roleFlag = false;
		/** 机构标识**/
		boolean orgFlag = false;
		/** 是否在session角色之内 **/
		for(Role role:roles){
			for(String r:roleArray){
				if(role.getName().equals(r)){
					roleFlag = true;
					break;
				}
			}
			if(roleFlag){
				break;
			}
		}
		/** 是否在session机构之内 **/
		for(String companyName:orgArray){
			if(user.getOwnerOrg().getCompanyName().equals(companyName)){
				orgFlag = true;
				break;
			}
		} 
		if(orgFlag  && roleFlag){
			
			return true;
		}
		return false;
	}
	
	/**
	 * 如果传入参数都为空时执行标签内容，反之，做其他业务处理
	 */
	public boolean isEmptyForParam(){
		if(StringUtils.isEmpty(this.notHasRoles) && StringUtils.isEmpty(this.hasRoles) &&
				StringUtils.isEmpty(this.company) && StringUtils.isEmpty(this.notCompany)){
			
			return true;
		}
		return false;
	}
}
