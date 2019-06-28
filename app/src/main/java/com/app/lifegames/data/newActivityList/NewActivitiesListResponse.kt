package com.app.lifegames.data.newActivityList


import com.google.gson.annotations.SerializedName


data class NewActivitiesListResponse(

	@field:SerializedName("data")
	val data: MutableList<DataItem>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)