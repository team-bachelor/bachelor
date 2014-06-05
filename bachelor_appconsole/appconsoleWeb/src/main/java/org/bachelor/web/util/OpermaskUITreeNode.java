/*
 * @(#)OperamaskUITreeNode.java	Mar 11, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.web.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Team Bachelor
 *
 */
public class OpermaskUITreeNode {
	
	public static final String CLASS_FOLDER = "folder";
	public static final String CLASS_FILE = "file";
	
	public static final String NODE_TYPE_PROJECT = "project";
	public static final String NODE_TYPE_MODULE = "module";

	private String id = "";
	/** 树节点显示文本，必需 **/
	private String text = "";
	/** 是否默认展开，非必须，默认值是false **/
	private boolean expanded = false;
	/** 树节点样式，非必需，默认有folder和file，如果用户自定制为其他，则显示用户自定义样式**/
	private String classes = "file";
	/** 是否有子节点，非必需，如果值为true表示要缓加载此时可以没有children属性**/
	private boolean hasChildren = false;
	/** 节点类型：工程，模块*/
	private String nodeType = "";
	
	private List<OpermaskUITreeNode> children = new ArrayList<OpermaskUITreeNode>();
	
	/**
	 * 
	 */
	public OpermaskUITreeNode(){
	}
	
	
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * @return the expanded
	 */
	public boolean isExpanded() {
		return expanded;
	}
	/**
	 * @param expanded the expanded to set
	 */
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	/**
	 * @return the classes
	 */
	public String getClasses() {
		return classes;
	}
	/**
	 * @param classes the classes to set
	 */
	public void setClasses(String classes) {
		this.classes = classes;
	}
	
	/**
	 * @return the hasChildren
	 */
	public boolean isHasChildren() {
		return hasChildren;
	}
	/**
	 * @param hasChildren the hasChildren to set
	 */
	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}


	/**
	 * @return the nodeType
	 */
	public String getNodeType() {
		return nodeType;
	}


	/**
	 * @param nodeType the nodeType to set
	 */
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	/**
	 * @return the children
	 */
	public List<OpermaskUITreeNode> getChildren() {
		return children;
	}


	/**
	 * @param children the children to set
	 */
	public void setChildren(List<OpermaskUITreeNode> children) {
		this.children = children;
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
	
}
