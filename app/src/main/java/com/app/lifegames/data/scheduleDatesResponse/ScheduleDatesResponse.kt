package com.app.lifegames.data.scheduleDatesResponse


import com.google.gson.annotations.SerializedName

data class ScheduleDatesResponse(

	@field:SerializedName("date")
	val date: MutableList<String?>? = null,

	@field:SerializedName("data")
	val data: MutableList<String>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)