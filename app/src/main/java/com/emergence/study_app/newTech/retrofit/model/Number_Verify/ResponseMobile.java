package com.emergence.study_app.newTech.retrofit.model.Number_Verify;

import com.google.gson.annotations.SerializedName;

public class ResponseMobile{

	@SerializedName("msg")
	private String msg;

	@SerializedName("res")
	private String res;

	@SerializedName("logintype")
	private String logintype;

	public String getMsg(){
		return msg;
	}

	public String getRes(){
		return res;
	}

	public String getLogintype(){
		return logintype;
	}
}