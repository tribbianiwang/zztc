package com.yascn.smartpark.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/10.
 */

public class ReRecordList implements Serializable{

    /**
     * msg : 正确返回数据
     * object : [{"CATE":"1","MONEY":"5.00","TIME":"2017-05-19 16:03:12","TYPE":"2","r_list":[{"CATE":"1","MONEY":"5.00","TIME":"2017-05-18 10:21:38","TYPE":"2"}]},{"CATE":"1","MONEY":"5.00","TIME":"2017-05-19 11:03:42","TYPE":"2","r_list":[]},{"CATE":"1","MONEY":"9.00","TIME":"2017-05-19 11:00:52","TYPE":"2","r_list":[{"CATE":"1","MONEY":"5.00","TIME":"2017-05-18 10:24:42","TYPE":"2"},{"CATE":"1","MONEY":"13.00","TIME":"2017-05-19 15:51:31","TYPE":"2"}]},{"CATE":"1","MONEY":"25.00","TIME":"2017-05-19 08:44:24","TYPE":"2","r_list":[{"CATE":"1","MONEY":"5.00","TIME":"2017-05-19 16:07:05","TYPE":"2"}]},{"CATE":"1","MONEY":"5.00","TIME":"2017-05-18 10:00:12","TYPE":"2","r_list":[]},{"CATE":"1","MONEY":"25.00","TIME":"2017-05-18 09:02:14","TYPE":"2","r_list":[]}]
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
         * CATE : 1
         * MONEY : 5.00
         * TIME : 2017-05-19 16:03:12
         * TYPE : 2
         * r_list : [{"CATE":"1","MONEY":"5.00","TIME":"2017-05-18 10:21:38","TYPE":"2"}]
         */

        private String CATE;
        private String MONEY;
        private String TIME;
        private String TYPE;
        private List<RListBean> r_list;

        public String getCATE() {
            return CATE;
        }

        public void setCATE(String CATE) {
            this.CATE = CATE;
        }

        public String getMONEY() {
            return MONEY;
        }

        public void setMONEY(String MONEY) {
            this.MONEY = MONEY;
        }

        public String getTIME() {
            return TIME;
        }

        public void setTIME(String TIME) {
            this.TIME = TIME;
        }

        public String getTYPE() {
            return TYPE;
        }

        public void setTYPE(String TYPE) {
            this.TYPE = TYPE;
        }

        public List<RListBean> getR_list() {
            return r_list;
        }

        public void setR_list(List<RListBean> r_list) {
            this.r_list = r_list;
        }

        public static class RListBean implements Serializable{
            /**
             * CATE : 1
             * MONEY : 5.00
             * TIME : 2017-05-18 10:21:38
             * TYPE : 2
             */

            private String CATE;
            private String MONEY;
            private String TIME;
            private String TYPE;

            public String getCATE() {
                return CATE;
            }

            public void setCATE(String CATE) {
                this.CATE = CATE;
            }

            public String getMONEY() {
                return MONEY;
            }

            public void setMONEY(String MONEY) {
                this.MONEY = MONEY;
            }

            public String getTIME() {
                return TIME;
            }

            public void setTIME(String TIME) {
                this.TIME = TIME;
            }

            public String getTYPE() {
                return TYPE;
            }

            public void setTYPE(String TYPE) {
                this.TYPE = TYPE;
            }

            @Override
            public String toString() {
                return "RListBean{" +
                        "CATE='" + CATE + '\'' +
                        ", MONEY='" + MONEY + '\'' +
                        ", TIME='" + TIME + '\'' +
                        ", TYPE='" + TYPE + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "ObjectBean{" +
                    "CATE='" + CATE + '\'' +
                    ", MONEY='" + MONEY + '\'' +
                    ", TIME='" + TIME + '\'' +
                    ", TYPE='" + TYPE + '\'' +
                    ", r_list=" + r_list +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ReRecordList{" +
                "msg='" + msg + '\'' +
                ", statusCode=" + statusCode +
                ", object=" + object +
                '}';
    }
}
