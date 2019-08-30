package com.yascn.smartpark.bean;

import java.util.List;

/**
 * Created by YASCN on 2017/9/16.
 */

public class ParkDetailBean {

    /**
     * msg : 正确返回数据
     * object : {"imgs":[{"IMG":"http://192.168.1.25:8080/YYParking/uploadFiles/uploadImgs//20170310/f8e4ca25228043cb80f99cff1c71b8e7.jpg"},{"IMG":"http://192.168.1.25:8080/YYParking/uploadFiles/uploadImgs//20170310/20690224f9ed4625b6c8c4bf2ce93660.jpg"},{"IMG":"http://192.168.1.25:8080/YYParking/uploadFiles/uploadImgs//20170327/40e42ff1ea614840a9b18735a42140b3.jpg"}],"FIRST_HOUR":"4","LNG":"113.726237","FREE_TIME":"1","costDetail":["免费时长10分钟","第一小时收费4元","累计收费2元","封顶费用20元"],"PARKING_ID":"1","USER_ID":"3ca749174f5c45fe801c08c2c8c76826","NAME":"亚视停车场1","CATE":"0","DATE":"2017-01-03 14:27:52","CODE":"1","COMULATION":"2","TOP_MONEY":"20","ADDRESS":"商务外环12号","COST_DETAILS":"免费时长10分钟，第一小时收费4元，累计收费2元，封顶费用20元","FREE_NUM":"1000","LAT":"34.792489","IS_AUTH":"1"}
     * statusCode : 1
     */

    private String msg;
    /**
     * imgs : [{"IMG":"http://192.168.1.25:8080/YYParking/uploadFiles/uploadImgs//20170310/f8e4ca25228043cb80f99cff1c71b8e7.jpg"},{"IMG":"http://192.168.1.25:8080/YYParking/uploadFiles/uploadImgs//20170310/20690224f9ed4625b6c8c4bf2ce93660.jpg"},{"IMG":"http://192.168.1.25:8080/YYParking/uploadFiles/uploadImgs//20170327/40e42ff1ea614840a9b18735a42140b3.jpg"}]
     * FIRST_HOUR : 4
     * LNG : 113.726237
     * FREE_TIME : 1
     * costDetail : ["免费时长10分钟","第一小时收费4元","累计收费2元","封顶费用20元"]
     * PARKING_ID : 1
     * USER_ID : 3ca749174f5c45fe801c08c2c8c76826
     * NAME : 亚视停车场1
     * CATE : 0
     * DATE : 2017-01-03 14:27:52
     * CODE : 1
     * COMULATION : 2
     * TOP_MONEY : 20
     * ADDRESS : 商务外环12号
     * COST_DETAILS : 免费时长10分钟，第一小时收费4元，累计收费2元，封顶费用20元
     * FREE_NUM : 1000
     * LAT : 34.792489
     * IS_AUTH : 1
     */

    private ObjectBean object;
    private int statusCode;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ObjectBean getObject() {
        return object;
    }

    public void setObject(ObjectBean object) {
        this.object = object;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public static class ObjectBean {
        private String FIRST_HOUR;
        private String LNG;
        private String FREE_TIME;
        private String PARKING_ID;
        private String USER_ID;
        private String NAME;
        private String CATE;
        private String DATE;
        private String CODE;
        private String COMULATION;
        private String TOP_MONEY;
        private String ADDRESS;
        private String COST_DETAILS;
        private String FREE_NUM;
        private String LAT;
        private String IS_AUTH;
        /**
         * IMG : http://192.168.1.25:8080/YYParking/uploadFiles/uploadImgs//20170310/f8e4ca25228043cb80f99cff1c71b8e7.jpg
         */

        private List<ImgsBean> imgs;
        private List<String> costDetail;

        public String getFIRST_HOUR() {
            return FIRST_HOUR;
        }

        public void setFIRST_HOUR(String FIRST_HOUR) {
            this.FIRST_HOUR = FIRST_HOUR;
        }

        public String getLNG() {
            return LNG;
        }

        public void setLNG(String LNG) {
            this.LNG = LNG;
        }

        public String getFREE_TIME() {
            return FREE_TIME;
        }

        public void setFREE_TIME(String FREE_TIME) {
            this.FREE_TIME = FREE_TIME;
        }

        public String getPARKING_ID() {
            return PARKING_ID;
        }

        public void setPARKING_ID(String PARKING_ID) {
            this.PARKING_ID = PARKING_ID;
        }

        public String getUSER_ID() {
            return USER_ID;
        }

        public void setUSER_ID(String USER_ID) {
            this.USER_ID = USER_ID;
        }

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public String getCATE() {
            return CATE;
        }

        public void setCATE(String CATE) {
            this.CATE = CATE;
        }

        public String getDATE() {
            return DATE;
        }

        public void setDATE(String DATE) {
            this.DATE = DATE;
        }

        public String getCODE() {
            return CODE;
        }

        public void setCODE(String CODE) {
            this.CODE = CODE;
        }

        public String getCOMULATION() {
            return COMULATION;
        }

        public void setCOMULATION(String COMULATION) {
            this.COMULATION = COMULATION;
        }

        public String getTOP_MONEY() {
            return TOP_MONEY;
        }

        public void setTOP_MONEY(String TOP_MONEY) {
            this.TOP_MONEY = TOP_MONEY;
        }

        public String getADDRESS() {
            return ADDRESS;
        }

        public void setADDRESS(String ADDRESS) {
            this.ADDRESS = ADDRESS;
        }

        public String getCOST_DETAILS() {
            return COST_DETAILS;
        }

        public void setCOST_DETAILS(String COST_DETAILS) {
            this.COST_DETAILS = COST_DETAILS;
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

        public String getIS_AUTH() {
            return IS_AUTH;
        }

        public void setIS_AUTH(String IS_AUTH) {
            this.IS_AUTH = IS_AUTH;
        }

        public List<ImgsBean> getImgs() {
            return imgs;
        }

        public void setImgs(List<ImgsBean> imgs) {
            this.imgs = imgs;
        }

        public List<String> getCostDetail() {
            return costDetail;
        }

        public void setCostDetail(List<String> costDetail) {
            this.costDetail = costDetail;
        }

        public static class ImgsBean {
            private String IMG;

            public String getIMG() {
                return IMG;
            }

            public void setIMG(String IMG) {
                this.IMG = IMG;
            }
        }
    }
}
