package com.emergence.study_app.newTech.retrofit.model.TermsCondition;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("date")
	private String date;

	@SerializedName("terms")
	private String terms;

	@SerializedName("id")
	private String id;

	@SerializedName("time")
	private String time;

	public String getDate(){
		return date;
	}

	public String getTerms(){
		return terms;
	}

	public String getId(){
		return id;
	}

	public String getTime(){
		return time;
	}
}