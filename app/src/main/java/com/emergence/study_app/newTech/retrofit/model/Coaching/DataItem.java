package com.emergence.study_app.newTech.retrofit.model.Coaching;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("date")
	private String date;

	@SerializedName("image")
	private String image;

	@SerializedName("address")
	private String address;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("otp")
	private String otp;

	@SerializedName("password")
	private String password;

	@SerializedName("delete_status")
	private String deleteStatus;

	@SerializedName("login_time")
	private String loginTime;

	@SerializedName("admin_id")
	private String adminId;

	@SerializedName("name")
	private String name;

	@SerializedName("logout_time")
	private String logoutTime;

	@SerializedName("id")
	private String id;

	@SerializedName("time")
	private String time;

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

	public String getAddress(){
		return address;
	}

	public String getMobile(){
		return mobile;
	}

	public String getOtp(){
		return otp;
	}

	public String getPassword(){
		return password;
	}

	public String getDeleteStatus(){
		return deleteStatus;
	}

	public String getLoginTime(){
		return loginTime;
	}

	public String getAdminId(){
		return adminId;
	}

	public String getName(){
		return name;
	}

	public String getLogoutTime(){
		return logoutTime;
	}

	public String getId(){
		return id;
	}

	public String getTime(){
		return time;
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