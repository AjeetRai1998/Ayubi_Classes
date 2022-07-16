package com.emergence.study_app.newTech.retrofit.model.Get_User;

import com.google.gson.annotations.SerializedName;

public class ResponseGetUser{

	@SerializedName("msg")
	private String msg;

	@SerializedName("res")
	private String res;

	@SerializedName("data")
	private Data data;

	public String getMsg(){
		return msg;
	}

	public String getRes(){
		return res;
	}

	public Data getData(){
		return data;
	}
}