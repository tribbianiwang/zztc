package com.yascn.smartpark.bean;

import java.util.List;

/**
 * Created by YASCN on 2017/9/14.
 */

public class RecentParkBean {


    /**
     * msg : 正确返回数据
     * object : [{"LNG":"113.72618","PARKING_ID":"4","ADDRESS":"商务外环1号","FREE_NUM":"100","LAT":"34.782393","NAME":"亚视停车场99"},{"LNG":"113.725042","PARKING_ID":"3","ADDRESS":"中油花园酒店","FREE_NUM":"10","LAT":"34.780784","NAME":"亚视停车场6"},{"LNG":"113.726237","PARKING_ID":"1","ADDRESS":"商务外环12号","FREE_NUM":"1000","LAT":"34.782489","NAME":"亚视停车场1"},{"LNG":"113.7369","PARKING_ID":"2335e1f5d2384e889bc857582309a186","ADDRESS":"会展中心1","FREE_NUM":"40","LAT":"34.778575","NAME":"郑东新区CBD10号停车场"},{"LNG":"113.7247","PARKING_ID":"2","ADDRESS":"商务外环8号","FREE_NUM":"800","LAT":"34.779027","NAME":"亚视停车场2"}]
     * statusCode : 1
     */

    private String msg;
    private int statusCode;
    /**
     * LNG : 113.72618
     * PARKING_ID : 4
     * ADDRESS : 商务外环1号
     * FREE_NUM : 100
     * LAT : 34.782393
     * NAME : 亚视停车场99
     */

    private List<ObjectBean> object;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<ObjectBean> getObject() {
        return object;
    }

    public void setObject(List<ObjectBean> object) {
        this.object = object;
    }

    public static class ObjectBean {
        private String LNG;
        private String PARKING_ID;
        private String ADDRESS;
        private String FREE_NUM;
        private String LAT;
        private String NAME;

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
    }
}
