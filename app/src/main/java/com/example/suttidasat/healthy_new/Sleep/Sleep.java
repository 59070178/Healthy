package com.example.suttidasat.healthy_new.Sleep;

import android.content.ContentValues;
import android.provider.BaseColumns;

public class Sleep {


    String date_sleep;
    String time_sleep;
    String time_wake;
    String diff;
    private int id;

    ContentValues row = new ContentValues();

    public Sleep(){

    }

    public Sleep(int id,String date_sleep , String time_sleep , String time_wake,String diff){
            this.date_sleep = date_sleep;
            this.time_sleep = time_sleep;
            this.time_wake = time_wake;
            this.diff = diff;
            this.id = id;
    }

    public ContentValues getContent() {
        return row;
    }

    public void setContent(String date_sleep , String time_sleep , String time_wake,String diff) {
        this.row.put("date", date_sleep);
        this.row.put("sleep_time", time_sleep);
        this.row.put("wake_time", time_wake);
        this.row.put("diff", diff);

    }

    public String getDate_sleep() {
        return date_sleep;
    }

    public void setDate_sleep(String date_sleep) {
        this.date_sleep = date_sleep;
    }

    public String getTime_sleep() {
        return time_sleep;
    }

    public void setTime_sleep(String time_sleep) {
        this.time_sleep = time_sleep;
    }

    public String getTime_wake() {
        return time_wake;
    }

    public void setTime_wake(String time_wake) {
        this.time_wake = time_wake;
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
