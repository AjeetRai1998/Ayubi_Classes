package com.emergence.study_app.newTech.retrofit.model.responseTestSeriese;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("date")
	private String date;

	@SerializedName("image")
	private String image;

	@SerializedName("expirydate")
	private String expirydate;

	@SerializedName("price")
	private String price;

	@SerializedName("delete_status")
	private String deleteStatus;

	@SerializedName("name")
	private String name;

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private String id;

	@SerializedName("package_id")
	private String packageId;

	@SerializedName("time")
	private String time;

	@SerializedName("coaching_id")
	private String coachingId;

	public String getDate(){
		return date;
	}

	public String getImage(){
		return image;
	}

	public String getExpirydate(){
		return expirydate;
	}

	public String getPrice(){
		return price;
	}

	public String getDeleteStatus(){
		return deleteStatus;
	}

	public String getName(){
		return name;
	}

	public String getDescription(){
		return description;
	}

	public String getId(){
		return id;
	}

	public String getPackageId(){
		return packageId;
	}

	public String getTime(){
		return time;
	}

	public String getCoachingId(){
		return coachingId;
	}
}