package com.emergence.study_app.newTech.retrofit.model.responseWalletPoints;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("id")
	private String id;

	@SerializedName("points")
	private String points;

	public String getId(){
		return id;
	}

	public String getPoints(){
		return points;
	}
}