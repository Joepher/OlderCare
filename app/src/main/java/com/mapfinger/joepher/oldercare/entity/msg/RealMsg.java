package com.mapfinger.joepher.oldercare.entity.msg;

import com.mapfinger.joepher.oldercare.entity.Coord;

import java.util.List;

/**
 * Created by Administrator on 2016/3/10.
 */
public class RealMsg {
    private String time;
    private String ratio;
    private String stime;
    private String etime;
    private Coord ccoord;
    private Coord scoord;
    private Coord ecoord;
    private List<Coord> path;

    public RealMsg() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public Coord getCcoord() {
        return ccoord;
    }

    public void setCcoord(Coord ccoord) {
        this.ccoord = ccoord;
    }

    public Coord getScoord() {
        return scoord;
    }

    public void setScoord(Coord scoord) {
        this.scoord = scoord;
    }

    public Coord getEcoord() {
        return ecoord;
    }

    public void setEcoord(Coord ecoord) {
        this.ecoord = ecoord;
    }

    public List<Coord> getPath() {
        return path;
    }

    public void setPath(List<Coord> path) {
        this.path = path;
    }

}
