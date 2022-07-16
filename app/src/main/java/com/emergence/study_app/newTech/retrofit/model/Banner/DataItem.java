package com.emergence.study_app.newTech.retrofit.model.Banner;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("date")
	private String date;

	@SerializedName("image")
	private String image;

	@SerializedName("delete_status")
	private String deleteStatus;

	@SerializedName("admin_id")
	private String adminId;

	@SerializedName("id")
	private String id;

	@SerializedName("time")
	private String time;

	@SerializedName("package_id")
	private String packageId;

	public String getDate(){
		return date;
	}

	public String getImage(){
		return image;
	}

	public String getDeleteStatus(){
		return deleteStatus;
	}

	public String getAdminId(){
		return adminId;
	}

	public String getId(){
		return id;
	}

	public String getTime(){
		return time;
	}

	public String getPackageId(){
		return packageId;
	}
}