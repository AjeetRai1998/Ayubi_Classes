package com.emergence.study_app.newTech.retrofit.model.Lacture;

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

	@SerializedName("package_id")
	private String packageId;

	@SerializedName("chapter_id")
	private String chapterId;

	@SerializedName("time")
	private String time;

	@SerializedName("coaching_id")
	private String coachingId;

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

	public String getPackageId(){
		return packageId;
	}

	public String getChapterId(){
		return chapterId;
	}

	public String getTime(){
		return time;
	}

	public String getCoachingId(){
		return coachingId;
	}
}