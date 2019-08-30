package com.yascn.smartpark.bean;

/**
 * Created by YASCN on 2018/5/9.
 */

public class WalletPayBean {

    /**
     * msg : 正确返回数据
     * object : {"flag":"2","Tip":""}
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
         * flag : 2
         * Tip :
         */

        private String flag;
        private String Tip;

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getTip() {
            return Tip;
        }

        public void setTip(String Tip) {
            this.Tip = Tip;
        }
    }
}
