package cn.org.bachelor.iam.acm.service;

import cn.org.bachelor.iam.acm.dao.ObjDomainMapper;
import cn.org.bachelor.iam.acm.domain.ObjDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author liuzhuo
 * @创建时间: 2021/02/07
 */
@Service
public class ObjDomainService {

    @Autowired
    private ObjDomainMapper domainMapper;


    /**
     *
     * @param issys 是否为系统默认
     * @return
     */
    public List<ObjDomain> getDomains(Boolean issys) {
        ObjDomain exrm = new ObjDomain();
        exrm.setIsSys(issys);
        return domainMapper.select(exrm);
    }
    public List<ObjDomain> selectAll(){
        return domainMapper.selectAll();
    }
    public void saveOrUpdate(ObjDomain domain) {
        List<ObjDomain> list = new ArrayList<>(1);
        list.add(domain);
        saveOrUpdate(list);
    }

    public void saveOrUpdate(List<ObjDomain> domains) {
        //设置查询的样例
        List<ObjDomain> dbperms = getDomains(false);
        Map<String, ObjDomain> dbMap = new HashMap<>(dbperms.size());
        for (ObjDomain dbvalue : dbperms) {
            dbMap.put(dbvalue.getCode(), dbvalue);
        }
        for (ObjDomain newvalue : domains) {
            if(dbMap.containsKey(newvalue.getCode())){
                ObjDomain dbvalue = dbMap.get(newvalue.getCode());
                newvalue.setId(dbvalue.getId());
                domainMapper.updateByPrimaryKey(newvalue);
            }else{
                domainMapper.insert(newvalue);
            }

        }
    }

}
