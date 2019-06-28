package com.app.lifegames.data.favouriteActivities


import com.google.gson.annotations.SerializedName

data class DataItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("overview")
	val overview: String? = null,

	@field:SerializedName("cat_color")
	val catColor: String? = null,

	@field:SerializedName("cat_name")
	val catName: String? = null,

	@field:SerializedName("age_group")
	val ageGroup: String? = null,

	@field:SerializedName("activity_details")
	val activityDetails: String? = null,

	@field:SerializedName("warnings")
	val warnings: String? = null,

	@field:SerializedName("completed")
	val completed: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("cat_value")
	val catValue: String? = null,

	@field:SerializedName("cat_icon")
	val catIcon: String? = null,

	@field:SerializedName("favourite")
	val favourite: String? = null,

	@field:SerializedName("materials")
	val materials: String? = null,

	@field:SerializedName("ratings")
	val ratings: String? = null,

	@field:SerializedName("age_group_icon")
	val ageGroupIcon: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("time")
	val time: String? = null
)