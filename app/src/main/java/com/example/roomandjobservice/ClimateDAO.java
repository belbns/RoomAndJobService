package com.example.roomandjobservice;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ClimateDAO {
    @Query("SELECT * FROM climate_table")
    List<ClimateItem> getAll();

    @Query("SELECT * FROM climate_table WHERE measure_time = :tm")
    ClimateItem getById(long tm);

    @Query("SELECT * FROM climate_table ORDER BY measure_time DESC LIMIT :num;")
    List<ClimateItem> getLastN(int num);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ClimateItem item);

    @Update
    void update(ClimateItem item);

    @Delete
    void delete(ClimateItem item);

}
