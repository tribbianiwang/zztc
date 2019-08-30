package com.yascn.smartpark.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by YASCN on 2017/9/13.
 */
@Entity
public class SearchHistoryDaoBean {
    @Id
    private Long id;
    private String LNG;
    private String PARKING_ID;
    private String ADDRESS;
    private String COLLECTION_ID;
    private String FREE_NUM;
    private String LAT;
    private String NAME;


    @Generated(hash = 34002055)
    public SearchHistoryDaoBean(Long id, String LNG, String PARKING_ID,
            String ADDRESS, String COLLECTION_ID, String FREE_NUM, String LAT,
            String NAME) {
        this.id = id;
        this.LNG = LNG;
        this.PARKING_ID = PARKING_ID;
        this.ADDRESS = ADDRESS;
        this.COLLECTION_ID = COLLECTION_ID;
        this.FREE_NUM = FREE_NUM;
        this.LAT = LAT;
        this.NAME = NAME;
    }

    @Generated(hash = 1722364744)
    public SearchHistoryDaoBean() {
    }


    public String getLNG() {
        return LNG;
    }

    public void setLNG(String LNG) {
        this.LNG = LNG;
    }

    public String getPARKING_ID() {
        return PARKING_ID;
    }

    public void setPARKING_ID(String PARKING_ID) {
        this.PARKING_ID = PARKING_ID;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getCOLLECTION_ID() {
        return COLLECTION_ID;
    }

    public void setCOLLECTION_ID(String COLLECTION_ID) {
        this.COLLECTION_ID = COLLECTION_ID;
    }

    public String getFREE_NUM() {
        return FREE_NUM;
    }

    public void setFREE_NUM(String FREE_NUM) {
        this.FREE_NUM = FREE_NUM;
    }

    public String getLAT() {
        return LAT;
    }

    public void setLAT(String LAT) {
        this.LAT = LAT;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}