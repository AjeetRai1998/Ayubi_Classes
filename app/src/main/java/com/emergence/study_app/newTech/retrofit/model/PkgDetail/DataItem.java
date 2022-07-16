package com.emergence.study_app.newTech.retrofit.model.PkgDetail;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("date")
	private String date;


	@SerializedName("include")
	private String include;

	@SerializedName("image")
	private String image;

	@SerializedName("total_lectures")
	private int totalLectures;

	@SerializedName("discount")
	private String discount;

	@SerializedName("description")
	private String description;

	@SerializedName("mrp")
	private String mrp;

	@SerializedName("requirement")
	private String requirement;

	@SerializedName("total_notes")
	private int totalNotes;

	@SerializedName("coaching_id")
	private String coachingId;

	@SerializedName("total_videos")
	private int totalVideos;

	@SerializedName("image2")
	private String image2;

	@SerializedName("video_url")
	private String videoUrl;

	@SerializedName("wwlearn")
	private String wwlearn;

	@SerializedName("price")
	private String price;

	@SerializedName("delete_status")
	private String deleteStatus;

	@SerializedName("name")
	private String name;

	@SerializedName("total_chapters")
	private int totalChapters;

	@SerializedName("id")
	private String id;

	@SerializedName("time")
	private String time;

	@SerializedName("category")
	private String category;

	@SerializedName("class")
	private String jsonMemberClass;

	public String getDate(){
		return date;
	}


	public String getInclude(){
		return include;
	}

	public String getImage(){
		return image;
	}

	public int getTotalLectures(){
		return totalLectures;
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

	public String getRequirement(){
		return requirement;
	}

	public int getTotalNotes(){
		return totalNotes;
	}

	public String getCoachingId(){
		return coachingId;
	}

	public int getTotalVideos(){
		return totalVideos;
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

	public String getPrice(){
		return price;
	}

	public String getDeleteStatus(){
		return deleteStatus;
	}

	public String getName(){
		return name;
	}

	public int getTotalChapters(){
		return totalChapters;
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

	public String getJsonMemberClass(){
		return jsonMemberClass;
	}
}