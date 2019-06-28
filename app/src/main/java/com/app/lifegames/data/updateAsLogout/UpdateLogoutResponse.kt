package com.app.lifegames.data.updateAsLogout


import com.google.gson.annotations.SerializedName


data class UpdateLogoutResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)