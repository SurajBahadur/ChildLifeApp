package com.app.lifegames.data.payment

import com.google.gson.annotations.SerializedName


data class FinalPaymentResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)