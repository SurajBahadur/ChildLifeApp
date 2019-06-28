package com.app.lifegames.database.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class CategoriesTable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "cat_name") // column name will be "note_content" instead of "content" in table
    private String catName;

    @ColumnInfo(name = "cat_color") // column name will be "note_content" instead of "content" in table
    private String catColor;

    @ColumnInfo(name = "cat_value") // column name will be "note_content" instead of "content" in table
    private String catValue;

    @ColumnInfo(name = "icon") // column name will be "note_content" instead of "content" in table
    private String icon;


    @ColumnInfo(name = "selectedCat") // column name will be "note_content" instead of "content" in table
    private int selectedCat;


    public CategoriesTable(String catName, String catColor,String catValue, String icon,int selectedCat) {
        this.catName = catName;
        this.catColor = catColor;
        this.catValue = catValue;
        this.icon = icon;
        this.selectedCat=selectedCat;
    }

    public int getSelectedCat() {
        return selectedCat;
    }

    public void setSelectedCat(int selectedCat) {
        this.selectedCat = selectedCat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatColor() {
        return catColor;
    }

    public void setCatColor(String catColor) {
        this.catColor = catColor;
    }

    public String getCatValue() {
        return catValue;
    }

    public void setCatValue(String catValue) {
        this.catValue = catValue;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoriesTable)) return false;

        CategoriesTable cat = (CategoriesTable) o;

        if (id != cat.id) return false;
        return catValue != null ? catValue.equals(cat.catValue) : cat.catValue == null;
    }



  /*  @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }*/

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", catName='" + catName + '\'' +
                ", catColor='" + catColor + '\'' +
                ", catValue='" + catValue + '\'' +
                ", icon='" + icon + '\'' +
                ", sel='" + selectedCat + '\'' +
                '}';
    }
}