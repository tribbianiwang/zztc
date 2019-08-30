package com.yascn.smartpark.bean;

/**
 * Created by YASCN on 2017/1/11.
 */

public class ALIPay {

    /**
     * msg : 正确返回数据
     * object : {"Tip":"截止支付时间为止，您在48分钟之内离场不再收取费用","OrderStr":"app_id=2016122004457332&biz_content=%7B%22out_trade_no%22%3A%221489383845050936140%22%2C%22total_amount%22%3A%220.00%22%2C%22subject%22%3A%22%E4%BA%9A%E8%A7%86%E5%81%9C%E8%BD%A6%E5%9C%BA2%22%2C%22timeout_express%22%3A%2230m%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=utf-8&method=alipay.trade.app.pay¬ify_url=http%3A%2F%2Fz1i6276594.imwork.net%3A16776%2FYYParking%2Fapp%2Fcallbacks&sign_type=RSA×tamp=2017-03-13+17%3A11%3A34&version=1.0&sign=HtuE7G9lr1ltiaGOfRJcLw9gm3qwS81N4%2FoiRuAKsfZz3o4cwSsLLvKSrRuPA%2B%2FxG48HEHbv2nlfI8CQZRonLh7RevMu4844EdhnImHI6KHYF7LwdPIpAKZuI71QsGjZ3ZH5us4E428CX4ZleZsx9bdaw7j%2FMmaTbz34iOTBcMo%3D"}
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
         * Tip : 截止支付时间为止，您在48分钟之内离场不再收取费用
         * OrderStr : app_id=2016122004457332&biz_content=%7B%22out_trade_no%22%3A%221489383845050936140%22%2C%22total_amount%22%3A%220.00%22%2C%22subject%22%3A%22%E4%BA%9A%E8%A7%86%E5%81%9C%E8%BD%A6%E5%9C%BA2%22%2C%22timeout_express%22%3A%2230m%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=utf-8&method=alipay.trade.app.pay¬ify_url=http%3A%2F%2Fz1i6276594.imwork.net%3A16776%2FYYParking%2Fapp%2Fcallbacks&sign_type=RSA×tamp=2017-03-13+17%3A11%3A34&version=1.0&sign=HtuE7G9lr1ltiaGOfRJcLw9gm3qwS81N4%2FoiRuAKsfZz3o4cwSsLLvKSrRuPA%2B%2FxG48HEHbv2nlfI8CQZRonLh7RevMu4844EdhnImHI6KHYF7LwdPIpAKZuI71QsGjZ3ZH5us4E428CX4ZleZsx9bdaw7j%2FMmaTbz34iOTBcMo%3D
         */

        private String Tip;
        private String OrderStr;

        public String getTip() {
            return Tip;
        }

        public void setTip(String Tip) {
            this.Tip = Tip;
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
                    "Tip='" + Tip + '\'' +
                    ", OrderStr='" + OrderStr + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ALIPay{" +
                "msg='" + msg + '\'' +
                ", object=" + object +
                ", statusCode=" + statusCode +
                '}';
    }
}
