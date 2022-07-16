package com.emergence.study_app.newTech.retrofit.model.responseExitLiveSession;

import com.google.gson.annotations.SerializedName;

public class ResponseExitLiveSession{

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