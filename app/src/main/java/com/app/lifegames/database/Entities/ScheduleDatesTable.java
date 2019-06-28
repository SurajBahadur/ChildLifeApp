package com.app.lifegames.database.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class ScheduleDatesTable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    public ScheduleDatesTable(String date,String isSelected,String date_new) {
        this.date = date;
        this.isSelected=isSelected;
        this.date_new=date_new;
    }

    @ColumnInfo(name = "date") // column name will be "note_content" instead of "content" in table
    private String date;
    @ColumnInfo(name = "date_new") // column name will be "note_content" instead of "content" in table

    private String date_new;

    @ColumnInfo(name = "isSelected") // column name will be "note_content" instead of "content" in table
    private String isSelected;

    public String getDate_new() {
        return date_new;
    }

    public void setDate_new(String date_new) {
        this.date_new = date_new;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }
}
