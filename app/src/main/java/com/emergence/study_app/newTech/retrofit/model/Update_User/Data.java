package com.emergence.study_app.newTech.retrofit.model.Update_User;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("date")
	private String date;

	@SerializedName("image")
	private String image;

	@SerializedName("gender")
	private String gender;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("student_id")
	private String studentId;

	@SerializedName("otp")
	private String otp;

	@SerializedName("delete_status")
	private String deleteStatus;

	@SerializedName("name")
	private String name;

	@SerializedName("coaching")
	private String coaching;

	@SerializedName("id")
	private String id;

	@SerializedName("time")
	private String time;

	@SerializedName("class")
	private String jsonMemberClass;

	@SerializedName("otp_status")
	private String otpStatus;

	@SerializedName("email")
	private String email;

	@SerializedName("status")
	private String status;

	public String getDate(){
		return date;
	}

	public String getImage(){
		return image;
	}

	public String getGender(){
		return gender;
	}

	public String getMobile(){
		return mobile;
	}

	public String getStudentId(){
		return studentId;
	}

	public String getOtp(){
		return otp;
	}

	public String getDeleteStatus(){
		return deleteStatus;
	}

	public String getName(){
		return name;
	}

	public String getCoaching(){
		return coaching;
	}

	public String getId(){
		return id;
	}

	public String getTime(){
		return time;
	}

	public String getJsonMemberClass(){
		return jsonMemberClass;
	}

	public String getOtpStatus(){
		return otpStatus;
	}

	public String getEmail(){
		return email;
	}

	public String getStatus(){
		return status;
	}
}