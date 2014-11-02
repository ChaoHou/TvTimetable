package com.example.c.tvtimetable.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.c.tvtimetable.area.Area;
import com.example.c.tvtimetable.channel.TVChannel;
import com.example.c.tvtimetable.station.TVStation;

/**
 * Created by C on 1/11/2014.
 */
public class SQLHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tvtimetable.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_AREA =
            "create table " + Area.TABLE_AREA +
                    "(" +
                            Area.COLUMN_ID + " integer primary key autoincrement , " +
                            Area.COLUMN_AREA_ID + " text not null, " +
                            Area.COLUMN_AREA + " text not null" +
                    ");";
    private static final String CREATE_TABLE_STATION =
            "create table " + TVStation.TABLE_STATION +
                    "(" +
                    TVStation.COLUMN_ID + " integer primary key autoincrement , " +
                    TVStation.COLUMN_STATION_ID + " text not null, " +
                    TVStation.COLUMN_STATION_NAME + " text not null, " +
                    TVStation.COLUMN_AREA_ID + " text not null" +
                    ");";
    private static final String CREATE_TABLE_CHANNEL =
            "create table " + TVChannel.TABLE_CHANNEL +
                    "(" +
                    TVChannel.COLUMN_ID + " integer primary key autoincrement , " +
                    TVChannel.COLUMN_CHANNEL_ID + " text not null, " +
                    TVChannel.COLUMN_CHANNEL + " text not null, " +
                    TVChannel.COLUMN_STATION_ID + " text not null" +
                    ");";

    private static final String DELETE_TABLE_AREA = "drop table if exists " + Area.TABLE_AREA;
    private static final String DELETE_TABLE_STATION = "drop table if exists " + TVStation.TABLE_STATION;
    private static final String DELETE_TABLE_CHANNEL = "drop table if exists " + TVChannel.TABLE_CHANNEL;

    public SQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_AREA);
        sqLiteDatabase.execSQL(CREATE_TABLE_STATION);
        sqLiteDatabase.execSQL(CREATE_TABLE_CHANNEL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL(DELETE_TABLE_AREA);
        sqLiteDatabase.execSQL(DELETE_TABLE_STATION);
        sqLiteDatabase.execSQL(DELETE_TABLE_CHANNEL);
        onCreate(sqLiteDatabase);
    }
}
