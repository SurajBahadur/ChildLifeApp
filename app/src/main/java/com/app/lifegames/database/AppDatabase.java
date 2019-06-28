package com.app.lifegames.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.app.lifegames.TaskTable;
import com.app.lifegames.database.Entities.ActivityDetailTable;
import com.app.lifegames.database.Entities.Categories;
import com.app.lifegames.database.Entities.CategoriesTable;
import com.app.lifegames.database.Entities.DoneActivitiesTable;
import com.app.lifegames.database.Entities.FavouriteActivitiesTable;
import com.app.lifegames.database.Entities.PlannerActivitiesTable;
import com.app.lifegames.database.Entities.ScheduleDatesTable;
import com.app.lifegames.database.Entities.SearchActivtiyTable;
import com.app.lifegames.utils.Constants;

@Database(entities = {CategoriesTable.class, SearchActivtiyTable.class, DoneActivitiesTable.class,
        FavouriteActivitiesTable.class,
        PlannerActivitiesTable.class, ActivityDetailTable.class,TaskTable.class, ScheduleDatesTable.class}, version =1)
public abstract class AppDatabase extends RoomDatabase {

   public abstract TaskDao getTaskDao();
   // public abstract TaskDao taskDao();

}