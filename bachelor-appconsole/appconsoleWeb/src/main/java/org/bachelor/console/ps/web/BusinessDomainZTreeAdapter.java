package org.bachelor.console.ps.web;

import java.util.Set;

import org.bachelor.ps.domain.BusinessDomain;
import org.bachelor.web.ztree.ZTreeNodeAdapter;
import org.bachelor.web.ztree.ZTreeTreeNode;

public class BusinessDomainZTreeAdapter implements ZTreeNodeAdapter<BusinessDomain>{

	@Override
	public ZTreeTreeNode toZTreeNodeModel(BusinessDomain businessDomain) {
		ZTreeTreeNode node = new ZTreeTreeNode();
		node.setId(businessDomain.getId());
		node.setName(businessDomain.getName());
		return node;
	}

	@Override
	public Set<BusinessDomain> getChildren(BusinessDomain businessDomain) {
		
		return businessDomain.getChildren();
	}

}
