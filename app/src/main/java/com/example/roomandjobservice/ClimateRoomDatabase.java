package com.example.roomandjobservice;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = ClimateItem.class, version = 1, exportSchema = false)
public abstract class ClimateRoomDatabase extends RoomDatabase {
    private static final String DB_NAME = "climate_db";
    private static ClimateRoomDatabase sInstance;

    public static synchronized  ClimateRoomDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context.getApplicationContext(),
                    ClimateRoomDatabase.class, DB_NAME)
                    .allowMainThreadQueries()   // allow query from main thread
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return sInstance;
    }

    public abstract ClimateDAO climateDao();

}
