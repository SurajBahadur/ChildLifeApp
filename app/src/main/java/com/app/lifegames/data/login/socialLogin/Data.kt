package com.app.lifegames.data.login.socialLogin


import com.google.gson.annotations.SerializedName

data class Data(

	@field:SerializedName("premium_user")
	val premiumUser: Any? = null,

	@field:SerializedName("social_id")
	val socialId: String? = null,

	@field:SerializedName("social_type")
	val socialType: String? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("device_token")
	val deviceToken: String? = null,

	@field:SerializedName("type")
	val type: String? = null
)