package com.yascn.smartpark.bean;

import com.amap.api.services.core.LatLonPoint;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by YASCN on 2018/3/26.
 */
@Entity
public class SearchGaodeHistoryBean {

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    private double lat;
    private double lon;
    private String title;
    private String snaippt;
    @Id
    private String id;


 

    public SearchGaodeHistoryBean() {
    }

    @Generated(hash = 84192285)
    public SearchGaodeHistoryBean(double lat, double lon, String title,
            String snaippt, String id) {
        this.lat = lat;
        this.lon = lon;
        this.title = title;
        this.snaippt = snaippt;
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSnaippt() {
        return snaippt;
    }

    public void setSnaippt(String snaippt) {
        this.snaippt = snaippt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
