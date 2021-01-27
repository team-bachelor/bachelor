package cn.org.bachelor.demo.web.utils;

import cn.org.bachelor.demo.web.domain.BaseMenu;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gxf on 2021/1/18 9:50
 */
@Data
public class ChildrenListUtil {

    public static List<BaseMenu> toTree02(List<BaseMenu> treeList) {
        List<BaseMenu> retList = new ArrayList<>();
        for (BaseMenu parent : treeList) {
            if (parent.getParentID() == null) {
                retList.add(findChildren(parent, treeList));
            }
        }
        return retList;
    }

    private static BaseMenu findChildren(BaseMenu parent, List<BaseMenu> treeList) {
        for (BaseMenu child : treeList) {
            if (parent.getId().equals(child.getParentID())) {
                if (parent.getChildren() == null) {
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(findChildren(child, treeList));
            }
        }
        return parent;
    }


}
