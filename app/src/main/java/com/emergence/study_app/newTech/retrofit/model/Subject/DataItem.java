package com.emergence.study_app.newTech.retrofit.model.Subject;

import java.util.List;
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

	@SerializedName("video")
	private List<VideoItem> video;

	@SerializedName("coaching_id")
	private Object coachingId;

	@SerializedName("packages")
	private List<PackagesItem> packages;

	@SerializedName("class")
	private Object jsonMemberClass;

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

	public List<VideoItem> getVideo(){
		return video;
	}

	public Object getCoachingId(){
		return coachingId;
	}

	public List<PackagesItem> getPackages(){
		return packages;
	}

	public Object getJsonMemberClass(){
		return jsonMemberClass;
	}
}