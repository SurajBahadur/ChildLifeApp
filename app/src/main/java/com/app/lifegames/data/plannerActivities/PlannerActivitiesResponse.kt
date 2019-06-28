package com.app.lifegames.data.plannerActivities

import com.google.gson.annotations.SerializedName


data class PlannerActivitiesResponse(

	@field:SerializedName("data")
	val data: MutableList<DataItem>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)