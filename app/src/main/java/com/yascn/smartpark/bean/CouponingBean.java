package com.yascn.smartpark.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by YASCN on 2018/5/7.
 */

public class CouponingBean implements Serializable{


    /**
     * msg : 正确返回数据
     * object : [{"END_TIME":"2018-06-05","NUM_VALUE":"2","COUPON_ID":"1","TYPE":"0","NAME":"2元优惠券"}]
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
         * END_TIME : 2018-06-05
         * NUM_VALUE : 2
         * COUPON_ID : 1
         * TYPE : 0
         * NAME : 2元优惠券
         */

        private String END_TIME;
        private String NUM_VALUE;
        private String COUPON_ID;
        private String TYPE;
        private String NAME;

        public String getEND_TIME() {
            return END_TIME;
        }

        public void setEND_TIME(String END_TIME) {
            this.END_TIME = END_TIME;
        }

        public String getNUM_VALUE() {
            return NUM_VALUE;
        }

        public void setNUM_VALUE(String NUM_VALUE) {
            this.NUM_VALUE = NUM_VALUE;
        }

        public String getCOUPON_ID() {
            return COUPON_ID;
        }

        public void setCOUPON_ID(String COUPON_ID) {
            this.COUPON_ID = COUPON_ID;
        }

        public String getTYPE() {
            return TYPE;
        }

        public void setTYPE(String TYPE) {
            this.TYPE = TYPE;
        }

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }
    }
}
