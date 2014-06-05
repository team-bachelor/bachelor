package org.bachelor.console.ps.web;

import java.util.HashSet;
import java.util.Set;

import org.bachelor.console.common.Constant;
import org.bachelor.ps.domain.Function;
import org.bachelor.ps.domain.ProjectModule;
import org.bachelor.web.ztree.ZTreeNodeAdapter;
import org.bachelor.web.ztree.ZTreeTreeNode;

public class ProjectModuleZTreeAdapter implements ZTreeNodeAdapter<Object>{

	@Override
	public ZTreeTreeNode toZTreeNodeModel(Object obj) {
		ZTreeTreeNode node = new ZTreeTreeNode();
		if(obj instanceof ProjectModule){
			ProjectModule pm = (ProjectModule)obj;
			node.setId(pm.getId());
			node.setName(pm.getName());
			node.setNodeType(Constant.ZTREE_NODE_TYPE_MODULE);
		}
		if(obj instanceof Function){
			Function f = (Function)obj;
			node.setId(f.getId());
			node.setName(f.getName());
			node.setNodeType(Constant.ZTREE_NODE_TYPE_FUNC);
		}

		return node;
	}

	@Override
	public Set<Object> getChildren(Object obj) {
		Set<Object> objSet = new HashSet<Object>();
		if(obj instanceof ProjectModule){
			ProjectModule pm = (ProjectModule)obj;
			
			objSet.addAll(pm.getChildModules());
			objSet.addAll(pm.getFunctions());
		}
		
		return objSet;
	}

}
