/*
 * @(#)Menu.java	Mar 22, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.menu.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OrderBy;

import org.bachelor.ps.domain.Function;

/**
 * @author user
 *
 */
@Entity
@Table(name="T_bchlr_MENU_INFO")
public class Menu implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2417396837748420313L;

	@Id
	@GenericGenerator(name="uuidGen", strategy="uuid")
	@GeneratedValue(generator="uuidGen")
	private String id;
	
	private String name;
	
	private String description;
	
	@Column(name="SHOW_ORDER")
	private int showOrder;
	
	private String flag;
	
	@Column(name="ICON_URL")
	private String iconUrl;
	
	@Column(name="IMAGE_URL")
	private String imageUrl;
	
	@Column(name="OPEN_TYPE")
	private String openType;
	
	/** 父菜单 **/
	@ManyToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "PARENT_ID")
	private Menu parentMenu;
	
	@OneToMany(mappedBy = "parentMenu", cascade = CascadeType.ALL, 
														fetch = FetchType.EAGER, targetEntity = Menu.class)
	@OrderBy(clause="showOrder asc")
	private Set<Menu> childMenus;
	
	/** 关联模块 **/
	@ManyToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "FUNCTION_ID")
	private Function func = new Function();
	
	@Transient
	/** 是否可用**/
	private boolean usage;
	
	@Transient
	/** 菜单是否可见 **/
	private boolean visable;
	
	public boolean isVisable() {
		return visable;
	}

	public void setVisable(boolean visable) {
		this.visable = visable;
	}

	public boolean isUsage() {
		return usage;
	}

	public void setUsage(boolean usage) {
		this.usage = usage;
	}

	public String getOpenType() {
		return openType;
	}

	public void setOpenType(String openType) {
		this.openType = openType;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the showOrder
	 */
	public int getShowOrder() {
		return showOrder;
	}

	/**
	 * @param showOrder the showOrder to set
	 */
	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}

	/**
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * @param flag the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	 
	public Menu getParentMenu() {
		return parentMenu;
	}

	public void setParentMenu(Menu parentMenu) {
		this.parentMenu = parentMenu;
	}
 
	public Set<Menu> getChildMenus() {
		return childMenus;
	}

	public void setChildMenus(Set<Menu> childMenus) {
		this.childMenus = childMenus;
	}

	/**
	 * @return the iconUrl
	 */
	public String getIconUrl() {
		return iconUrl;
	}

	/**
	 * @param iconUrl the iconUrl to set
	 */
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	/**
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * @param imageUrl the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the func
	 */
	public Function getFunc() {
		return func;
	}

	/**
	 * @param func the func to set
	 */
	public void setFunc(Function func) {
		this.func = func;
	}


}
