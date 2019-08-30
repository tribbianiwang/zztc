package com.yascn.smartpark.bean;

/**
 * Created by YASCN on 2017/1/19.
 */

public class EditU {

    /**
     * msg : 正确返回数据
     * object : {"IMG":"http://192.168.1.25:8080/YYParking/uploadFiles/uploadImgs/20170119/e7ea0e6d03014249a3f0039de732f4d0.png","flag":"1"}
     * statusCode : 1
     */

    private String msg;
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
        /**
         * IMG : http://192.168.1.25:8080/YYParking/uploadFiles/uploadImgs/20170119/e7ea0e6d03014249a3f0039de732f4d0.png
         * flag : 1
         */

        private String IMG;
        private String flag;

        public String getIMG() {
            return IMG;
        }

        public void setIMG(String IMG) {
            this.IMG = IMG;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        @Override
        public String toString() {
            return "ObjectBean{" +
                    "IMG='" + IMG + '\'' +
                    ", flag='" + flag + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "EditU{" +
                "msg='" + msg + '\'' +
                ", object=" + object +
                ", statusCode=" + statusCode +
                '}';
    }
}
