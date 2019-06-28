package com.app.lifegames.data.selectiondata


import com.google.gson.annotations.SerializedName


data class DataItem(

        @field:SerializedName("cat_name")
        val catName: String? = null,

        @field:SerializedName("icon")
        val icon: String? = null,

        @field:SerializedName("cat_value")
        val catValue: String? = null,

        @field:SerializedName("cat_color")
        val catColor: String? = null,

        var selected: Boolean = false
)