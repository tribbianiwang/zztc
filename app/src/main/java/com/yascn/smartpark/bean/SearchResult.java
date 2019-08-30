package com.yascn.smartpark.bean;

import java.util.List;

/**
 * Created by YASCN on 2017/9/12.
 */

public class SearchResult {


    /**
     * msg : 正确返回数据
     * object : [{"LNG":"113.726237","costDetail":["免费时长10分钟","第一小时收费4元","累计收费2元","封顶费用20元"],"PARKING_ID":"1","ADDRESS":"商务外环12号","COLLECTION_ID":"0c41e8f4f69b42d2b5ca1d3dd4ce2e91","FREE_NUM":"1000","LAT":"34.782489","NAME":"亚视停车场1"},{"LNG":"113.725042","costDetail":["免费时长1分钟","第一小时收费5元","累计收费2元","封顶费用25元"],"PARKING_ID":"3","ADDRESS":"中油花园酒店","COLLECTION_ID":"663b5bcf5790431da007406ac2952d3d","FREE_NUM":"10","LAT":"34.780784","NAME":"亚视停车场6"}]
     * statusCode : 1
     */

    private String msg;
    private int statusCode;
    /**
     * LNG : 113.726237
     * costDetail : ["免费时长10分钟","第一小时收费4元","累计收费2元","封顶费用20元"]
     * PARKING_ID : 1
     * ADDRESS : 商务外环12号
     * COLLECTION_ID : 0c41e8f4f69b42d2b5ca1d3dd4ce2e91
     * FREE_NUM : 1000
     * LAT : 34.782489
     * NAME : 亚视停车场1
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
        private String COLLECTION_ID;
        private String FREE_NUM;
        private String LAT;
        private String NAME;
        private List<String> costDetail;

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

        public List<String> getCostDetail() {
            return costDetail;
        }

        public void setCostDetail(List<String> costDetail) {
            this.costDetail = costDetail;
        }
    }
}
