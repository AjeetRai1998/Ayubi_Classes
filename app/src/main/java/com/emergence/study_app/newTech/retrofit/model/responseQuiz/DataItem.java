package com.emergence.study_app.newTech.retrofit.model.responseQuiz;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("date")
	private String date;

	@SerializedName("status_check")
	private String statusCheck;

	@SerializedName("questions")
	private String questions;

	@SerializedName("minus_marking")
	private String minusMarking;

	@SerializedName("coaching_id")
	private String coachingId;

	@SerializedName("result")
	private String result;

	@SerializedName("delete_status")
	private String deleteStatus;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	@SerializedName("time")
	private String time;

	@SerializedName("quiz_open")
	private String quizOpen;

	@SerializedName("timming")
	private String timming;

	@SerializedName("status")
	private String status;

	public String getDate(){
		return date;
	}

	public String getStatusCheck(){
		return statusCheck;
	}

	public String getQuestions(){
		return questions;
	}

	public String getMinusMarking(){
		return minusMarking;
	}

	public String getCoachingId(){
		return coachingId;
	}

	public String getResult(){
		return result;
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

	public String getQuizOpen(){
		return quizOpen;
	}

	public String getTimming(){
		return timming;
	}

	public String getStatus(){
		return status;
	}
}