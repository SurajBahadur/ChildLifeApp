package com.app.lifegames.data.markAsPlanner


import com.google.gson.annotations.SerializedName


data class MarkAsPlannerResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)