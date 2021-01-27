package cn.org.bachelor.demo.web.service;

import cn.org.bachelor.demo.web.dao.TreeMapper;
import cn.org.bachelor.demo.web.domain.OrganizationTree;
import cn.org.bachelor.demo.web.utils.TreeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author 易罐
 */
@Service
public class TreeService {

    @Resource
    TreeMapper treeMapper;

    public List<OrganizationTree> findOrganizationTree() {
        List<OrganizationTree> list = treeMapper.findOrganizationTree();
        //将list转 tree结构
        return TreeUtil.buildOrganizationTree(list);
    }
}
