package org.bachelor.demo.devman.bp;

import org.bachelor.bpm.domain.BaseBpDataEx;

public class DevManBpDataEx extends BaseBpDataEx{


	/**
	 * 
	 */
	private static final long serialVersionUID = 7477538120876832868L;

	private String dwId;
	
	private String devMantGroup;
	
	private String verifyRoles;
	

	public String getDwId() {
		return dwId;
	}

	public void setDwId(String dwId) {
		this.dwId = dwId;
	}

	public String getDevMantGroup() {
		return devMantGroup;
	}

	public void setDevMantGroup(String devMantGroup) {
		this.devMantGroup = devMantGroup;
	}

	public String getVerifyRoles() {
		return verifyRoles;
	}

	public void setVerifyRoles(String verifyRoles) {
		this.verifyRoles = verifyRoles;
	}
	
	
}
