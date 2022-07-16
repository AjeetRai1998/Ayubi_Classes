package com.emergence.study_app.newTech.retrofit.model.responseComboOffer;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataItem{

	@SerializedName("date")
	private String date;

	@SerializedName("purchase")
	private String purchase;

	@SerializedName("expiry_date")
	private String expiry_date;

	@SerializedName("image")
	private String image;

	@SerializedName("package")
	private List<JsonMemberPackageItem> jsonMemberPackage;

	@SerializedName("description")
	private String description;

	@SerializedName("testseries")
	private List<TestseriesItem> testseries;

	@SerializedName("package_id")
	private String packageId;

	@SerializedName("coaching_id")
	private String coachingId;

	@SerializedName("total_mrp")
	private String totalMrp;

	@SerializedName("price")
	private String price;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	@SerializedName("time")
	private String time;

	@SerializedName("test_id")
	private String testId;

	@SerializedName("status")
	private String status;

	public String getDate(){
		return date;
	}
	public String getPurchase(){
		return purchase;
	}
	public String getExpiry_date(){
		return expiry_date;
	}

	public String getImage(){
		return image;
	}

	public List<JsonMemberPackageItem> getJsonMemberPackage(){
		return jsonMemberPackage;
	}

	public String getDescription(){
		return description;
	}

	public List<TestseriesItem> getTestseries(){
		return testseries;
	}

	public String getPackageId(){
		return packageId;
	}

	public String getCoachingId(){
		return coachingId;
	}

	public String getTotalMrp(){
		return totalMrp;
	}

	public String getPrice(){
		return price;
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

	public String getTestId(){
		return testId;
	}

	public String getStatus(){
		return status;
	}
}