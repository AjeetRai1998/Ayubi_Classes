package com.emergence.study_app.newTech.retrofit.model.responseAnswer;

import com.google.gson.annotations.SerializedName;

public class ResponseAnswer {

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