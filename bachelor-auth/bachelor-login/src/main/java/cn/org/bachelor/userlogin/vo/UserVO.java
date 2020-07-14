package cn.org.bachelor.userlogin.vo;

public class UserVO {
	public Long id;
	public String name;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public UserVO() {
		super();
	}
	public UserVO(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
}
