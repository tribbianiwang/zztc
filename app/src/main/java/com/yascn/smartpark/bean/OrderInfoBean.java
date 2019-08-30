package com.yascn.smartpark.bean;

import java.io.Serializable;

/**
 * Created by YASCN on 2018/5/7.
 */

public class OrderInfoBean implements Serializable{


    /**
     * msg : 正确返回数据
     * object : {"number":"沪A11111","flag":"1","money":"0.01","pname":"郑东CBD99号停车场（测试）","etime":"2018-05-18 09:25:29","stime":"2018-05-18 09:19:51","stay_time":"5分钟38秒"}
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
        /**
         * number : 沪A11111
         * flag : 1
         * money : 0.01
         * pname : 郑东CBD99号停车场（测试）
         * etime : 2018-05-18 09:25:29
         * stime : 2018-05-18 09:19:51
         * stay_time : 5分钟38秒
         */

        private String number;
        private String flag;
        private String money;
        private String pname;
        private String etime;
        private String stime;
        private String stay_time;
        private String isBalancePay;

        public String getIsBalancePay() {
            return isBalancePay;
        }

        public void setIsBalancePay(String isBalancePay) {
            this.isBalancePay = isBalancePay;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getPname() {
            return pname;
        }

        public void setPname(String pname) {
            this.pname = pname;
        }

        public String getEtime() {
            return etime;
        }

        public void setEtime(String etime) {
            this.etime = etime;
        }

        public String getStime() {
            return stime;
        }

        public void setStime(String stime) {
            this.stime = stime;
        }

        public String getStay_time() {
            return stay_time;
        }

        public void setStay_time(String stay_time) {
            this.stay_time = stay_time;
        }

        @Override
        public String toString() {
            return "ObjectBean{" +
                    "number='" + number + '\'' +
                    ", flag='" + flag + '\'' +
                    ", money='" + money + '\'' +
                    ", pname='" + pname + '\'' +
                    ", etime='" + etime + '\'' +
                    ", stime='" + stime + '\'' +
                    ", stay_time='" + stay_time + '\'' +
                    ", isBalancePay='" + isBalancePay + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "OrderInfoBean{" +
                "msg='" + msg + '\'' +
                ", object=" + object +
                ", statusCode=" + statusCode +
                '}';
    }
}
