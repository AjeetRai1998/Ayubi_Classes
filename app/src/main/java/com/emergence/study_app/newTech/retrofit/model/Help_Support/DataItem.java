package com.emergence.study_app.newTech.retrofit.model.Help_Support;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("date")
	private String date;

	@SerializedName("help")
	private String help;

	@SerializedName("id")
	private String id;

	@SerializedName("time")
	private String time;

	public String getDate(){
		return date;
	}

	public String getHelp(){
		return help;
	}

	public String getId(){
		return id;
	}

	public String getTime(){
		return time;
	}
}