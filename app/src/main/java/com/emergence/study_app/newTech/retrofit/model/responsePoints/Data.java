package com.emergence.study_app.newTech.retrofit.model.responsePoints;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("amount")
	private String amount;

	@SerializedName("id")
	private String id;

	@SerializedName("points")
	private String points;

	public String getAmount(){
		return amount;
	}

	public String getId(){
		return id;
	}

	public String getPoints(){
		return points;
	}
}