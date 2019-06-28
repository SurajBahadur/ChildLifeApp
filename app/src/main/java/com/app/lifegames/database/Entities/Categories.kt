package com.app.lifegames.database.Entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class Categories(id: Int , cat_name: String, cat_color: String, cat_value: String, icon: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Int?=id

    var cat_name: String?=cat_name
    var cat_color: String?=cat_color
    var cat_value: String?=cat_value
    var icon: String?=icon

    override fun toString(): String {
        return "Category{" +
                "id=" + id +
                ", cat_name='" + cat_name + '\''.toString() +
                ", cat_color='" + cat_color + '\''.toString() +
                ", cat_color='" + cat_value + '\''.toString() +
                ", cat_color='" + icon + '\''.toString() +
                '}'.toString()
    }
}
