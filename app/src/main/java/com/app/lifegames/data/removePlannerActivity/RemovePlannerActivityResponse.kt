package com.app.lifegames.data.removePlannerActivity

import com.google.gson.annotations.SerializedName

data class RemovePlannerActivityResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)