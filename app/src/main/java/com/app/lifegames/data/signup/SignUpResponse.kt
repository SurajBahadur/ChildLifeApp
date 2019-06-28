package com.app.lifegames.data.signup

import com.google.gson.annotations.SerializedName


data class SignUpResponse(

	@field:SerializedName("user_info")
	val userInfo: UserInfo? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)