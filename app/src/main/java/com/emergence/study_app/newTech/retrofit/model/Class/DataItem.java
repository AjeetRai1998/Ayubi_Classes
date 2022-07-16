package com.emergence.study_app.newTech.retrofit.model.Class;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("date")
	private String date;

	@SerializedName("delete_status")
	private String deleteStatus;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	@SerializedName("time")
	private String time;

	public String getDate(){
		return date;
	}

	public String getDeleteStatus(){
		return deleteStatus;
	}

	public String getName(){
		return name;
	}

	public String getId(){
		return id;
	}

	public String getTime(){
		return time;
	}
}