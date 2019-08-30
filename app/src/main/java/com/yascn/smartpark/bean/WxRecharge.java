package com.yascn.smartpark.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by YASCN on 2017/4/26.
 */

public class WxRecharge {

    /**
     * msg : 正确返回数据
     * object : {"appid":"wxa484f7e067a7526e","flag":"1","noncestr":"1821271902","package":"Sign=WXpay","partnerid":"1430377202","prepayid":"wx2017042618215026ca55a1ec0192093465","sign":"37B5D272560CD1C26ADF428C33BDD31C","timestamp":"1493202087"}
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
         * appid : wxa484f7e067a7526e
         * flag : 1
         * noncestr : 1821271902
         * package : Sign=WXpay
         * partnerid : 1430377202
         * prepayid : wx2017042618215026ca55a1ec0192093465
         * sign : 37B5D272560CD1C26ADF428C33BDD31C
         * timestamp : 1493202087
         */

        private String appid;
        private String flag;
        private String noncestr;
        @SerializedName("package")
        private String packageX;
        private String partnerid;
        private String prepayid;
        private String sign;
        private String timestamp;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
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
                    "appid='" + appid + '\'' +
                    ", flag='" + flag + '\'' +
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
        return "WxRecharge{" +
                "msg='" + msg + '\'' +
                ", object=" + object +
                ", statusCode=" + statusCode +
                '}';
    }
}
