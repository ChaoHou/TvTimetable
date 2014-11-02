package com.example.c.tvtimetable.channel;

/**
 * Created by C on 1/11/2014.
 */
public class TVChannel {

    public static final String TABLE_CHANNEL = "channels";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CHANNEL_ID = "channel_id";
    public static final String COLUMN_CHANNEL = "channel";
    public static final String COLUMN_STATION_ID = "station_id";
    public static final String[] COLUMNS = {COLUMN_ID,COLUMN_CHANNEL_ID,COLUMN_CHANNEL,COLUMN_STATION_ID};

    private long id;
    private String tvChannelID;
    private String tvChannel;

    private String stationID;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTvChannel() {
        return tvChannel;
    }

    public void setTvChannel(String tvChannel) {
        this.tvChannel = tvChannel;
    }

    public String getTvChannelID() {
        return tvChannelID;
    }

    public void setTvChannelID(String tvChannelID) {
        this.tvChannelID = tvChannelID;
    }

    public String getStationID() {
        return stationID;
    }

    public void setStationID(String stationID) {
        this.stationID = stationID;
    }

    @Override
    public String toString() {
        return tvChannel;
    }
}
