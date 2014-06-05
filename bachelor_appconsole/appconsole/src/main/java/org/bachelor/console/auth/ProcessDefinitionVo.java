package org.bachelor.console.auth;

import java.io.Serializable;

public class ProcessDefinitionVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2184928602671030925L;

	/** unique identifier */
	private  String id;

	  /** label used for display purposes */
	private  String name;
	  
	  /** unique name for all versions this process definitions */
	private String key;
	  
	  /** description of this process **/
	private  String description;
	  
	  /** version of this process definition */
	private  int version;

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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
}
