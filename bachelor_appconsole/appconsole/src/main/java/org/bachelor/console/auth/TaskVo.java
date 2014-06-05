package org.bachelor.console.auth;

import java.io.Serializable;

public class TaskVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8321816184120652213L;

	
	private	String key;
	
	  /** Name or title of the task. */
	private	String name;

	  /** Free text description of the task. */
	private	String description;

	

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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
}
