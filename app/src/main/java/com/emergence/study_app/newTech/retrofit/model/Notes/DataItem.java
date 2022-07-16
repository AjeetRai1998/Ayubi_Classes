package com.emergence.study_app.newTech.retrofit.model.Notes;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("date")
	private String date;
	@SerializedName("download")
	private String download;

	@SerializedName("lecture_id")
	private String lectureId;

	@SerializedName("attachment")
	private String attachment;

	@SerializedName("delete_status")
	private String deleteStatus;

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

	public String getDate(){
		return date;
	}

	public String getLectureId(){
		return lectureId;
	}

	public String getAttachment(){
		return attachment;
	}

	public String getDeleteStatus(){
		return deleteStatus;
	}

	public String getId(){
		return id;
	}
	public String getDownload(){
		return download;
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
}