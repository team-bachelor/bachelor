package org.bachelor.stat.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.bachelor.auth.common.AuthConstant;
import org.bachelor.context.service.IVLService;
import org.bachelor.stat.dao.IStatUserTrackDao;
import org.bachelor.stat.domain.StatUserTrack;
import org.bachelor.stat.service.IStatUserTrackService;

/**
 *  功能拦截器 
 * @author user
 *
 */
@Service
public class StatUserTrackImplService implements IStatUserTrackService {

	@Autowired
	private IVLService vlService;
	
	@Autowired
	private IStatUserTrackDao statUserTrackDao;
	
	/**
	 * 存储功能拦截器信息
	 */
	@Override
	public void save(StatUserTrack sut) {
		 
		statUserTrackDao.save(sut);
	}

	/**
	 * 把StatUserTrack实体以链表的形式存储至Session
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void saveUserTrackFuncEntity(StatUserTrack sut) {
		LinkedList<StatUserTrack> suts =  (LinkedList<StatUserTrack>) vlService.getSessionAttribute(AuthConstant.USER_TRACK_FUNC_ENTITY);
		/** 如果session中没有存储链表时，对链表进行初始化操作 **/
		if(suts==null || suts.size()<=0){
			suts = new LinkedList<StatUserTrack>();
		}
		/** 如果链表值大于指定链表大小时，删除最后一个实体 **/
		if(suts!=null && suts.size()>62){
			suts.removeLast();
		}
		/**  把实体添加至链表的第一位 **/
		suts.addFirst(sut);
		/**  存储至session中 **/
		vlService.setSessionAttribute(AuthConstant.USER_TRACK_FUNC_ENTITY, suts);
	}

	/**
	 * 从session得到StatUserTrack实体.
	 * near为空返回前一个实体，反之返回near数范围的实体
	 */
	@SuppressWarnings("unchecked")
	@Override
	public StatUserTrack getLasted() {
		LinkedList<StatUserTrack> suts =  (LinkedList<StatUserTrack>) vlService.getSessionAttribute(AuthConstant.USER_TRACK_FUNC_ENTITY);
		if(suts!=null && suts.size()>0){
			return suts.getLast();
		}
		return null;
	}

	/**
	 * 得到指定数量值
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StatUserTrack> getLasted(int num) {
		LinkedList<StatUserTrack> suts =  (LinkedList<StatUserTrack>) vlService.getSessionAttribute(AuthConstant.USER_TRACK_FUNC_ENTITY);
		/** 如果链表为空或者没有值时返回当前对象 **/
		if(suts==null || suts.size()<=0){
			
			return suts;
		}
		List<StatUserTrack> sut_list = new ArrayList<StatUserTrack>();
		/**  如果链表中值小于参数值时，把链表大小赋值给参数 **/
		if(suts.size()<num){
			num = suts.size();
		}
		/** 循环得到实体 **/
		for(int i=1;i<=num;i++){ 
			sut_list.add(suts.get((suts.size()-i)));
		}
		return sut_list;
	}

	/**
	 * 得到当前值的前一个对象
	 */
	@SuppressWarnings("unchecked")
	@Override
	public StatUserTrack getPre() {
		LinkedList<StatUserTrack> suts =  (LinkedList<StatUserTrack>) vlService.getSessionAttribute(AuthConstant.USER_TRACK_FUNC_ENTITY);
		if(suts!=null && suts.size()>1){
			return suts.get(suts.size()-2);
		}
		return null;
	}

}
