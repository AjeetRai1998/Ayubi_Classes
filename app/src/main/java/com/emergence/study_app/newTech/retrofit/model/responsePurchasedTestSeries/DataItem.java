package com.emergence.study_app.newTech.retrofit.model.responsePurchasedTestSeries;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("date")
	private String date;

	@SerializedName("image")
	private String image;

	@SerializedName("expirydate")
	private String expirydate;

	@SerializedName("method")
	private String method;

	@SerializedName("description")
	private String description;

	@SerializedName("package_id")
	private String packageId;

	@SerializedName("coaching_id")
	private String coachingId;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("price")
	private String price;

	@SerializedName("delete_status")
	private String deleteStatus;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	@SerializedName("time")
	private String time;

	@SerializedName("order_id")
	private String orderId;

	@SerializedName("order_type")
	private String orderType;

	@SerializedName("status")
	private String status;

	public String getDate(){
		return date;
	}

	public String getImage(){
		return image;
	}

	public String getExpirydate(){
		return expirydate;
	}

	public String getMethod(){
		return method;
	}

	public String getDescription(){
		return description;
	}

	public String getPackageId(){
		return packageId;
	}

	public String getCoachingId(){
		return coachingId;
	}

	public String getUserId(){
		return userId;
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

	public String getId(){
		return id;
	}

	public String getTime(){
		return time;
	}

	public String getOrderId(){
		return orderId;
	}

	public String getOrderType(){
		return orderType;
	}

	public String getStatus(){
		return status;
	}
}