package com.yascn.smartpark.bean;

import org.greenrobot.greendao.annotation.Entity;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by YASCN on 2017/9/15.
 */
@Entity
public class JPush implements Serializable {

    /**
     * NUMBER : 豫A00001
     * MONEY : 4
     * PARKING_ID : 4
     * PARK_TIME : 1小时0分钟
     * FLAG : 3
     * NAME : 亚视99号停车场
     */

    @Id(autoincrement = true)
    private long id;
    private long time;
    private String NUMBER;
    private String MONEY;
    private String PARKING_ID;
    private String PARK_TIME;
    private String FLAG;
    private String NAME;

    @Generated(hash = 255004341)
    public JPush(long id, long time, String NUMBER, String MONEY,
            String PARKING_ID, String PARK_TIME, String FLAG, String NAME) {
        this.id = id;
        this.time = time;
        this.NUMBER = NUMBER;
        this.MONEY = MONEY;
        this.PARKING_ID = PARKING_ID;
        this.PARK_TIME = PARK_TIME;
        this.FLAG = FLAG;
        this.NAME = NAME;
    }

    @Generated(hash = 1617458914)
    public JPush() {
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getNUMBER() {
        return NUMBER;
    }

    public void setNUMBER(String NUMBER) {
        this.NUMBER = NUMBER;
    }

    public String getMONEY() {
        return MONEY;
    }

    public void setMONEY(String MONEY) {
        this.MONEY = MONEY;
    }

    public String getPARKING_ID() {
        return PARKING_ID;
    }

    public void setPARKING_ID(String PARKING_ID) {
        this.PARKING_ID = PARKING_ID;
    }

    public String getPARK_TIME() {
        return PARK_TIME;
    }

    public void setPARK_TIME(String PARK_TIME) {
        this.PARK_TIME = PARK_TIME;
    }

    public String getFLAG() {
        return FLAG;
    }

    public void setFLAG(String FLAG) {
        this.FLAG = FLAG;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    @Override
    public String toString() {
        return "JPush{" +
                "time=" + time +
                ", NUMBER='" + NUMBER + '\'' +
                ", MONEY='" + MONEY + '\'' +
                ", PARKING_ID='" + PARKING_ID + '\'' +
                ", PARK_TIME='" + PARK_TIME + '\'' +
                ", FLAG='" + FLAG + '\'' +
                ", NAME='" + NAME + '\'' +
                '}';
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
