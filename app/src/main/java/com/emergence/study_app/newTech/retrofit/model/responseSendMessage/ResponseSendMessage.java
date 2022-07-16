package com.emergence.study_app.newTech.retrofit.model.responseSendMessage;

import com.google.gson.annotations.SerializedName;

public class ResponseSendMessage{

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