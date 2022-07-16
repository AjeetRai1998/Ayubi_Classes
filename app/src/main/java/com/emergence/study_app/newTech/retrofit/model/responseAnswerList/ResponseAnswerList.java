package com.emergence.study_app.newTech.retrofit.model.responseAnswerList;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseAnswerList{

	@SerializedName("msg")
	private String msg;

	@SerializedName("res")
	private String res;

	@SerializedName("data")
	private List<DataItem> data;

	public String getMsg(){
		return msg;
	}

	public String getRes(){
		return res;
	}

	public List<DataItem> getData(){
		return data;
	}
}