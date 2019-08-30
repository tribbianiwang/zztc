package com.yascn.smartpark.bean;

/**
 * Created by YASCN on 2017/9/16.
 */

public class Comment {
    /**
     * msg : 正确返回数据
     * object : {"flag":"1"}
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
         */

        private String flag;

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        @Override
        public String toString() {
            return "ObjectBean{" +
                    "flag='" + flag + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DefaultBean{" +
                "msg='" + msg + '\'' +
                ", object=" + object +
                ", statusCode=" + statusCode +
                '}';
    }
}
