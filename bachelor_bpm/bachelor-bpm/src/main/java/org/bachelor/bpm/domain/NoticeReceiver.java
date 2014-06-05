package org.bachelor.bpm.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_ufp_bpm_notice_receiver")
public class NoticeReceiver {
	
	@Id
	@GenericGenerator(name="uuidGen" , strategy="uuid")
	@GeneratedValue(generator="uuidGen")
	private String id;
	
	@ManyToOne(cascade={CascadeType.MERGE})
	@JoinColumn(name="notice_id")
	private Notice notice;
	
	@Column(name="receiver_id")
	private String receiverId;
	@Column(name="receiver_name")
	private String receiverName;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Notice getNotice() {
		return notice;
	}
	public void setNotice(Notice notice) {
		this.notice = notice;
	}
	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
}
