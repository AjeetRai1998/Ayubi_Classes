package com.emergence.study_app.newTech.retrofit.model.Privacy_Policy;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("date")
	private String date;

	@SerializedName("privacy")
	private String privacy;

	@SerializedName("id")
	private String id;

	@SerializedName("time")
	private String time;

	public String getDate(){
		return date;
	}

	public String getPrivacy(){
		return privacy;
	}

	public String getId(){
		return id;
	}

	public String getTime(){
		return time;
	}
}