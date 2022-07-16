package com.emergence.study_app.newTech.retrofit.model.responseRank;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("Email")
	private String email;

	@SerializedName("total_marks")
	private int totalMarks;

	@SerializedName("Marks")
	private int marks;

	@SerializedName("Mobile")
	private String mobile;

	@SerializedName("Name")
	private String name;

	public String getEmail(){
		return email;
	}

	public int getTotalMarks(){
		return totalMarks;
	}

	public int getMarks(){
		return marks;
	}

	public String getMobile(){
		return mobile;
	}

	public String getName(){
		return name;
	}
}