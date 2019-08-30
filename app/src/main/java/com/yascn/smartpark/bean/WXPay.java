package com.yascn.smartpark.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by YASCN on 2017/1/10.
 */

public class WXPay {

    /**
     * msg : 正确返回数据
     * object : {"Tip":"截止支付时间为止，您在46分钟之内离场不再收取费用","appid":"wxa484f7e067a7526e","noncestr":"1713569006","package":"Sign=WXpay","partnerid":"1430377202","prepayid":"","sign":"CE11FBBDFE0EF6FD17B12525AD635D8D","timestamp":"1489396437"}
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
         * Tip : 截止支付时间为止，您在46分钟之内离场不再收取费用
         * appid : wxa484f7e067a7526e
         * noncestr : 1713569006
         * package : Sign=WXpay
         * partnerid : 1430377202
         * prepayid :
         * sign : CE11FBBDFE0EF6FD17B12525AD635D8D
         * timestamp : 1489396437
         */

        private String Tip;
        private String appid;
        private String noncestr;
        @SerializedName("package")
        private String packageX;
        private String partnerid;
        private String prepayid;
        private String sign;
        private String timestamp;

        public String getTip() {
            return Tip;
        }

        public void setTip(String Tip) {
            this.Tip = Tip;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        @Override
        public String toString() {
            return "ObjectBean{" +
                    "Tip='" + Tip + '\'' +
                    ", appid='" + appid + '\'' +
                    ", noncestr='" + noncestr + '\'' +
                    ", packageX='" + packageX + '\'' +
                    ", partnerid='" + partnerid + '\'' +
                    ", prepayid='" + prepayid + '\'' +
                    ", sign='" + sign + '\'' +
                    ", timestamp='" + timestamp + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "WXPay{" +
                "msg='" + msg + '\'' +
                ", object=" + object +
                ", statusCode=" + statusCode +
                '}';
    }
}
