package org.bachelor.auth.domain;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import org.bachelor.org.domain.User;

@Entity
@Table(name = "T_bchlr_AUTH_ROLE_USER")
public class AuthRoleUser {
	
	@Id
	@GenericGenerator(name="uuidGen", strategy="uuid")
	@GeneratedValue(generator="uuidGen")
	private String id;
	
	@ManyToOne(cascade = { CascadeType.MERGE})
	@JoinColumn(name = "USER_ID")
	private User user;
	
	@ManyToOne(cascade = { CascadeType.MERGE})
	@JoinColumn(name = "ROLE_ID")
	private Role role;
	
	@Transient
	private String roleIds;
   
	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
