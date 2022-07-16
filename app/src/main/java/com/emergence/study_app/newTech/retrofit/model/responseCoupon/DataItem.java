package com.emergence.study_app.newTech.retrofit.model.responseCoupon;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("valid")
	private String valid;

	@SerializedName("date")
	private String date;

	@SerializedName("code")
	private String code;

	@SerializedName("criteria")
	private String criteria;

	@SerializedName("name")
	private String name;

	@SerializedName("discount")
	private String discount;

	@SerializedName("del_status")
	private String delStatus;

	@SerializedName("id")
	private String id;

	@SerializedName("time")
	private String time;

	@SerializedName("limits")
	private String limits;

	public String getValid(){
		return valid;
	}

	public String getDate(){
		return date;
	}

	public String getCode(){
		return code;
	}

	public String getCriteria(){
		return criteria;
	}

	public String getName(){
		return name;
	}

	public String getDiscount(){
		return discount;
	}

	public String getDelStatus(){
		return delStatus;
	}

	public String getId(){
		return id;
	}

	public String getTime(){
		return time;
	}

	public String getLimits(){
		return limits;
	}
}