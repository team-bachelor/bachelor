package org.bachelor.ps.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;


/**
 * 业务域对象
 * 
 * @author user
 *
 */
@Entity
@Table(name = "T_UFP_PS_MODULE_EXT")
public class ProjectModuleExt {

	@Id
	@GenericGenerator(name="uuidGen", strategy="uuid")
	@GeneratedValue(generator="uuidGen")
	private String id;
	/** 扩展信息值 **/
	private String value;
	/** 模块对象标识。1：table，2：view，3：sequence，4：java package，5：jsp路径，6：flash路径 **/
	private String flag;
	/** 扩展信息描述 **/
	private String description;
	/** 模块ID **/
	@Column(name = "MODULE_ID")
	private String moduleId;
/*	@ManyToOne(cascade = {CascadeType.MERGE})
	@JoinColumn(name = "MODULE_ID")
	private ProjectModule extModule; 
	
	public ProjectModule getExtModule() {
		return extModule;
	}
	public void setExtModule(ProjectModule extModule) {
		this.extModule = extModule;
	}*/
	
	public String getId() {
		return id;
	}
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
