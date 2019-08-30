package com.yascn.smartpark.bean;

import java.io.Serializable;
import java.util.List;

public class MonthCardDetailBean implements Serializable {

    /**
     * msg : 正确返回数据
     * object : [{"money":"122","monthcard_id":"02b3658970384e0a88efbb6b1430f1d1","name":"郑东CBD99号停车场（测试）","days":"7","type":"周卡"},{"money":"111","monthcard_id":"72df1ad978c14528999f8050a7a677fb","name":"郑东CBD99号停车场（测试）","days":"7","type":"周卡"},{"money":"123","monthcard_id":"b9d4e2cc42df4eac85e064c8aad11d7a","name":"郑东CBD99号停车场（测试）","days":"7","type":"周卡"},{"money":"11","monthcard_id":"d235b5c917d84557b49dc58ea55f3c3b","name":"郑东CBD99号停车场（测试）","days":"7","type":"周卡"},{"money":"333","monthcard_id":"faddc08c210d4e049783975eb5fa1ee8","name":"郑东CBD99号停车场（测试）","days":"365","type":"年卡"}]
     * statusCode : 1
     */

    private String msg;
    private int statusCode;
    private List<ObjectBean> object;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<ObjectBean> getObject() {
        return object;
    }

    public void setObject(List<ObjectBean> object) {
        this.object = object;
    }

    public static class ObjectBean implements Serializable{
        /**
         * money : 122
         * monthcard_id : 02b3658970384e0a88efbb6b1430f1d1
         * name : 郑东CBD99号停车场（测试）
         * days : 7
         * type : 周卡
         */

        private String money;
        private String monthcard_id;
        private String name;
        private String days;
        private String type;

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getMonthcard_id() {
            return monthcard_id;
        }

        public void setMonthcard_id(String monthcard_id) {
            this.monthcard_id = monthcard_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDays() {
            return days;
        }

        public void setDays(String days) {
            this.days = days;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
