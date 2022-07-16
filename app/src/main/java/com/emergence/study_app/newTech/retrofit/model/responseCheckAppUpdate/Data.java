package com.emergence.study_app.newTech.retrofit.model.responseCheckAppUpdate;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("id")
	private String id;

	@SerializedName("version")
	private String version;

	public String getId(){
		return id;
	}

	public String getVersion(){
		return version;
	}
}