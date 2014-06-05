package org.bachelor.auth.vo;

public class RoleVo {
	
	
	/** 主键 **/
	private String id;
	/** 角色名称 **/
	private String name;
	/** 角色描述 **/
	private String description;
	/** 备注 **/
	private String memo;
	
	private String descripton;
	
	public String getDescripton() {
		return descripton;
	}
	public void setDescripton(String descripton) {
		this.descripton = descripton;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
}
