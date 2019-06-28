package com.app.lifegames.database

import android.arch.persistence.room.*
import android.arch.persistence.room.Delete
import com.app.lifegames.TaskTable
import com.app.lifegames.database.Entities.*


@Dao
interface TaskDao {

    @Query("SELECT * FROM CategoriesTable")
    fun getAll(): List<CategoriesTable>


    @Query("SELECT * FROM CategoriesTable WHERE cat_value=:id")
    fun getParticulorAll(id:String): List<CategoriesTable>




    @Query("SELECT * FROM SearchActivtiyTable WHERE user_id=:id")
    fun getAllActvities(id:String): List<SearchActivtiyTable>

    @Query("SELECT * FROM SearchActivtiyTable WHERE age_group=:age")
    fun getActvities(age:String): List<SearchActivtiyTable>
    @Query("SELECT * FROM SearchActivtiyTable WHERE cat_value=:cat AND user_id=:userID")
    fun getActvitiesAccToCat(cat:String,userID: String): List<SearchActivtiyTable>

    @Query("SELECT * FROM SearchActivtiyTable WHERE activity_id=:id")
    fun getPartActvities(id:String): List<SearchActivtiyTable>

    @Query("SELECT * FROM SearchActivtiyTable WHERE cat_value=:cat AND age_group=:age AND user_id=:id")
    fun getActvities(cat:String,age:String,id:String): List<SearchActivtiyTable>

    @Query("SELECT * FROM SearchActivtiyTable WHERE age_group=:age AND user_id=:id")
    fun getActvities2(age:String,id:String): List<SearchActivtiyTable>

    @Query("SELECT * FROM DoneActivitiesTable WHERE user_id=:user")
    fun getDoneActvities(user:String): List<DoneActivitiesTable>

    @Query("SELECT * FROM DoneActivitiesTable WHERE activity_id=:id")
    fun getPartDoneActvities(id:String): List<DoneActivitiesTable>


    @Query("SELECT * FROM FavouriteActivitiesTable WHERE user_id=:id")
    fun getFavActvities(id:String): List<FavouriteActivitiesTable>


    @Query("SELECT * FROM FavouriteActivitiesTable WHERE activity_id=:id")
    fun getPartFavActvities(id:String): List<FavouriteActivitiesTable>

    @Query("DELETE  FROM FavouriteActivitiesTable WHERE user_id=:id  AND activity_id=:actID" )
    fun deleteFavActvities(id:String,actID: String)


    @Query("SELECT * FROM PlannerActivitiesTable WHERE user_id=:id")
    fun getPlannerActvities(id:String): List<PlannerActivitiesTable>

    @Query("DELETE  FROM PlannerActivitiesTable WHERE user_id=:id")
    fun deletePlannerActvities(id:String)

    @Query("SELECT * FROM PlannerActivitiesTable WHERE activity_id=:id")
    fun getPartPlannerActvities(id:String): List<PlannerActivitiesTable>


    @Query("SELECT * FROM ActivityDetailTable")
    fun getActivityDetail():ActivityDetailTable
    @Query("SELECT * FROM ActivityDetailTable WHERE activity_id=:id")
    fun getActivityDetail(id:String): List<ActivityDetailTable>

    @Query("SELECT * FROM ActivityDetailTable WHERE activity_id=:id")
    fun getSelectedActivityDetail(id:String):ActivityDetailTable

    @Query("SELECT * FROM "+ "TaskTable")
    fun getTaskT(): List<TaskTable>


    @Insert
    fun insert(categories: CategoriesTable)
    @Query("UPDATE CategoriesTable SET selectedCat=:selct WHERE cat_value = :cat")
    fun update(selct:Int,cat:String)

    @Query("UPDATE CategoriesTable SET selectedCat=:selct,cat_value=:catValue,cat_color=:catColor,cat_name=:catName, icon=:cat_icon WHERE cat_value = :catValue")
    fun updateCatTable(selct:Int,catValue:String,catName:String,catColor:String,cat_icon: String)

    @Query("UPDATE CategoriesTable SET selectedCat=:selct")
    fun updateAll(selct:Int)



    @Insert
    fun insertDetail(detail: ActivityDetailTable)


    @Query("UPDATE ActivityDetailTable SET activity_id=:actID,image=:image,title=:title,age_group=:age_group,overview=:overview,cat_value=:cat_value,cat_color=:cat_color,cat_name=:cat_name,materials=:materials, warnings=:warnings ,time=:time,activity_details=:activity_details, cat_icon=:cat_icon,ratings=:ratings   WHERE activity_id = :actID")
    fun updateDetail(actID:String, image:String, title:String, age_group:String, overview:String,  cat_color:String,  cat_name:String,  cat_value:String,  materials:String,  warnings:String,  time:String,  activity_details:String,  cat_icon:String,  ratings:String)




    @Insert
    fun insertActivities(activites: SearchActivtiyTable)
    @Query("UPDATE SearchActivtiyTable SET activity_id=:actID,image=:image,title=:title,age_group=:age_group,overview=:overview," +
            "time=:time,activity_details=:activity_details, cat_icon=:cat_icon,ratings=:ratings, cat_color=:catColor," +
            "cat_color=:catColor,cat_name=:catName,cat_value=:catValue,materials=:materials," +
            "warnings=:warnings," +
            " completed=:completed," +
            "favourite=:favourite,age_group_icon=:ageGroupIcon, user_id=:usreID   WHERE activity_id = :actID")
    fun updateSearchActivtiyTable(actID: String, image: String, title: String, age_group: String,
                                  overview: String, time: String, activity_details: String, cat_icon: String, ratings: String,
                                  catColor: String, catName: String?, catValue: String?, materials: String, warnings: String, completed: String,
                                  favourite: String, ageGroupIcon: String,usreID:String)




    @Insert
    fun insertDoneActivities(activites: DoneActivitiesTable)
    @Query("UPDATE DoneActivitiesTable SET activity_id=:actID,image=:image,title=:title,age_group=:age_group," +
            "overview=:overview,time=:time,activity_details=:activitydetail, cat_icon=:catIcon,ratings=:ratings," +
            "age_group_icon=:age_group_icon,cat_color=:cat_color,cat_name=:cat_name,cat_value=:cat_value,materials=:materials,warnings=:warnings," +
            "completed=:completed,favourite=:favourite, user_id=:userID   WHERE activity_id = :actID")
    fun updateDoneActivities(actID: String, image: String, title: String, age_group: String,
                             age_group_icon: String, overview: String, cat_color: String,
                             cat_name: String, cat_value: String, time: String, materials: String, warnings: String,
                             completed: String, favourite: String, activitydetail: String, catIcon: String, ratings: String,userID:String)



    @Insert
    fun insertFavActivities(activites: FavouriteActivitiesTable)
    @Query("UPDATE FavouriteActivitiesTable SET activity_id=:actID,image=:image,title=:title,age_group=:age_group," +
            "overview=:overview,time=:time,activity_details=:activitydetail, cat_icon=:catIcon,ratings=:ratings," +
            "age_group_icon=:age_group_icon,cat_color=:cat_color,cat_name=:cat_name,cat_value=:cat_value,materials=:materials,warnings=:warnings," +
            "completed=:completed,favourite=:favourite, user_id=:userID   WHERE activity_id = :actID")
    fun updateFavActivities(actID: String, image: String, title: String, age_group: String,
                            age_group_icon: String, overview: String, cat_color: String,
                            cat_name: String, cat_value: String, time: String, materials: String, warnings: String,
                            completed: String, favourite: String, activitydetail: String, catIcon: String, ratings: String,userID:String)




    @Insert
    fun insertPlannerActivities(activites: PlannerActivitiesTable)
    @Query("UPDATE PlannerActivitiesTable SET activity_id=:actID,image=:image,title=:title,age_group=:age_group," +
            "overview=:overview,time=:time,activity_details=:activitydetail, cat_icon=:catIcon,ratings=:ratings," +
            "age_group_icon=:age_group_icon,cat_color=:cat_color,cat_name=:cat_name,cat_value=:cat_value,materials=:materials,warnings=:warnings," +
            "completed=:completed,favourite=:favourite, user_id=:userID, schedule=:schedule,reminder=:reminder   WHERE activity_id = :actID")
    fun updatePlannerActivities(actID: String, image: String, title: String, age_group: String,
                                age_group_icon: String, overview: String, cat_color: String,
                                cat_name: String, cat_value: String, time: String, materials: String, warnings: String,
                                completed: String, favourite: String, activitydetail: String, catIcon: String, ratings: String,userID:String
                                ,schedule:String,reminder:String)
    @Query("DELETE FROM PlannerActivitiesTable WHERE activity_id=:actID AND user_id=:id")
    fun deleteAct(actID:String,id:String)


    @Insert
    fun insertT(task: TaskTable)


    @Insert
    fun insertDates(dates: ScheduleDatesTable)

    @Query("SELECT * FROM ScheduleDatesTable")
    fun getAllDates(): List<ScheduleDatesTable>



    @Query("SELECT id FROM ScheduleDatesTable WHERE date=:value")
    fun getDate(value:String): ScheduleDatesTable

    @Query("SELECT * FROM ScheduleDatesTable WHERE id=:id")
    fun getPartDate(id:String): List<ScheduleDatesTable>

    @Query("UPDATE ScheduleDatesTable SET isSelected=:selected WHERE date=:date")
    fun getPartDate(date:String,selected:String)

    @Query("DELETE FROM ScheduleDatesTable")
    fun deleteDates()
    /*
  * update the object in database
  * @param note, object to be updated
  */

    /*
  * delete the object from database
  * @param note, object to be deleted
  */
    @Delete
    fun delete(categories: CategoriesTable)

}