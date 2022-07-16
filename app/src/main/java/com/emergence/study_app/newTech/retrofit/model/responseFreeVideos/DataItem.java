package com.emergence.study_app.newTech.retrofit.model.responseFreeVideos;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("delete_status")
	private String deleteStatus;

	@SerializedName("id")
	private String id;

	@SerializedName("coaching_id")
	private String coachingId;

	@SerializedName("title")
	private String title;

	@SerializedName("url")
	private String url;

	public String getDeleteStatus(){
		return deleteStatus;
	}

	public String getId(){
		return id;
	}

	public String getCoachingId(){
		return coachingId;
	}

	public String getTitle(){
		return title;
	}

	public String getUrl(){
		return url;
	}
}