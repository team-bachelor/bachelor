package org.bachelor.console.menu.web;

import java.util.Set;

import org.bachelor.console.common.Constant;
import org.bachelor.menu.domain.Menu;
import org.bachelor.web.ztree.ZTreeNodeAdapter;
import org.bachelor.web.ztree.ZTreeTreeNode;

public class MenuZTreeAdapter implements ZTreeNodeAdapter<Menu>{

	@Override
	public ZTreeTreeNode toZTreeNodeModel(Menu menu) {
		ZTreeTreeNode node = new ZTreeTreeNode();
		node.setId(menu.getId());
		node.setName(menu.getName());
		node.setNodeType(Constant.ZTREE_NODE_TYPE_MENU);
		return node;
	}

	@Override
	public Set<Menu> getChildren(Menu menu) {
		
		return menu.getChildMenus();
	}

}
