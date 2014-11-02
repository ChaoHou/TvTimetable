package com.example.c.tvtimetable.area;

/**
 * Created by C on 1/11/2014.
 */
public class Area {

    public static final String TABLE_AREA = "areas";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_AREA_ID = "area_id";
    public static final String COLUMN_AREA = "area";
    public static final String[] COLUMNS = {COLUMN_ID,COLUMN_AREA_ID,COLUMN_AREA};

    private long id;
    private String areaID;
    private String area;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAreaID() {
        return areaID;
    }

    public void setAreaID(String areaID) {
        this.areaID = areaID;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return area;
    }
}
