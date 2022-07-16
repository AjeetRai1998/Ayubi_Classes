package com.emergence.study_app.newTech.retrofit.model.Video;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("date")
	private String date;

	@SerializedName("view_complete")
	private String view_complete;

	@SerializedName("lecture_id")
	private String lectureId;

	@SerializedName("delete_status")
	private String deleteStatus;

	@SerializedName("video_count")
	private String videoCount;

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

	@SerializedName("title")
	private String title;

	@SerializedName("url")
	private String url;

	public String getDate(){
		return date;
	}

	public String getLectureId(){
		return lectureId;
	}

	public String getDeleteStatus(){
		return deleteStatus;
	}

	public String getVideoCount(){
		return videoCount;
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

	public String getTitle(){
		return title;
	}

	public String getUrl(){
		return url;
	}
	public String getView_complete(){
		return view_complete;
	}
}