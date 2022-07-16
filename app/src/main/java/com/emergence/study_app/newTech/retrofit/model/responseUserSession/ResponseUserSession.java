package com.emergence.study_app.newTech.retrofit.model.responseUserSession;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseUserSession{

	@SerializedName("msg")
	private String msg;

	@SerializedName("res")
	private String res;

	@SerializedName("data")
	private List<Object> data;

	public String getMsg(){
		return msg;
	}

	public String getRes(){
		return res;
	}

	public List<Object> getData(){
		return data;
	}
}