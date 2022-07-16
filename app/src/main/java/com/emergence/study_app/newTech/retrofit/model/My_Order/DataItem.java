package com.emergence.study_app.newTech.retrofit.model.My_Order;

import com.google.gson.annotations.SerializedName;

public class DataItem{
	@SerializedName("amount_order")
	private String amount_order;

	@SerializedName("combo")
	private String combo;

	@SerializedName("date")
	private String date;

	@SerializedName("exp_date")
	private String expDate;

	@SerializedName("include")
	private String include;

	@SerializedName("image")
	private String image;

	@SerializedName("method")
	private String method;

	@SerializedName("discount")
	private String discount;

	@SerializedName("description")
	private String description;

	@SerializedName("mrp")
	private String mrp;

	@SerializedName("package_id")
	private String packageId;

	@SerializedName("requirement")
	private String requirement;

	@SerializedName("coaching_id")
	private String coachingId;

	@SerializedName("image2")
	private String image2;

	@SerializedName("video_url")
	private String videoUrl;

	@SerializedName("wwlearn")
	private String wwlearn;

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

	@SerializedName("category")
	private String category;

	@SerializedName("order_id")
	private String orderId;

	@SerializedName("class")
	private String jsonMemberClass;

	@SerializedName("status")
	private String status;

	public String getAmount_order(){
		return amount_order;
	}

	public String getCombo(){
		return combo;
	}

	public String getDate(){
		return date;
	}
	public String getExpiry_date(){
		return expDate;
	}

	public String getInclude(){
		return include;
	}

	public String getImage(){
		return image;
	}

	public String getMethod(){
		return method;
	}

	public String getDiscount(){
		return discount;
	}

	public String getDescription(){
		return description;
	}

	public String getMrp(){
		return mrp;
	}

	public String getPackageId(){
		return packageId;
	}

	public String getRequirement(){
		return requirement;
	}

	public String getCoachingId(){
		return coachingId;
	}

	public String getImage2(){
		return image2;
	}

	public String getVideoUrl(){
		return videoUrl;
	}

	public String getWwlearn(){
		return wwlearn;
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

	public String getCategory(){
		return category;
	}

	public String getOrderId(){
		return orderId;
	}

	public String getJsonMemberClass(){
		return jsonMemberClass;
	}

	public String getStatus(){
		return status;
	}
}