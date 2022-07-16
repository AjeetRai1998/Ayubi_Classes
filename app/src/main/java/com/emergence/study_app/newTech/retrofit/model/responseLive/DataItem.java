package com.emergence.study_app.newTech.retrofit.model.responseLive;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("date")
	private String date;

	@SerializedName("image")
	private String image;

	@SerializedName("teacher_id")
	private String teacherId;

	@SerializedName("session")
	private String session;

	@SerializedName("packageid")
	private String packageid;

	@SerializedName("url")
	private String url;

	@SerializedName("duration")
	private String duration;

	@SerializedName("delete_status")
	private String deleteStatus;

	@SerializedName("livetime")
	private String livetime;

	@SerializedName("topic")
	private String topic;

	@SerializedName("id")
	private String id;

	@SerializedName("short_desc")
	private String shortDesc;

	@SerializedName("time")
	private String time;

	@SerializedName("livedate")
	private String livedate;

	public String getDate(){
		return date;
	}

	public String getImage(){
		return image;
	}

	public String getTeacherId(){
		return teacherId;
	}

	public String getSession(){
		return session;
	}

	public String getPackageid(){
		return packageid;
	}

	public String getUrl(){
		return url;
	}

	public String getDuration(){
		return duration;
	}

	public String getDeleteStatus(){
		return deleteStatus;
	}

	public String getLivetime(){
		return livetime;
	}

	public String getTopic(){
		return topic;
	}

	public String getId(){
		return id;
	}

	public String getShortDesc(){
		return shortDesc;
	}

	public String getTime(){
		return time;
	}

	public String getLivedate(){
		return livedate;
	}
}