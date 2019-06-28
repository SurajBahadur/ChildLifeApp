package com.app.lifegames.data.login


import com.google.gson.annotations.SerializedName

data class Data(

	@field:SerializedName("premium_user")
	val premiumUser: Any? = null,

	@field:SerializedName("social_id")
	val socialId: Any? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("device_token")
	val deviceToken: String? = null,

	@field:SerializedName("id")
	val id: Any? = null,

	@field:SerializedName("type")
	val type: String? = null
)