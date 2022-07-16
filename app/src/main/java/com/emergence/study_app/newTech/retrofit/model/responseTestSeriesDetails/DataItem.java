package com.emergence.study_app.newTech.retrofit.model.responseTestSeriesDetails;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("date")
	private String date;

	@SerializedName("isAttempt")
	private String isAttempt;

	@SerializedName("testseries_name")
	private String testseriesName;

	@SerializedName("delete_status")
	private String deleteStatus;

	@SerializedName("name")
	private String name;

	@SerializedName("questions")
	private String questions;

	@SerializedName("minus_marking")
	private String minusMarking;

	@SerializedName("testseries")
	private String testseries;

	@SerializedName("id")
	private String id;

	@SerializedName("time")
	private String time;

	@SerializedName("coaching_id")
	private String coachingId;

	@SerializedName("timming")
	private String timming;

	public String getDate(){
		return date;
	}

	public String getIsAttempt(){
		return isAttempt;
	}

	public String getTestseriesName(){
		return testseriesName;
	}

	public String getDeleteStatus(){
		return deleteStatus;
	}

	public String getName(){
		return name;
	}

	public String getQuestions(){
		return questions;
	}

	public String getMinusMarking(){
		return minusMarking;
	}

	public String getTestseries(){
		return testseries;
	}

	public String getId(){
		return id;
	}

	public String getTime(){
		return time;
	}

	public String getCoachingId(){
		return coachingId;
	}

	public String getTimming(){
		return timming;
	}
}