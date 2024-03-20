package cn.org.bachelor.iam.acm.service;

import cn.org.bachelor.iam.acm.dao.ObjOperationMapper;
import cn.org.bachelor.iam.acm.domain.ObjOperation;
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
public class ObjOperationService {

    @Autowired
    private ObjOperationMapper operationMapper;


    /**
     * @param issys 是否为系统默认
     * @return
     */
    public List<ObjOperation> getOperations(Boolean issys) {
        ObjOperation exrm = new ObjOperation();
        exrm.setIsSys(issys);
        return operationMapper.select(exrm);
    }

    public List<ObjOperation> selectAll(){
        return operationMapper.selectAll();
    }

    public void saveOrUpdate(ObjOperation operation) {
        List<ObjOperation> list = new ArrayList<>(1);
        list.add(operation);
        saveOrUpdate(list);
    }

    public void saveOrUpdate(List<ObjOperation> operations) {
        //设置查询的样例
        List<ObjOperation> dbperms = selectAll();
        Map<String, ObjOperation> dbMap = new HashMap<>(dbperms.size());
        for (ObjOperation dbvalue : dbperms) {
            dbMap.put(dbvalue.getCode(), dbvalue);
        }
        for (ObjOperation newvalue : operations) {
            if(dbMap.containsKey(newvalue.getCode())){
                ObjOperation dbvalue = dbMap.get(newvalue.getCode());
                if(dbvalue.getIsSys() || "".equals(newvalue.getName())){
                    continue;
                }
                newvalue.setId(dbvalue.getId());
                operationMapper.updateByPrimaryKey(newvalue);
            }else{
                operationMapper.insert(newvalue);
            }

        }
    }

}
