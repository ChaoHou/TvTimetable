package com.example.c.tvtimetable.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.c.tvtimetable.area.Area;
import com.example.c.tvtimetable.channel.TVChannel;
import com.example.c.tvtimetable.station.TVStation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by C on 1/11/2014.
 */
public class DataSet {

    private SQLiteDatabase database;
    private SQLHelper dbHelper;


    public DataSet(Context context){
        dbHelper = new SQLHelper(context);
    }

    public void open(){
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public void insertArea(Area area){
        insertArea(area.getAreaID(),area.getArea());
    }

    public void insertArea(String areaID,String area){
        ContentValues values = new ContentValues();
        values.put(Area.COLUMN_AREA_ID,areaID);
        values.put(Area.COLUMN_AREA,area);
        long id = database.insert(Area.TABLE_AREA,null,values);
    }

    public List<Area> getAllAreas(){
        List<Area> list = new ArrayList<Area>();
        Cursor cursor = database.query(Area.TABLE_AREA,Area.COLUMNS,null,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Area area = cursorToArea(cursor);
            list.add(area);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public void deleteAllAreas(){
        List<Area> list = getAllAreas();
        for(Area area:list){
            database.delete(Area.TABLE_AREA,Area.COLUMN_ID + " = "+area.getId(),null);
        }
    }

    public void insertStation(String stationID,String stationName,String areaID){
        ContentValues values = new ContentValues();
        values.put(TVStation.COLUMN_STATION_ID,stationID);
        values.put(TVStation.COLUMN_STATION_NAME,stationName);
        values.put(TVStation.COLUMN_AREA_ID,areaID);
        long id = database.insert(TVStation.TABLE_STATION,null,values);
    }

    public List<TVStation> getAllStationsByAreaID(String areaID){
        List<TVStation> list = new ArrayList<TVStation>();
        String selection = TVStation.COLUMN_AREA_ID + " = " + areaID;
        Cursor cursor = database.query(TVStation.TABLE_STATION,TVStation.COLUMNS,selection,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            TVStation station = cursorToStation(cursor);
            list.add(station);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public void deleteAllStationsByAreaID(String areaID){
        List<TVStation> list = getAllStationsByAreaID(areaID);
        for(TVStation station:list){
            database.delete(TVStation.TABLE_STATION,TVStation.COLUMN_ID + " = " + station.getId(),null);
        }
    }

    public void insertChannel(String channelID,String channel,String stationID){
        ContentValues values = new ContentValues();
        values.put(TVChannel.COLUMN_CHANNEL_ID,channelID);
        values.put(TVChannel.COLUMN_CHANNEL,channel);
        values.put(TVChannel.COLUMN_STATION_ID,stationID);
        long id = database.insert(TVChannel.TABLE_CHANNEL,null,values);
    }

    public List<TVChannel> getAllChannelsByStationID(String stationID){
        List<TVChannel> list = new ArrayList<TVChannel>();
        String selection = TVChannel.COLUMN_STATION_ID + " = " + stationID;
        Cursor cursor = database.query(TVChannel.TABLE_CHANNEL,TVChannel.COLUMNS,selection,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            TVChannel channel = cursorToChannel(cursor);
            list.add(channel);
            cursor.moveToNext();
        }
        cursor.close();
        return  list;
    }

    public void deleteAllChannelsByStationID(String stationID){
        List<TVChannel> list = getAllChannelsByStationID(stationID);
        for(TVChannel channel:list){
            database.delete(TVChannel.TABLE_CHANNEL,TVChannel.COLUMN_ID + " = " + channel.getId(),null);
        }
    }

    private Area cursorToArea(Cursor cursor){
        Area area = new Area();
        area.setId(cursor.getLong(0));
        area.setAreaID(cursor.getString(1));
        area.setArea(cursor.getString(2));
        return area;
    }

    private TVStation cursorToStation(Cursor cursor){
        TVStation station = new TVStation();
        station.setId(cursor.getLong(0));
        station.setTvStationID(cursor.getString(1));
        station.setTvStationName(cursor.getString(2));
        station.setAreaID(cursor.getString(3));
        return station;
    }

    private TVChannel cursorToChannel(Cursor cursor) {
        TVChannel channel = new TVChannel();
        channel.setId(cursor.getLong(0));
        channel.setTvChannelID(cursor.getString(1));
        channel.setTvChannel(cursor.getString(2));
        channel.setStationID(cursor.getString(3));
        return channel;
    }
}
