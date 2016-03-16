package com.mapfinger.joepher.oldercare.entity.msg;

/**
 * Created by Administrator on 2016/3/10.
 */
public class AlarmMsg {
    private String time;
    private String latitude;
    private String longitude;

    public AlarmMsg() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

}
