package com.yascn.smartpark.bean;

/**
 * Created by YASCN on 2017/4/26.
 */

public class AliRecharge {

    /**
     * msg : 正确返回数据
     * object : {"flag":"1","OrderStr":"app_id=2016122004457332&biz_content=%7B%22out_trade_no%22%3A%221493195468338276093%22%2C%22total_amount%22%3A%2220%22%2C%22subject%22%3A%22%E4%BA%9A%E8%A7%86%E5%81%9C%E8%BD%A6%E4%BD%99%E9%A2%9D%E5%85%85%E5%80%BC%22%2C%22timeout_express%22%3A%2230m%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=utf-8&method=alipay.trade.app.pay¬ify_url=http%3A%2F%2Fz1i6276594.imwork.net%3A16776%2FYYParking%2Fapp%2Fcallbacks&sign_type=RSA×tamp=2017-04-26+16%3A31%3A08&version=1.0&sign=aLEcy%2Fy3SwujIurh%2BZL4SJdcIUbGgc7Ud4KD%2FknpRPNr2uHCTQOaqWXraisvJAmOCY%2FMvgxW5pmvU8B11%2BFmYCdGhGdxFZTBWbj4MOn3lSg1P19ayHZSksPS5NxHO2oKy%2FEMvbSxYj3VbASL9Ehy7McwhA0kaLwUZpGvlIvMyoQ%3D"}
     * statusCode : 1
     */

//    {"msg":"正确返回数据","object":{"flag":"1","orderStr":"app_id=2016122004457332&biz_content=%7B%22out_trade_no%22%3A%220.1%22%2C%22total_amount%22%3A%220.1%22%2C%22subject%22%3A%22%E9%83%91%E4%B8%9C%E6%96%B0%E5%8C%BACBD99%E5%8F%B7%28%E6%B5%8B%E8%AF%95%29-7%E6%9C%88%E5%8D%A1-%E8%B1%ABUUUUUU%22%2C%22timeout_express%22%3A%2210m%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=utf-8&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fz1i6276594.imwork.net%2Fwrzs%2Fapp2%2Fcallbacks&sign_type=RSA&timestamp=2018-11-06+09%3A57%3A45&version=1.0&sign=EKc%2FEbyAkYeiSdsq49O3jqPsLHeNrrgd2hqWQh5eMPHLj6Cvbs626xeF%2BfEgVvPkEl04AKqathicsnLURk3TnkdmoUsLpm71mqj%2F9HPOeUHEVk9TtnjKN9RcLLbBZ8LHiH53jsGjMvFTzq4oDrz17KUkKrhC%2F3%2BSP%2F%2BZmNOPn%2Fs%3D"},"statusCode":1}

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
         * OrderStr : app_id=2016122004457332&biz_content=%7B%22out_trade_no%22%3A%221493195468338276093%22%2C%22total_amount%22%3A%2220%22%2C%22subject%22%3A%22%E4%BA%9A%E8%A7%86%E5%81%9C%E8%BD%A6%E4%BD%99%E9%A2%9D%E5%85%85%E5%80%BC%22%2C%22timeout_express%22%3A%2230m%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=utf-8&method=alipay.trade.app.pay¬ify_url=http%3A%2F%2Fz1i6276594.imwork.net%3A16776%2FYYParking%2Fapp%2Fcallbacks&sign_type=RSA×tamp=2017-04-26+16%3A31%3A08&version=1.0&sign=aLEcy%2Fy3SwujIurh%2BZL4SJdcIUbGgc7Ud4KD%2FknpRPNr2uHCTQOaqWXraisvJAmOCY%2FMvgxW5pmvU8B11%2BFmYCdGhGdxFZTBWbj4MOn3lSg1P19ayHZSksPS5NxHO2oKy%2FEMvbSxYj3VbASL9Ehy7McwhA0kaLwUZpGvlIvMyoQ%3D
         */

        private String flag;
        private String OrderStr;

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getOrderStr() {
            return OrderStr;
        }

        public void setOrderStr(String OrderStr) {
            this.OrderStr = OrderStr;
        }

        @Override
        public String toString() {
            return "ObjectBean{" +
                    "flag='" + flag + '\'' +
                    ", OrderStr='" + OrderStr + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AliRecharge{" +
                "msg='" + msg + '\'' +
                ", object=" + object +
                ", statusCode=" + statusCode +
                '}';
    }


}
