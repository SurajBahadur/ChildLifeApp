package com.app.lifegames.data.selectiondata


import com.google.gson.annotations.SerializedName


data class SelectionCategoryResponse(

	@field:SerializedName("data")
	val data: MutableList<DataItem>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)