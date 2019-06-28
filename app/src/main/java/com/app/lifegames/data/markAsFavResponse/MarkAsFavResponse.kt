package com.app.lifegames.data.markAsFavResponse


import com.google.gson.annotations.SerializedName

data class MarkAsFavResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)