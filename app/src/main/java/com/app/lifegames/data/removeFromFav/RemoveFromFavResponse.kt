package com.app.lifegames.data.removeFromFav


import com.google.gson.annotations.SerializedName


data class RemoveFromFavResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)