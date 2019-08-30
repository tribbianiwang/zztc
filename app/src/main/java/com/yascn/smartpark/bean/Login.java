package com.yascn.smartpark.bean;

/**
 * Created by YASCN on 2017/9/7.
 */

public class Login {

    /**
     * msg : 正确返回数据
     * object : {"flag":"1","user_id":"02eeeeeb93304d2aa1901ed342f1ce32"}
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
         * flag : 1
         * user_id : 02eeeeeb93304d2aa1901ed342f1ce32
         */

        private String flag;
        private String user_id;

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }
}
