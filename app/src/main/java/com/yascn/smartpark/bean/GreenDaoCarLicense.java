package com.yascn.smartpark.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by YASCN on 2018/5/7.
 */
@Entity
public class GreenDaoCarLicense {
    @Id
    private Long id;

    private String carLicense;

    public String getCarLicense() {
        return this.carLicense;
    }

    public void setCarLicense(String carLicense) {
        this.carLicense = carLicense;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Generated(hash = 1902851126)
    public GreenDaoCarLicense(Long id, String carLicense) {
        this.id = id;
        this.carLicense = carLicense;
    }

    @Generated(hash = 636685619)
    public GreenDaoCarLicense() {
    }
}
