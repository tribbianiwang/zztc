package com.yascn.smartpark.bean;

import java.io.Serializable;

/**
 * Created by YASCN on 2018/5/5.
 */

public class QueryCarLicenseFeeBean implements Serializable{


    /**
     * msg : 正确返回数据
     * object : {"order_no":"1525510099385749392","number":"苏222222","start_time":"2018-05-05 16:42:09","ORDER_FORM_ID":"16f70d22c8eb4dedb601df01d0ee992d","amount":"0.01","paid":"0.01","park_name":"郑东CBD99号停车场（测试）","stay_time":"34分钟18秒","park_no":"99"}
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

    public static class ObjectBean implements Serializable{
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        /**
         * order_no : 1525510099385749392
         * number : 苏222222
         * start_time : 2018-05-05 16:42:09
         * ORDER_FORM_ID : 16f70d22c8eb4dedb601df01d0ee992d
         * amount : 0.01
         * paid : 0.01
         * park_name : 郑东CBD99号停车场（测试）
         * stay_time : 34分钟18秒
         * park_no : 99
         */
        private String status;
        private String order_no;
        private String number;
        private String start_time;
        private String ORDER_FORM_ID;
        private String amount;
        private String paid;
        private String park_name;
        private String stay_time;
        private String park_no;
        private String isBalancePay;

        public String getIsBalancePay() {
            return isBalancePay;
        }

        public void setIsBalancePay(String isBalancePay) {
            this.isBalancePay = isBalancePay;
        }


        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getORDER_FORM_ID() {
            return ORDER_FORM_ID;
        }

        public void setORDER_FORM_ID(String ORDER_FORM_ID) {
            this.ORDER_FORM_ID = ORDER_FORM_ID;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getPaid() {
            return paid;
        }

        public void setPaid(String paid) {
            this.paid = paid;
        }

        public String getPark_name() {
            return park_name;
        }

        public void setPark_name(String park_name) {
            this.park_name = park_name;
        }

        public String getStay_time() {
            return stay_time;
        }

        public void setStay_time(String stay_time) {
            this.stay_time = stay_time;
        }

        public String getPark_no() {
            return park_no;
        }

        public void setPark_no(String park_no) {
            this.park_no = park_no;
        }
    }
}
