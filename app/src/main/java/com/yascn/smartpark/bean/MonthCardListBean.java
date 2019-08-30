package com.yascn.smartpark.bean;

import java.util.List;

public class MonthCardListBean {


    /**
     * msg : 正确返回数据
     * object : [{"address":"郑州市金水区商务外环路12号","park_id":"1eaa0331bea44956b13709f4a7490d40","name":"郑东CBD99号停车场（测试）"},{"address":"郑州市金水区众意路与商务外环路交叉口北150米 ","park_id":"2335e1f5d2384e889bc857582309a186","name":"郑东新区CBD1号停车场"},{"address":"郑州市金水区众意西路","park_id":"3","name":"郑东新区CBD2号停车场"},{"address":"郑州市金水区九如路与商务外环路交叉口东北角","park_id":"f15f68f9991f4918bb2ccd66910623a9","name":"郑东新区CBD9号停车场"}]
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

    public static class ObjectBean {
        /**
         * address : 郑州市金水区商务外环路12号
         * park_id : 1eaa0331bea44956b13709f4a7490d40
         * name : 郑东CBD99号停车场（测试）
         */

        private String address;
        private String park_id;
        private String name;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPark_id() {
            return park_id;
        }

        public void setPark_id(String park_id) {
            this.park_id = park_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
