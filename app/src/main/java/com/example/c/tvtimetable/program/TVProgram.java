package com.example.c.tvtimetable.program;

/**
 * Created by C on 1/11/2014.
 */
public class TVProgram {

    private String playTime;
    private String tvProgram;

    public String getPlayTime() {
        return playTime;
    }

    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }

    public String getTvProgram() {
        return tvProgram;
    }

    public void setTvProgram(String tvProgram) {
        this.tvProgram = tvProgram;
    }

    @Override
    public String toString() {
        return playTime+" "+tvProgram;
    }
}
