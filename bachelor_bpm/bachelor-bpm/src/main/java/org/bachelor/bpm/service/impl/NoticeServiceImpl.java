package org.bachelor.bpm.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bachelor.bpm.auth.IBpmOrg;
import org.bachelor.bpm.auth.IBpmRole;
import org.bachelor.bpm.auth.IBpmUser;
import org.bachelor.bpm.dao.INoticeDao;
import org.bachelor.bpm.domain.Notice;
import org.bachelor.bpm.service.IAuthService;
import org.bachelor.bpm.service.INoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
public class NoticeServiceImpl implements INoticeService {

	@Autowired
	private INoticeDao noticeDao;
	
	@Autowired
	private IAuthService authService;
	
	@Override
	public Notice publish(String title, String content, String Url,
			List<String> userIdList, String creatorId) {
		Notice notice = new Notice();
		IBpmUser user = authService.findUserById(creatorId);
		notice.setCreateTime(new Date());
		notice.setTitle(title);
		notice.setContent(content);
		notice.setUrl(Url);
		notice.setSenderId(user.getId());
		notice.setSenderName(user.getName());
		noticeDao.save(notice);
		saveNoticeReceiverInfo(userIdList,notice.getId());
		return notice;
	}
	
	/**
	 * 保存接收人员信息
	 * @param userIdList
	 * @param noticeId
	 */
	private void saveNoticeReceiverInfo(List<String> userIdList,String noticeId){
		if(userIdList!=null && userIdList.size()>0){
			String cSQL[] = new String[userIdList.size()];
			int index = 0;
			for(String userId:userIdList){
				IBpmUser user = authService.findUserById(userId);
				cSQL[index] = "insert into T_UFP_BPM_NOTICE_RECEIVER(id,NOTICE_ID,RECEIVER_ID,RECEIVER_NAME) values('"+
				UUID.randomUUID().toString()+"','"+noticeId+"','"+user.getId()+"','"+user.getName()+"'";
				index++;
			}
			if(cSQL!=null && cSQL.length>0){
				noticeDao.batchNoticeReceiver(cSQL);
			}
		}
	}
	
	/**
	 * 保存角色信息
	 * @param roleIdList
	 * @param companyIdList
	 * @param noticeId
	 */
	private void saveNoticeRoleInfo(List<String> roleIdList,List<String> companyIdList,String noticeId){
		if(roleIdList!=null && roleIdList.size()>0){
			String cSQL[] = new String[roleIdList.size()];
			int index = 0;
			for(String roleId:roleIdList){
				IBpmRole role = authService.findRoleById(roleId);
				IBpmOrg org = null;
				if(index<companyIdList.size()){
					org = authService.findOrgById(companyIdList.get(index));
				}
				cSQL[index] = "insert into T_UFP_BPM_NOTICE_ROLE(id,NOTICE_ID,ROLE_ID,ROLE_DESC,COMPANY_ID,COMPANY_NAME) values('"+
				UUID.randomUUID().toString()+"','"+noticeId+"','"+role.getId()+"','"+role.getName()+"',"+
						"'"+(org!=null?org.getId():null)+"','"+(org!=null?org.getName():null)+"'";
				index++;
			}
			if(cSQL!=null && cSQL.length>0){
				noticeDao.batchNoticeRole(cSQL);
			}
		}
	}
	@Override
	public Notice publish(String title, String content, String Url,String creatorId,
			List<String> roleIdList, List<String> companyIdList) {
		Notice notice = new Notice();
		IBpmUser user = authService.findUserById(creatorId);
		notice.setCreateTime(new Date());
		notice.setTitle(title);
		notice.setContent(content);
		notice.setUrl(Url);
		notice.setSenderId(user.getId());
		notice.setSenderName(user.getName());
		noticeDao.save(notice);
		saveNoticeRoleInfo(roleIdList,companyIdList,notice.getId());
		return notice;
	}

	@Override
	public void updateToRead(String noticeId, String readUserId) {
		Notice notice = noticeDao.findById(noticeId);
		IBpmUser user = authService.findUserById(readUserId);
		notice.setReadId(user.getId());
		notice.setReadName(user.getName());
		notice.setReadTime(new Date());
		noticeDao.update(notice);
	}

	@Override
	public void updateToDone(String noticeId, String doneUserId) {
		Notice notice = noticeDao.findById(noticeId);
		IBpmUser user = authService.findUserById(doneUserId);
		notice.setDoneId(user.getId());
		notice.setDoneName(user.getName());
		notice.setFinishTime(new Date());
		noticeDao.update(notice);
	}

	@Override
	@RequestMapping("findByUserId")
	public List<Notice> queryByUserId(String userId) {
		 
		return noticeDao.queryByUserId(userId);
	}

	@Override
	public List<Notice> queryByRoleId(String roleId) {
		 
		return noticeDao.queryByRoleId(roleId);
	}

	@Override
	public List<Notice> queryByExample(Notice notice) {
		 
		return noticeDao.queryByExample(notice);
	}


}
