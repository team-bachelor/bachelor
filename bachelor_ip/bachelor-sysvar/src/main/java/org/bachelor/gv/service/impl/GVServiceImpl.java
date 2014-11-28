package org.bachelor.gv.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.bachelor.gv.dao.IGVDao;
import org.bachelor.gv.domain.GlobalVariable;
import org.bachelor.gv.service.IGVService;

@Service
@RequestMapping("gv/variable/")
public class GVServiceImpl implements IGVService{

	@Autowired
	private IGVDao gvDao;
//	@Resource(name="redisTemplate")
//	private RedisTemplate<Serializable, Serializable> redisTemplate;
	
	@Override
	public List<GlobalVariable> queryAll(GlobalVariable gv) {
		List<GlobalVariable> gvList = gvDao.queryAll(gv);
//		for(GlobalVariable gv : gvList){
//			redisTemplate.opsForValue().set("GV:id:"+gv.getId(), gv);
//			redisTemplate.opsForValue().set("GV:name:"+gv.getName(), gv.getId());
//		}
		return gvList;
	}
	 
	@Override
	public void update(GlobalVariable gv) {
		gvDao.update(gv);
		
	}

	@Override
	public void save(GlobalVariable gv) {
		gvDao.save(gv);
	}

	@Override
	public void saveOrUpdate(GlobalVariable gv) {
		if(gv == null){
			return;
		}
		if(StringUtils.isEmpty(gv.getId())){
			gv.setId(null);
		}
		gvDao.saveOrUpdate(gv);
		
	}

	@Override
	public void delete(GlobalVariable gv) {
		gvDao.delete(gv);
	}

	@Override
	@RequestMapping("findById.htm")
	@ResponseBody
	//@Cacheable(value="ipCache")
	public GlobalVariable findById(String id) {
		GlobalVariable gv = gvDao.findById(id);
		return gv;
	}

	@Override
	@RequestMapping("findByName.htm")
	public GlobalVariable findByName(String name) {
		GlobalVariable gv = null;
		//String id = (String)redisTemplate.opsForValue().get("GV:name:"+name);
		//GlobalVariable gv = (GlobalVariable)redisTemplate.opsForValue().get("GV:id:"+id);
		//if(gv == null){
			List<GlobalVariable> gvList = gvDao.findByProperty("name", name);
			if(gvList.size() > 0){
				gv =  gvList.get(0);
			}
			
		//}else{
		//}
		return gv;
	}

	@Override
	public void batchDeleteById(String info) {
		String delInfo[] = info.split(",");
		String dSQL[] = new String[delInfo.length];
		for(int i=0;i<delInfo.length;i++){
			if(delInfo[i]!=null && !"".equals(delInfo[i])){
				dSQL[i] = "delete from t_bchlr_gv where id='"+delInfo[i]+"'";
			}
		}
		if(dSQL!=null && dSQL.length>0){
			gvDao.batchDeleteById(dSQL);
		}
	}

	@Override
	public boolean exist(String propertyName, String propertyValue) {
		
		return gvDao.exist(propertyName, propertyValue);
	}
}
