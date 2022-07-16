package com.emergence.study_app.newTech.retrofit.model.videoPackageOrder;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("date")
	private String date;

	@SerializedName("discount")
	private String discount;

	@SerializedName("description")
	private String description;

	@SerializedName("package_id")
	private String packageId;

	@SerializedName("coaching_id")
	private String coachingId;

	@SerializedName("type")
	private String type;

	@SerializedName("coupon_details")
	private Object couponDetails;

	@SerializedName("video_url")
	private String videoUrl;

	@SerializedName("wwlearn")
	private String wwlearn;

	@SerializedName("coupon_id")
	private String couponId;

	@SerializedName("price")
	private String price;

	@SerializedName("delete_status")
	private String deleteStatus;

	@SerializedName("exp_date")
	private String expDate;

	@SerializedName("id")
	private String id;

	@SerializedName("order_type")
	private String orderType;

	@SerializedName("class")
	private String jsonMemberClass;

	@SerializedName("include")
	private String include;

	@SerializedName("image")
	private String image;

	@SerializedName("wallet")
	private String wallet;

	@SerializedName("method")
	private String method;

	@SerializedName("coupon")
	private String coupon;

	@SerializedName("combo")
	private String combo;

	@SerializedName("mrp")
	private String mrp;

	@SerializedName("requirement")
	private String requirement;

	@SerializedName("image2")
	private String image2;

	@SerializedName("active_status")
	private String activeStatus;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("amount_order")
	private String amountOrder;

	@SerializedName("name")
	private String name;

	@SerializedName("time")
	private String time;

	@SerializedName("category")
	private String category;

	@SerializedName("order_id")
	private String orderId;

	@SerializedName("status")
	private String status;

	@SerializedName("wallet_amount")
	private String walletAmount;

	public String getDate(){
		return date;
	}

	public String getDiscount(){
		return discount;
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

	public String getType(){
		return type;
	}

	public Object getCouponDetails(){
		return couponDetails;
	}

	public String getVideoUrl(){
		return videoUrl;
	}

	public String getWwlearn(){
		return wwlearn;
	}

	public String getCouponId(){
		return couponId;
	}

	public String getPrice(){
		return price;
	}

	public String getDeleteStatus(){
		return deleteStatus;
	}

	public String getExpDate(){
		return expDate;
	}

	public String getId(){
		return id;
	}

	public String getOrderType(){
		return orderType;
	}

	public String getJsonMemberClass(){
		return jsonMemberClass;
	}

	public String getInclude(){
		return include;
	}

	public String getImage(){
		return image;
	}

	public String getWallet(){
		return wallet;
	}

	public String getMethod(){
		return method;
	}

	public String getCoupon(){
		return coupon;
	}

	public String getCombo(){
		return combo;
	}

	public String getMrp(){
		return mrp;
	}

	public String getRequirement(){
		return requirement;
	}

	public String getImage2(){
		return image2;
	}

	public String getActiveStatus(){
		return activeStatus;
	}

	public String getUserId(){
		return userId;
	}

	public String getAmountOrder(){
		return amountOrder;
	}

	public String getName(){
		return name;
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

	public String getStatus(){
		return status;
	}

	public String getWalletAmount(){
		return walletAmount;
	}
}