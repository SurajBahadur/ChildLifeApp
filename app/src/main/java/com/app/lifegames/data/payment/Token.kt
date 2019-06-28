package com.app.lifegames.data.payment


import com.google.gson.annotations.SerializedName


data class Token(

	@field:SerializedName("Credit_card_ID")
	val creditCardID: String? = null,

	@field:SerializedName("clientToken")
	val clientToken: String? = null,

	@field:SerializedName("customerId")
	val customerId: String? = null
)