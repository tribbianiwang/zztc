package com.yascn.smartpark.bean;

/**
 * Created by YASCN on 2017/9/13.
 */

public class GetPayMoney {

    /**
     * msg : 正确返回数据
     * object : {"STATUS":"1","money":"6.00","balance":"0.02","isHavePwd":"0","detail":"1269819"}
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
         * STATUS : 1
         * money : 6.00
         * balance : 0.02
         * isHavePwd : 0
         * detail : 1269819
         */

        private String STATUS;
        private String money;
        private String balance;
        private String isHavePwd;
        private String detail;
        private String stay_time;

        public String getMoneyS() {
            return moneyS;
        }

        public void setMoneyS(String moneyS) {
            this.moneyS = moneyS;
        }

        private String moneyS;


        public String getStay_time() {
            return stay_time;
        }

        public void setStay_time(String stay_time) {
            this.stay_time = stay_time;
        }

        public String getSTATUS() {
            return STATUS;
        }

        public void setSTATUS(String STATUS) {
            this.STATUS = STATUS;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getIsHavePwd() {
            return isHavePwd;
        }

        public void setIsHavePwd(String isHavePwd) {
            this.isHavePwd = isHavePwd;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }


    }


}
