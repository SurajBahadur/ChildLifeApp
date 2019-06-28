package com.app.lifegames.data.saveDeviceID

import com.google.gson.annotations.SerializedName


data class SaveDeviceResponse(

	@field:SerializedName("login_status")
	val loginStatus: String? = null,

	@field:SerializedName("user_type")
	val userType: String? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)