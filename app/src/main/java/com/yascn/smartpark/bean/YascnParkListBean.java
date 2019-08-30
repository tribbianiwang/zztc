package com.yascn.smartpark.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by YASCN on 2017/9/7.
 */

public class YascnParkListBean implements Serializable{


    /**
     * msg : 正确返回数据
     * object : [{"LNG":"113.726237","costDetail":["免费时长10分钟","第一小时收费4元","累计收费2元","封顶费用20元"],"PARKING_ID":"1","ADDRESS":"商务外环12号","COLLECTION_ID":"0","FREE_NUM":"1000","ET_LIST":[{"IMG":"http://192.168.1.25:8080/YYParking/uploadFiles/uploadImgs//20170327/9f68a6c1b411400996a70984e27adf3d.jpg","P_ENTRANCE_ID":"034507cee3174c4c9e1072476393b4ff","LNG":"1","PARKING_ID":"1","LAT":"1","NAME":"1"},{"IMG":"","P_ENTRANCE_ID":"4b0acdc01aae4922a1fb6d5ff661412c","LNG":"113.726098","PARKING_ID":"1","LAT":"34.782967","NAME":"北入口"}],"LAT":"34.782489","NAME":"亚视停车场1"},{"LNG":"113.725042","costDetail":["免费时长1分钟","第一小时收费5元","累计收费2元","封顶费用25元"],"PARKING_ID":"3","ADDRESS":"中油花园酒店","COLLECTION_ID":"0","FREE_NUM":"10","ET_LIST":[{"IMG":"","P_ENTRANCE_ID":"888451b924a440cbb917b433766328b5","LNG":"113.724791","PARKING_ID":"3","LAT":"34.780525","NAME":"西入口"},{"IMG":"http://192.168.1.25:8080/wrzs/uploadFiles/uploadImgs/20170517/20f9d5c98a174e049a2e9282a1250309.JPG","P_ENTRANCE_ID":"c5a11b1d2c0e4ff6b61571daddab6445","LNG":"113.725536","PARKING_ID":"3","LAT":"34.780387","NAME":"东入口"}],"LAT":"34.780784","NAME":"亚视停车场6"},{"LNG":"113.7369","costDetail":["免费时长1分钟","第一小时收费0.01元","累计收费0.01元","封顶费用30元"],"PARKING_ID":"2335e1f5d2384e889bc857582309a186","ADDRESS":"会展中心1","COLLECTION_ID":"0","FREE_NUM":"40","ET_LIST":[],"LAT":"34.778575","NAME":"郑东新区CBD10号停车场"}]
     * statusCode : 1
     */

    private String msg;
    private int statusCode;
    /**
     * LNG : 113.726237
     * costDetail : ["免费时长10分钟","第一小时收费4元","累计收费2元","封顶费用20元"]
     * PARKING_ID : 1
     * ADDRESS : 商务外环12号
     * COLLECTION_ID : 0
     * FREE_NUM : 1000
     * ET_LIST : [{"IMG":"http://192.168.1.25:8080/YYParking/uploadFiles/uploadImgs//20170327/9f68a6c1b411400996a70984e27adf3d.jpg","P_ENTRANCE_ID":"034507cee3174c4c9e1072476393b4ff","LNG":"1","PARKING_ID":"1","LAT":"1","NAME":"1"},{"IMG":"","P_ENTRANCE_ID":"4b0acdc01aae4922a1fb6d5ff661412c","LNG":"113.726098","PARKING_ID":"1","LAT":"34.782967","NAME":"北入口"}]
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

    public static class ObjectBean implements Serializable{
        private String LNG;
        private String PARKING_ID;
        private String ADDRESS;
        private String COLLECTION_ID;
        private String FREE_NUM;
        private String LAT;
        private String NAME;
        private List<String> costDetail;
        /**
         * IMG : http://192.168.1.25:8080/YYParking/uploadFiles/uploadImgs//20170327/9f68a6c1b411400996a70984e27adf3d.jpg
         * P_ENTRANCE_ID : 034507cee3174c4c9e1072476393b4ff
         * LNG : 1
         * PARKING_ID : 1
         * LAT : 1
         * NAME : 1
         */

        private List<ETLISTBean> ET_LIST;

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

        public List<ETLISTBean> getET_LIST() {
            return ET_LIST;
        }

        public void setET_LIST(List<ETLISTBean> ET_LIST) {
            this.ET_LIST = ET_LIST;
        }

        public static class ETLISTBean implements Serializable{
            private String IMG;
            private String P_ENTRANCE_ID;
            private String LNG;
            private String PARKING_ID;
            private String LAT;
            private String NAME;

            public String getIMG() {
                return IMG;
            }

            public void setIMG(String IMG) {
                this.IMG = IMG;
            }

            public String getP_ENTRANCE_ID() {
                return P_ENTRANCE_ID;
            }

            public void setP_ENTRANCE_ID(String P_ENTRANCE_ID) {
                this.P_ENTRANCE_ID = P_ENTRANCE_ID;
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
}
