package cn.org.bachelor.iam.oauth2.client.model;

import com.alibaba.fastjson.JSONObject;

import java.io.*;


public class MJsonObject implements Serializable {


	private static final long serialVersionUID = -8266338435954087063L;
	private JSONObject jb;

	public MJsonObject(){}

	public MJsonObject(JSONObject jb){
		this.jb = jb;
	}
	
	public String getJsonValue(String key){
		JSONObject ele = jb.getJSONObject(key);
		if(ele==null)return "";
		return ele.isEmpty()?"":ele.toJSONString();
	}

	public JSONObject getJb() {
		return jb;
	}

	public void setJb(JSONObject jb) {
		this.jb = jb;
	}
}
