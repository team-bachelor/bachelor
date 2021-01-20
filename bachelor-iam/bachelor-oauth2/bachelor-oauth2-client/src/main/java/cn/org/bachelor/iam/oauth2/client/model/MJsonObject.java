package cn.org.bachelor.iam.oauth2.client.model;

import java.io.*;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class MJsonObject implements Serializable {


	private static final long serialVersionUID = -8266338435954087063L;
	private JsonObject jb;

	public MJsonObject(){}

	public MJsonObject(JsonObject jb){
		this.jb = jb;
	}
	
	public String getJsonValue(String key){
		JsonElement ele = jb.get(key);
		if(ele==null)return "";
		return ele.isJsonNull()?"":ele.getAsString();
	}

	public JsonObject getJb() {
		return jb;
	}

	public void setJb(JsonObject jb) {
		this.jb = jb;
	}
}
