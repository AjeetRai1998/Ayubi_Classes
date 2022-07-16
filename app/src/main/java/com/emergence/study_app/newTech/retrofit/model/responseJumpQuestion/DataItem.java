package com.emergence.study_app.newTech.retrofit.model.responseJumpQuestion;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("date")
	private String date;

	@SerializedName("image")
	private String image;

	@SerializedName("description")
	private String description;

	@SerializedName("minus_marking")
	private String minusMarking;

	@SerializedName("marks")
	private String marks;

	@SerializedName("coaching_id")
	private String coachingId;

	@SerializedName("attempt")
	private boolean attempt;

	@SerializedName("ansdescription")
	private String ansdescription;

	@SerializedName("answer")
	private String answer;

	@SerializedName("delete_status")
	private String deleteStatus;

	@SerializedName("options")
	private String options;

	@SerializedName("id")
	private String id;

	@SerializedName("time")
	private String time;

	@SerializedName("test_id")
	private String testId;

	@SerializedName("status")
	private String status;

	public String getDate(){
		return date;
	}

	public String getImage(){
		return image;
	}

	public String getDescription(){
		return description;
	}

	public String getMinusMarking(){
		return minusMarking;
	}

	public String getMarks(){
		return marks;
	}

	public String getCoachingId(){
		return coachingId;
	}

	public boolean isAttempt(){
		return attempt;
	}

	public String getAnsdescription(){
		return ansdescription;
	}

	public String getAnswer(){
		return answer;
	}

	public String getDeleteStatus(){
		return deleteStatus;
	}

	public String getOptions(){
		return options;
	}

	public String getId(){
		return id;
	}

	public String getTime(){
		return time;
	}

	public String getTestId(){
		return testId;
	}

	public String getStatus(){
		return status;
	}
}