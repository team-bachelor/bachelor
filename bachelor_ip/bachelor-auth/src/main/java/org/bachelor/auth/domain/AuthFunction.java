package org.bachelor.auth.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import org.bachelor.ps.domain.Function;

@Entity
@Table(name="T_UFP_AUTH_FUNCTION")
public class AuthFunction implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3091980227471091527L;

	@Id
	@GenericGenerator(name="uuidGen", strategy="uuid")
	@GeneratedValue(generator="uuidGen")
	private String id;
	
	private String visible;
	
	private String usage;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "ROLE_ID")
	 private Role role;
	 
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "FUNCTION_ID")
	 private Function func;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Function getFunc() {
		return func;
	}

	public void setFunc(Function func) {
		this.func = func;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}
}
