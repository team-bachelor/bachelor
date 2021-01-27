package cn.org.bachelor.demo.web.utils;

import cn.org.bachelor.demo.web.domain.OrganizationTree;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * list 转 tree 工具类
 *
 * @author lixiaolong
 * @date 2020/12/30 13:55
 */
@Data
public class TreeUtil {

    /**
     * 把一个List转成树
     *
     * @param list
     * @return
     */
    public static List<OrganizationTree> buildOrganizationTree(List<OrganizationTree> list) {
        List<OrganizationTree> tree = new ArrayList<>();
        for (OrganizationTree node : list) {
            if (node.getParentId() == 0) {
                tree.add(findChild(node, list));
            }
        }
        return tree;
    }

    /**
     * 递归集合，将组织机构list转为tree
     *
     * @param node
     * @param list
     * @return
     * @author lixiaolong
     * @date 2020/12/30 13:57
     */
    public static OrganizationTree findChild(OrganizationTree node, List<OrganizationTree> list) {
        for (OrganizationTree n : list) {
            if (node.getId().equals(n.getParentId()) || n.getParentId().equals(node.getId())) {
                if (node.getChildren() == null) {
                    node.setChildren(new ArrayList<>());
                }
                OrganizationTree child = findChild(n, list);
                node.getChildren().add(child);
                node.setCounts(node.getCounts() + n.getCounts());
            }
        }
        return node;
    }

}