package com.app.lifegames.database.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
@Entity
public class FavouriteActivitiesTable  {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "activity_id") // column name will be "note_content" instead of "content" in table
    private String actID;

    @ColumnInfo(name = "image") // column name will be "note_content" instead of "content" in table
    private String image;

    @ColumnInfo(name = "title") // column name will be "note_content" instead of "content" in table
    private String title;

    @ColumnInfo(name = "age_group") // column name will be "note_content" instead of "content" in table
    private String age_group;

    @ColumnInfo(name = "age_group_icon") // column name will be "note_content" instead of "content" in table
    private String age_group_icon;

    @ColumnInfo(name = "overview") // column name will be "note_content" instead of "content" in table
    private String overview;

    @ColumnInfo(name = "cat_color") // column name will be "note_content" instead of "content" in table
    private String cat_color;

    @ColumnInfo(name = "cat_name") // column name will be "note_content" instead of "content" in table
    private String cat_name;


    @ColumnInfo(name = "cat_value") // column name will be "note_content" instead of "content" in table
    private String cat_value;

    @ColumnInfo(name = "time") // column name will be "note_content" instead of "content" in table
    private String time;

    @ColumnInfo(name = "materials") // column name will be "note_content" instead of "content" in table
    private String materials;

    @ColumnInfo(name = "warnings") // column name will be "note_content" instead of "content" in table
    private String warnings;

    @ColumnInfo(name = "completed") // column name will be "note_content" instead of "content" in table
    private String completed;

    @ColumnInfo(name = "favourite") // column name will be "note_content" instead of "content" in table
    private String favourite;

    @ColumnInfo(name = "activity_details") // column name will be "note_content" instead of "content" in table
    private String activity_details;


    @ColumnInfo(name = "cat_icon") // column name will be "note_content" instead of "content" in table
    private String cat_icon;


    @ColumnInfo(name = "ratings") // column name will be "note_content" instead of "content" in table
    private String ratings;

    @ColumnInfo(name = "user_id") // column name will be "note_content" instead of "content" in table
    private String user_id;



    public FavouriteActivitiesTable( String actID, String image, String title, String age_group, String age_group_icon, String overview, String cat_color, String cat_name, String cat_value, String time, String materials, String warnings, String completed, String favourite, String activity_details, String cat_icon, String ratings, String user_id) {

        this.actID = actID;
        this.image = image;
        this.title = title;
        this.age_group = age_group;
        this.age_group_icon = age_group_icon;
        this.overview = overview;
        this.cat_color = cat_color;
        this.cat_name = cat_name;
        this.cat_value = cat_value;
        this.time = time;
        this.materials = materials;
        this.warnings = warnings;
        this.completed = completed;
        this.favourite = favourite;
        this.activity_details = activity_details;
        this.cat_icon = cat_icon;
        this.ratings = ratings;
        this.user_id = user_id;
    }
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public String getAge_group_icon() {
        return age_group_icon;
    }

    public void setAge_group_icon(String age_group_icon) {
        this.age_group_icon = age_group_icon;
    }

    public String getCat_color() {
        return cat_color;
    }

    public void setCat_color(String cat_color) {
        this.cat_color = cat_color;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCat_value() {
        return cat_value;
    }

    public void setCat_value(String cat_value) {
        this.cat_value = cat_value;
    }

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }

    public String getWarnings() {
        return warnings;
    }

    public void setWarnings(String warnings) {
        this.warnings = warnings;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public String getFavourite() {
        return favourite;
    }

    public void setFavourite(String favourite) {
        this.favourite = favourite;
    }

    public String getActID() {
        return actID;
    }

    public void setActID(String actID) {
        this.actID = actID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAge_group() {
        return age_group;
    }

    public void setAge_group(String age_group) {
        this.age_group = age_group;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getActivity_details() {
        return activity_details;
    }

    public void setActivity_details(String activity_details) {
        this.activity_details = activity_details;
    }

    public String getCat_icon() {
        return cat_icon;
    }

    public void setCat_icon(String cat_icon) {
        this.cat_icon = cat_icon;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }


}
