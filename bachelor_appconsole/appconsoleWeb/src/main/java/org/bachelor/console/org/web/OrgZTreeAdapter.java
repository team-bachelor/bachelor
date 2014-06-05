package org.bachelor.console.org.web;

import java.util.Set;

import org.bachelor.org.domain.Org;
import org.bachelor.web.ztree.ZTreeNodeAdapter;
import org.bachelor.web.ztree.ZTreeTreeNode;

public class OrgZTreeAdapter implements ZTreeNodeAdapter<Org>{

	@Override
	public ZTreeTreeNode toZTreeNodeModel(Org org) {
		ZTreeTreeNode node = new ZTreeTreeNode();
		node.setId(org.getId());
		node.setName(org.getName());
		node.setIsParent(false);
		if(org.getIsChildren()>0){
			node.setIsParent(true);
		}
		return node;
	}

	@Override
	public Set<Org> getChildren(Org org) {
		
		return org.childModules;
	}

}
