package cn.org.bachelor.iam.acm.service;

import cn.org.bachelor.iam.acm.dao.ObjPermissionMapper;
import cn.org.bachelor.iam.acm.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2021/02/07
 */
@Service
public class ObjPermissionService {

    @Autowired
    private ObjPermissionMapper permissionMapper;


    /**
     * @param issys 是否为系统默认
     * @return
     */
    public List<ObjPermission> getPermissions(Boolean issys) {
        ObjPermission exrm = new ObjPermission();
        exrm.setIsSys(issys);
        return permissionMapper.select(exrm);
    }
    public List<ObjPermission> selectAll(){
        return permissionMapper.selectAll();
    }
    public void saveOrUpdate(ObjPermission permission) {
        List<ObjPermission> list = new ArrayList<>(1);
        list.add(permission);
        saveOrUpdate(list);
    }
    /**
     * @param permissions
     */
    public void saveOrUpdate(List<ObjPermission> permissions) {
        //设置查询的样例
        List<ObjPermission> dbperms = getPermissions(false);
        Map<String, ObjPermission> dbMap = new HashMap<>(dbperms.size());
        for (ObjPermission dbperm : dbperms) {
            dbMap.put(dbperm.getCode(), dbperm);
        }
        for (ObjPermission newperm : permissions) {
            if(dbMap.containsKey(newperm.getCode())){
                ObjPermission dbperm = dbMap.get(newperm.getCode());
                newperm.setId(dbperm.getId());
                permissionMapper.updateByPrimaryKey(newperm);
            }else{
                permissionMapper.insert(newperm);
            }

        }
    }

}
