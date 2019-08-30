package com.yascn.smartpark.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by YASCN on 2017/9/7.
 */

public class Ordering implements Serializable{

    /**
     * msg : 正确返回数据
     * object : [{"ORDER_FORM_ID":"09d6a8fe718c4626b347915b721ec6b9","NUMBER":"豫SDRTY5","STATUS":"0","LNG":"113.726237","SERVER_TIME":"2017-09-12 17:42:17","PARKING_ID":"1","ADDRESS":"商务外环12号","YX_TIME":"30","ORDER_TIME":"2017-09-12 17:12:25","LAT":"34.782489","NAME":"亚视停车场1"}]
     * statusCode : 1
     */

    private String msg;
    private int statusCode;
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

    public static class ObjectBean implements Serializable{
        /**
         * ORDER_FORM_ID : 09d6a8fe718c4626b347915b721ec6b9
         * NUMBER : 豫SDRTY5
         * STATUS : 0
         * LNG : 113.726237
         * SERVER_TIME : 2017-09-12 17:42:17
         * PARKING_ID : 1
         * ADDRESS : 商务外环12号
         * YX_TIME : 30
         * ORDER_TIME : 2017-09-12 17:12:25
         * LAT : 34.782489
         * NAME : 亚视停车场1
         */

        private String ORDER_FORM_ID;
        private String PARKING_ID;
        private String NAME;
        private String NUMBER;
        private String ADDRESS;
        private String LAT;
        private String LNG;
        private String YX_TIME;
        private String SERVER_TIME;
        private String ORDER_TIME;
        private String START_TIME;
        private String PARK_TIME;
        private String MONEY;
        private String LAST_TIME;
        private String TIMEOUT;
        private String YJ_MONEY;
        private String STATUS;

        public String getORDER_FORM_ID() {
            return ORDER_FORM_ID;
        }

        public void setORDER_FORM_ID(String ORDER_FORM_ID) {
            this.ORDER_FORM_ID = ORDER_FORM_ID;
        }

        public String getPARKING_ID() {
            return PARKING_ID;
        }

        public void setPARKING_ID(String PARKING_ID) {
            this.PARKING_ID = PARKING_ID;
        }

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public String getNUMBER() {
            return NUMBER;
        }

        public void setNUMBER(String NUMBER) {
            this.NUMBER = NUMBER;
        }

        public String getADDRESS() {
            return ADDRESS;
        }

        public void setADDRESS(String ADDRESS) {
            this.ADDRESS = ADDRESS;
        }

        public String getLAT() {
            return LAT;
        }

        public void setLAT(String LAT) {
            this.LAT = LAT;
        }

        public String getLNG() {
            return LNG;
        }

        public void setLNG(String LNG) {
            this.LNG = LNG;
        }

        public String getYX_TIME() {
            return YX_TIME;
        }

        public void setYX_TIME(String YX_TIME) {
            this.YX_TIME = YX_TIME;
        }

        public String getSERVER_TIME() {
            return SERVER_TIME;
        }

        public void setSERVER_TIME(String SERVER_TIME) {
            this.SERVER_TIME = SERVER_TIME;
        }

        public String getORDER_TIME() {
            return ORDER_TIME;
        }

        public void setORDER_TIME(String ORDER_TIME) {
            this.ORDER_TIME = ORDER_TIME;
        }

        public String getSTART_TIME() {
            return START_TIME;
        }

        public void setSTART_TIME(String START_TIME) {
            this.START_TIME = START_TIME;
        }

        public String getPARK_TIME() {
            return PARK_TIME;
        }

        public void setPARK_TIME(String PARK_TIME) {
            this.PARK_TIME = PARK_TIME;
        }

        public String getMONEY() {
            return MONEY;
        }

        public void setMONEY(String MONEY) {
            this.MONEY = MONEY;
        }

        public String getLAST_TIME() {
            return LAST_TIME;
        }

        public void setLAST_TIME(String LAST_TIME) {
            this.LAST_TIME = LAST_TIME;
        }

        public String getTIMEOUT() {
            return TIMEOUT;
        }

        public void setTIMEOUT(String TIMEOUT) {
            this.TIMEOUT = TIMEOUT;
        }

        public String getYJ_MONEY() {
            return YJ_MONEY;
        }

        public void setYJ_MONEY(String YJ_MONEY) {
            this.YJ_MONEY = YJ_MONEY;
        }

        public String getSTATUS() {
            return STATUS;
        }

        public void setSTATUS(String STATUS) {
            this.STATUS = STATUS;
        }

        @Override
        public String toString() {
            return "ObjectBean{" +
                    "ORDER_FORM_ID='" + ORDER_FORM_ID + '\'' +
                    ", PARKING_ID='" + PARKING_ID + '\'' +
                    ", NAME='" + NAME + '\'' +
                    ", NUMBER='" + NUMBER + '\'' +
                    ", ADDRESS='" + ADDRESS + '\'' +
                    ", LAT='" + LAT + '\'' +
                    ", LNG='" + LNG + '\'' +
                    ", YX_TIME='" + YX_TIME + '\'' +
                    ", SERVER_TIME='" + SERVER_TIME + '\'' +
                    ", ORDER_TIME='" + ORDER_TIME + '\'' +
                    ", START_TIME='" + START_TIME + '\'' +
                    ", PARK_TIME='" + PARK_TIME + '\'' +
                    ", MONEY='" + MONEY + '\'' +
                    ", LAST_TIME='" + LAST_TIME + '\'' +
                    ", TIMEOUT='" + TIMEOUT + '\'' +
                    ", YJ_MONEY='" + YJ_MONEY + '\'' +
                    ", STATUS='" + STATUS + '\'' +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "Ordering{" +
                "msg='" + msg + '\'' +
                ", statusCode=" + statusCode +
                ", object=" + object +
                '}';
    }
}
