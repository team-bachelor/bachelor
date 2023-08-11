package org.bachelor.console.auth.web;

import java.util.HashSet;
import java.util.Set;

import org.bachelor.auth.domain.Role;
import org.bachelor.auth.domain.RoleGroup;
import org.bachelor.console.common.Constant;
import org.bachelor.ps.domain.Function;
import org.bachelor.ps.domain.ProjectModule;
import org.bachelor.web.ztree.ZTreeNodeAdapter;
import org.bachelor.web.ztree.ZTreeTreeNode;

public class RoleZTreeAdapter implements ZTreeNodeAdapter<Object>{

	@Override
	public ZTreeTreeNode toZTreeNodeModel(Object obj) {
		ZTreeTreeNode node = new ZTreeTreeNode();
		if(obj instanceof RoleGroup){
			RoleGroup rg = (RoleGroup)obj;
			node.setId(rg.getId());
			node.setName(rg.getName());
			node.setFlag(rg.getFlag());
			node.setNodeType(Constant.ZTREE_NODE_TYPE_GROUP);
		}
		if(obj instanceof Role){
			Role r = (Role)obj;
			node.setId(r.getId());
			node.setName(r.getDescription());
			node.setNodeType(Constant.ZTREE_NODE_TYPE_ROLE);
		}

		return node;
	}

	@Override
	public Set<Object> getChildren(Object obj) {
		Set<Object> objSet = new HashSet<Object>();
		if(obj instanceof RoleGroup){
			RoleGroup pm = (RoleGroup)obj;
			
			objSet.addAll(pm.getChildrenRoleGroups());
			objSet.addAll(pm.getRoles());
		}
		
		return objSet;
	}

}
