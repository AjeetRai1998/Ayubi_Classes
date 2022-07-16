package com.emergence.study_app.newTech.retrofit.model.Update_profile;

import com.google.gson.annotations.SerializedName;

public class ResponseUpdateProfile{

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