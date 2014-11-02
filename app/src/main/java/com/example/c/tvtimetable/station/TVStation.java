package com.example.c.tvtimetable.station;

/**
 * Created by C on 1/11/2014.
 */
public class TVStation {

    public static final String TABLE_STATION = "stations";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_STATION_ID = "station_id";
    public static final String COLUMN_STATION_NAME = "station_name";
    public static final String COLUMN_AREA_ID = "area_id";
    public static final String[] COLUMNS = {COLUMN_ID,COLUMN_STATION_ID,COLUMN_STATION_NAME,COLUMN_AREA_ID};

    private long id;
    private String tvStationID;
    private String tvStationName;

    private String areaID;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTvStationID() {
        return tvStationID;
    }

    public void setTvStationID(String tvStationID) {
        this.tvStationID = tvStationID;
    }

    public String getTvStationName() {
        return tvStationName;
    }

    public void setTvStationName(String tvStationName) {
        this.tvStationName = tvStationName;
    }

    public String getAreaID() {
        return areaID;
    }

    public void setAreaID(String areaID) {
        this.areaID = areaID;
    }

    @Override
    public String toString() {
        return tvStationName;
    }
}
