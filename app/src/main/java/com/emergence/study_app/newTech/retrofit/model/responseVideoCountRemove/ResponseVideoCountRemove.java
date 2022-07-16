package com.emergence.study_app.newTech.retrofit.model.responseVideoCountRemove;

import com.google.gson.annotations.SerializedName;

public class ResponseVideoCountRemove{

	@SerializedName("msg")
	private String msg;

	@SerializedName("res")
	private String res;

	public String getMsg(){
		return msg;
	}

	public String getRes(){
		return res;
	}
}