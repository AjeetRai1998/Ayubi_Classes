package com.emergence.study_app.newTech.retrofit.model.responseComboOffer;

import com.google.gson.annotations.SerializedName;

public class JsonMemberPackageItem{

	@SerializedName("image")
	private String image;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	public String getImage(){
		return image;
	}

	public String getName(){
		return name;
	}

	public String getId(){
		return id;
	}
}