package com.app.lifegames.data.login

import com.google.gson.annotations.SerializedName


data class ErrorResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("errors")
	val errors: Errors? = null
)