package com.example.roomandjobservice;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "climate_table")
public class ClimateItem {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "measure_time")
    public long measureTime;

    @ColumnInfo(name = "pressure")
    public int Pressure;

    @ColumnInfo(name = "temp_battery")
    public int tempBattery;

    @ColumnInfo(name = "temp_air")
    public int tempAir;
}
