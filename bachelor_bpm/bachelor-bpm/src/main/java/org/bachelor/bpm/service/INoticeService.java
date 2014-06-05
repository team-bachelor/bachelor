package org.bachelor.bpm.service;

import java.util.List;

import org.bachelor.bpm.domain.Notice;

/**
 * 通知服务接口
 * @author user
 *
 */
public interface INoticeService {

	/**
	 * 创建新通知
	 * 将通知发送给指定的用户
	 * 
	 * @param title 通知标题
	 * @param content 通知内容
	 * @param Url 关联Url
	 * @param userIdList 接收通知的用户ID的List
	 * @param creatorId 创建者Id
	 * @return 通知实体
	 */
	public Notice publish(String title, String content, String Url, List<String> userIdList, String creatorId);

	/**
	 * 创建新通知
	 * 将通知发送给指定公司和角色所包含的用户。
	 * roleIdList和companyIdList中的参数必须是一一对应。
	 * 如果是发送给全局的角色，那么对应的companyIdList的值设置为null。
	 * 
	 * @param title 通知标题
	 * @param content 通知内容
	 * @param Url 关联Url
	 * @param roleIdList 接收通知的角色ID的List
	 * @param companyIdList 接收通知的公司ID的List
	 * @return 通知实体
	 */
	public Notice publish(String title, String content, String Url,String creatorId, List<String> roleIdList, List<String> companyIdList);
	
	/**
	 * 将通知设置为已阅
	 * 如果通知已经是已阅，则此方法不做任何操作
	 * 
	 * @param noticeId 通知Id
	 * @param readUserId 执行已阅的用户Id
	 */
	public void updateToRead(String noticeId, String readUserId);
	
	/**
	 * 将通知设置为已办
	 * 如果通知已经是已办，则此方法不做任何操作
	 * 
	 * @param noticeId 通知Id
	 * @param doneUserId 执行已办的用户Id
	 */
	public void updateToDone(String noticeId, String doneUserId);
	
	/**
	 * 查询指定用户Id以及所具有角色相关的通知
	 * 
	 * @param userId 用户Id
	 * @return 通知实体List
	 */
	public List<Notice> queryByUserId(String userId);
	
	/**
	 * 查询指定角色Id相关的通知
	 * 
	 * @param roleId 角色Id
	 * @return 通知实体List
	 */
	public List<Notice> queryByRoleId(String roleId);
	
	/**
	 * 根据Notice实体查询Notice信息集合
	 * @param notice
	 * @return
	 */
	public List<Notice> queryByExample(Notice notice);

}
