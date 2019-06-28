package com.app.lifegames.data.selectedActivity


import com.google.gson.annotations.SerializedName


data class SelectedActivityResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)