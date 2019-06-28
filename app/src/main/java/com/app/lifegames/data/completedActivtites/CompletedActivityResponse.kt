package com.app.lifegames.data.completedActivtites


import com.google.gson.annotations.SerializedName


data class CompletedActivityResponse(

	@field:SerializedName("data")
	val data: MutableList<DataItem>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)