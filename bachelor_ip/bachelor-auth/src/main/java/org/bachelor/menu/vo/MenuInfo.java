/*
 * @(#)MenuInfo.java	Apr 24, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.menu.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author user
 *
 */
public class MenuInfo {

	private String id;
	
	private String name;
	
	private String description;
	
	private int showOrder;
	
	private String iconUrl;
	
	private String imageUrl;
	
	private List<MenuInfo> children = new ArrayList<MenuInfo>();
	
	private String funcId;
	
	private String funcUrl;
	
	/** 菜单是否可以执行，也就是是否可以响应单击事件**/
	private boolean executable;
	 
	public boolean isExecutable() {
		return executable;
	}

	public void setExecutable(boolean executable) {
		this.executable = executable;
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
	 * @return the children
	 */
	public List<MenuInfo> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<MenuInfo> children) {
		this.children = children;
	}

	/**
	 * @return the funcId
	 */
	public String getFuncId() {
		return funcId;
	}

	/**
	 * @param funcId the funcId to set
	 */
	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}

	/**
	 * @return the funcUrl
	 */
	public String getFuncUrl() {
		return funcUrl;
	}

	/**
	 * @param funcUrl the funcUrl to set
	 */
	public void setFuncUrl(String funcUrl) {
		this.funcUrl = funcUrl;
	}
	
	
}
