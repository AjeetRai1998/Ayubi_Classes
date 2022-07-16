package com.emergence.study_app.newTech.retrofit.model.Notification;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("date")
	private String date;

	@SerializedName("image")
	private String image;

	@SerializedName("delete_status")
	private String deleteStatus;

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private String id;

	@SerializedName("time")
	private String time;

	@SerializedName("title")
	private String title;

	public String getDate(){
		return date;
	}

	public String getImage(){
		return image;
	}

	public String getDeleteStatus(){
		return deleteStatus;
	}

	public String getDescription(){
		return description;
	}

	public String getId(){
		return id;
	}

	public String getTime(){
		return time;
	}

	public String getTitle(){
		return title;
	}
}