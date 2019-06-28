package com.app.lifegames.data.rateActivity
import com.google.gson.annotations.SerializedName


data class RateActivityResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)