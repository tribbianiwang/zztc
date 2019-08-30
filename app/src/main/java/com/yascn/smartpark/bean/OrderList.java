package com.yascn.smartpark.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by YASCN on 2017/9/14.
 */

public class OrderList implements Serializable{


    /**
     * msg : 正确返回数据
     * object : {"sum":"0.09","order_list":[{"ORDER_FORM_ID":"f028e5d356dd45468e1ea8e1ae662bc8","START_TIME":"2017-10-31 14:45:32","NUMBER":"豫U99999","MONEY":"0.01","IS_COM":"0","ORDER_NO":"1509432353488878711","TIME":"43分钟","NAME":"郑东CBD99号停车场（测试）","PAY_TIME":"2017-10-31 15:29:46","ZB_PIC":"http://app.yascn.com/wrzs/uploadFiles/uploadImgs/sys/2htcc.jpg","END_TINE":"2017-10-31 15:29:25","STATUS":"3","ADDRESS":"郑州市金水区商务外环路12号","ORDER_TIME":"2017-10-31 14:45:53","PAY_METHOD":"4-2"},{"ORDER_FORM_ID":"fd89178dfa0243efb5e3d36e6af6de61","START_TIME":"2017-10-24 16:09:34","NUMBER":"豫LLLLLL","MONEY":"0","IS_COM":"0","ORDER_NO":"1508832594634122972","TIME":"40分钟","NAME":"郑东CBD99号停车场（测试）","PAY_TIME":"2017-10-24 16:50:41","ZB_PIC":"http://app.yascn.com/wrzs/uploadFiles/uploadImgs/sys/2htcc.jpg","END_TINE":"2017-10-24 16:50:24","STATUS":"3","ADDRESS":"郑州市金水区商务外环路12号","ORDER_TIME":"2017-10-24 16:09:54","PAY_METHOD":"3"},{"ORDER_FORM_ID":"ff35d7aa0e1b4addb05b39eec3590e5d","START_TIME":"2017-10-24 15:26:35","NUMBER":"豫LLLLLL","MONEY":"0.01","IS_COM":"0","ORDER_NO":"1508830015842994833","TIME":"41分钟","NAME":"郑东CBD99号停车场（测试）","PAY_TIME":"2017-10-24 16:07:55","ZB_PIC":"http://app.yascn.com/wrzs/uploadFiles/uploadImgs/sys/2htcc.jpg","END_TINE":"2017-10-24 16:07:37","STATUS":"3","ADDRESS":"郑州市金水区商务外环路12号","ORDER_TIME":"2017-10-24 15:26:55","PAY_METHOD":"3"},{"ORDER_FORM_ID":"3707807891fa495ea4d4537621d8cc25","START_TIME":"2017-10-24 15:05:07","NUMBER":"豫LLLLLL","MONEY":"0.01","IS_COM":"0","ORDER_NO":"1508828728111710313","TIME":"20分钟","NAME":"郑东CBD99号停车场（测试）","PAY_TIME":"2017-10-24 15:26:09","ZB_PIC":"http://app.yascn.com/wrzs/uploadFiles/uploadImgs/sys/2htcc.jpg","END_TINE":"2017-10-24 15:25:52","STATUS":"3","ADDRESS":"郑州市金水区商务外环路12号","ORDER_TIME":"2017-10-24 15:05:28","PAY_METHOD":"4-2"},{"ORDER_FORM_ID":"c6ad22af80da476e98fe1a0b5aaf74ff","START_TIME":"2017-10-24 14:58:59","NUMBER":"豫LLLLLL","MONEY":"0.01","IS_COM":"0","ORDER_NO":"1508828360379279262","TIME":"2分钟","NAME":"郑东CBD99号停车场（测试）","PAY_TIME":"2017-10-24 15:01:38","ZB_PIC":"http://app.yascn.com/wrzs/uploadFiles/uploadImgs/sys/2htcc.jpg","END_TINE":"2017-10-24 15:01:21","STATUS":"3","ADDRESS":"郑州市金水区商务外环路12号","ORDER_TIME":"2017-10-24 14:59:20","PAY_METHOD":"3"},{"ORDER_FORM_ID":"63df7736bf814b3db51742da2e724686","START_TIME":"2017-10-24 14:55:04","NUMBER":"豫LLLLLL","MONEY":"0.01","IS_COM":"0","ORDER_NO":"1508828124518788053","TIME":"3分钟","NAME":"郑东CBD99号停车场（测试）","PAY_TIME":"2017-10-24 14:58:37","ZB_PIC":"http://app.yascn.com/wrzs/uploadFiles/uploadImgs/sys/2htcc.jpg","END_TINE":"2017-10-24 14:58:20","STATUS":"3","ADDRESS":"郑州市金水区商务外环路12号","ORDER_TIME":"2017-10-24 14:55:24","PAY_METHOD":"3"},{"ORDER_FORM_ID":"34b312599b9e459b96d7221bca061606","START_TIME":"2017-10-24 14:48:18","NUMBER":"豫LLLLLL","MONEY":"0.01","IS_COM":"0","ORDER_NO":"1508827719209191673","TIME":"3分钟","NAME":"郑东CBD99号停车场（测试）","PAY_TIME":"2017-10-24 14:52:14","ZB_PIC":"http://app.yascn.com/wrzs/uploadFiles/uploadImgs/sys/2htcc.jpg","END_TINE":"2017-10-24 14:52:13","STATUS":"3","ADDRESS":"郑州市金水区商务外环路12号","ORDER_TIME":"2017-10-24 14:48:39","PAY_METHOD":"1-2"},{"ORDER_FORM_ID":"9eccec22f18c4976a3cf3fd54b4aa6a8","START_TIME":"2017-10-24 14:41:32","NUMBER":"豫LLLLLL","MONEY":"0.01","IS_COM":"0","ORDER_NO":"1508827313125728131","TIME":"4分钟","NAME":"郑东CBD99号停车场（测试）","PAY_TIME":"2017-10-24 14:45:52","ZB_PIC":"http://app.yascn.com/wrzs/uploadFiles/uploadImgs/sys/2htcc.jpg","END_TINE":"2017-10-24 14:45:33","STATUS":"3","ADDRESS":"郑州市金水区商务外环路12号","ORDER_TIME":"2017-10-24 14:41:53","PAY_METHOD":"1-2"},{"ORDER_FORM_ID":"890efb4dd7c44cbc97f55d18fe403cf7","START_TIME":"2017-10-24 14:32:01","NUMBER":"豫LLLLLL","MONEY":"0.01","IS_COM":"0","ORDER_NO":"1508826742133160674","TIME":"3分钟","NAME":"郑东CBD99号停车场（测试）","PAY_TIME":"2017-10-24 14:35:49","ZB_PIC":"http://app.yascn.com/wrzs/uploadFiles/uploadImgs/sys/2htcc.jpg","END_TINE":"2017-10-24 14:35:32","STATUS":"3","ADDRESS":"郑州市金水区商务外环路12号","ORDER_TIME":"2017-10-24 14:32:22","PAY_METHOD":"4-2"},{"ORDER_FORM_ID":"9c3f5ed309654e738cd5f4b9dbfbee06","START_TIME":"2017-10-24 14:22:17","NUMBER":"豫LLLLLL","MONEY":"0.01","IS_COM":"0","ORDER_NO":"1508826454874630617","TIME":"7分钟","NAME":"郑东CBD99号停车场（测试）","PAY_TIME":"2017-10-24 14:27:42","ZB_PIC":"http://app.yascn.com/wrzs/uploadFiles/uploadImgs/sys/2htcc.jpg","END_TINE":"2017-10-24 14:29:42","STATUS":"3","ADDRESS":"郑州市金水区商务外环路12号","ORDER_TIME":"2017-10-24 14:22:38","PAY_METHOD":"1-1"}]}
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
         * sum : 0.09
         * order_list : [{"ORDER_FORM_ID":"f028e5d356dd45468e1ea8e1ae662bc8","START_TIME":"2017-10-31 14:45:32","NUMBER":"豫U99999","MONEY":"0.01","IS_COM":"0","ORDER_NO":"1509432353488878711","TIME":"43分钟","NAME":"郑东CBD99号停车场（测试）","PAY_TIME":"2017-10-31 15:29:46","ZB_PIC":"http://app.yascn.com/wrzs/uploadFiles/uploadImgs/sys/2htcc.jpg","END_TINE":"2017-10-31 15:29:25","STATUS":"3","ADDRESS":"郑州市金水区商务外环路12号","ORDER_TIME":"2017-10-31 14:45:53","PAY_METHOD":"4-2"},{"ORDER_FORM_ID":"fd89178dfa0243efb5e3d36e6af6de61","START_TIME":"2017-10-24 16:09:34","NUMBER":"豫LLLLLL","MONEY":"0","IS_COM":"0","ORDER_NO":"1508832594634122972","TIME":"40分钟","NAME":"郑东CBD99号停车场（测试）","PAY_TIME":"2017-10-24 16:50:41","ZB_PIC":"http://app.yascn.com/wrzs/uploadFiles/uploadImgs/sys/2htcc.jpg","END_TINE":"2017-10-24 16:50:24","STATUS":"3","ADDRESS":"郑州市金水区商务外环路12号","ORDER_TIME":"2017-10-24 16:09:54","PAY_METHOD":"3"},{"ORDER_FORM_ID":"ff35d7aa0e1b4addb05b39eec3590e5d","START_TIME":"2017-10-24 15:26:35","NUMBER":"豫LLLLLL","MONEY":"0.01","IS_COM":"0","ORDER_NO":"1508830015842994833","TIME":"41分钟","NAME":"郑东CBD99号停车场（测试）","PAY_TIME":"2017-10-24 16:07:55","ZB_PIC":"http://app.yascn.com/wrzs/uploadFiles/uploadImgs/sys/2htcc.jpg","END_TINE":"2017-10-24 16:07:37","STATUS":"3","ADDRESS":"郑州市金水区商务外环路12号","ORDER_TIME":"2017-10-24 15:26:55","PAY_METHOD":"3"},{"ORDER_FORM_ID":"3707807891fa495ea4d4537621d8cc25","START_TIME":"2017-10-24 15:05:07","NUMBER":"豫LLLLLL","MONEY":"0.01","IS_COM":"0","ORDER_NO":"1508828728111710313","TIME":"20分钟","NAME":"郑东CBD99号停车场（测试）","PAY_TIME":"2017-10-24 15:26:09","ZB_PIC":"http://app.yascn.com/wrzs/uploadFiles/uploadImgs/sys/2htcc.jpg","END_TINE":"2017-10-24 15:25:52","STATUS":"3","ADDRESS":"郑州市金水区商务外环路12号","ORDER_TIME":"2017-10-24 15:05:28","PAY_METHOD":"4-2"},{"ORDER_FORM_ID":"c6ad22af80da476e98fe1a0b5aaf74ff","START_TIME":"2017-10-24 14:58:59","NUMBER":"豫LLLLLL","MONEY":"0.01","IS_COM":"0","ORDER_NO":"1508828360379279262","TIME":"2分钟","NAME":"郑东CBD99号停车场（测试）","PAY_TIME":"2017-10-24 15:01:38","ZB_PIC":"http://app.yascn.com/wrzs/uploadFiles/uploadImgs/sys/2htcc.jpg","END_TINE":"2017-10-24 15:01:21","STATUS":"3","ADDRESS":"郑州市金水区商务外环路12号","ORDER_TIME":"2017-10-24 14:59:20","PAY_METHOD":"3"},{"ORDER_FORM_ID":"63df7736bf814b3db51742da2e724686","START_TIME":"2017-10-24 14:55:04","NUMBER":"豫LLLLLL","MONEY":"0.01","IS_COM":"0","ORDER_NO":"1508828124518788053","TIME":"3分钟","NAME":"郑东CBD99号停车场（测试）","PAY_TIME":"2017-10-24 14:58:37","ZB_PIC":"http://app.yascn.com/wrzs/uploadFiles/uploadImgs/sys/2htcc.jpg","END_TINE":"2017-10-24 14:58:20","STATUS":"3","ADDRESS":"郑州市金水区商务外环路12号","ORDER_TIME":"2017-10-24 14:55:24","PAY_METHOD":"3"},{"ORDER_FORM_ID":"34b312599b9e459b96d7221bca061606","START_TIME":"2017-10-24 14:48:18","NUMBER":"豫LLLLLL","MONEY":"0.01","IS_COM":"0","ORDER_NO":"1508827719209191673","TIME":"3分钟","NAME":"郑东CBD99号停车场（测试）","PAY_TIME":"2017-10-24 14:52:14","ZB_PIC":"http://app.yascn.com/wrzs/uploadFiles/uploadImgs/sys/2htcc.jpg","END_TINE":"2017-10-24 14:52:13","STATUS":"3","ADDRESS":"郑州市金水区商务外环路12号","ORDER_TIME":"2017-10-24 14:48:39","PAY_METHOD":"1-2"},{"ORDER_FORM_ID":"9eccec22f18c4976a3cf3fd54b4aa6a8","START_TIME":"2017-10-24 14:41:32","NUMBER":"豫LLLLLL","MONEY":"0.01","IS_COM":"0","ORDER_NO":"1508827313125728131","TIME":"4分钟","NAME":"郑东CBD99号停车场（测试）","PAY_TIME":"2017-10-24 14:45:52","ZB_PIC":"http://app.yascn.com/wrzs/uploadFiles/uploadImgs/sys/2htcc.jpg","END_TINE":"2017-10-24 14:45:33","STATUS":"3","ADDRESS":"郑州市金水区商务外环路12号","ORDER_TIME":"2017-10-24 14:41:53","PAY_METHOD":"1-2"},{"ORDER_FORM_ID":"890efb4dd7c44cbc97f55d18fe403cf7","START_TIME":"2017-10-24 14:32:01","NUMBER":"豫LLLLLL","MONEY":"0.01","IS_COM":"0","ORDER_NO":"1508826742133160674","TIME":"3分钟","NAME":"郑东CBD99号停车场（测试）","PAY_TIME":"2017-10-24 14:35:49","ZB_PIC":"http://app.yascn.com/wrzs/uploadFiles/uploadImgs/sys/2htcc.jpg","END_TINE":"2017-10-24 14:35:32","STATUS":"3","ADDRESS":"郑州市金水区商务外环路12号","ORDER_TIME":"2017-10-24 14:32:22","PAY_METHOD":"4-2"},{"ORDER_FORM_ID":"9c3f5ed309654e738cd5f4b9dbfbee06","START_TIME":"2017-10-24 14:22:17","NUMBER":"豫LLLLLL","MONEY":"0.01","IS_COM":"0","ORDER_NO":"1508826454874630617","TIME":"7分钟","NAME":"郑东CBD99号停车场（测试）","PAY_TIME":"2017-10-24 14:27:42","ZB_PIC":"http://app.yascn.com/wrzs/uploadFiles/uploadImgs/sys/2htcc.jpg","END_TINE":"2017-10-24 14:29:42","STATUS":"3","ADDRESS":"郑州市金水区商务外环路12号","ORDER_TIME":"2017-10-24 14:22:38","PAY_METHOD":"1-1"}]
         */

        private String sum;
        private List<OrderListBean> order_list;

        public String getSum() {
            return sum;
        }

        public void setSum(String sum) {
            this.sum = sum;
        }

        public List<OrderListBean> getOrder_list() {
            return order_list;
        }

        public void setOrder_list(List<OrderListBean> order_list) {
            this.order_list = order_list;
        }

        public static class OrderListBean implements Serializable{
            /**
             * ORDER_FORM_ID : f028e5d356dd45468e1ea8e1ae662bc8
             * START_TIME : 2017-10-31 14:45:32
             * NUMBER : 豫U99999
             * MONEY : 0.01
             * IS_COM : 0
             * ORDER_NO : 1509432353488878711
             * TIME : 43分钟
             * NAME : 郑东CBD99号停车场（测试）
             * PAY_TIME : 2017-10-31 15:29:46
             * ZB_PIC : http://app.yascn.com/wrzs/uploadFiles/uploadImgs/sys/2htcc.jpg
             * END_TINE : 2017-10-31 15:29:25
             * STATUS : 3
             * ADDRESS : 郑州市金水区商务外环路12号
             * ORDER_TIME : 2017-10-31 14:45:53
             * PAY_METHOD : 4-2
             */

            private String ORDER_FORM_ID;
            private String START_TIME;
            private String NUMBER;
            private String MONEY;
            private String IS_COM;
            private String ORDER_NO;
            private String TIME;
            private String NAME;
            private String PAY_TIME;
            private String ZB_PIC;
            private String END_TINE;
            private String STATUS;
            private String ADDRESS;
            private String ORDER_TIME;
            private String PAY_METHOD;

            public String getCOUPONM() {
                return COUPONM;
            }

            public void setCOUPONM(String COUPONM) {
                this.COUPONM = COUPONM;
            }

            private String COUPONM;

            public String getORDER_FORM_ID() {
                return ORDER_FORM_ID;
            }

            public void setORDER_FORM_ID(String ORDER_FORM_ID) {
                this.ORDER_FORM_ID = ORDER_FORM_ID;
            }

            public String getSTART_TIME() {
                return START_TIME;
            }

            public void setSTART_TIME(String START_TIME) {
                this.START_TIME = START_TIME;
            }

            public String getNUMBER() {
                return NUMBER;
            }

            public void setNUMBER(String NUMBER) {
                this.NUMBER = NUMBER;
            }

            public String getMONEY() {
                return MONEY;
            }

            public void setMONEY(String MONEY) {
                this.MONEY = MONEY;
            }

            public String getIS_COM() {
                return IS_COM;
            }

            public void setIS_COM(String IS_COM) {
                this.IS_COM = IS_COM;
            }

            public String getORDER_NO() {
                return ORDER_NO;
            }

            public void setORDER_NO(String ORDER_NO) {
                this.ORDER_NO = ORDER_NO;
            }

            public String getTIME() {
                return TIME;
            }

            public void setTIME(String TIME) {
                this.TIME = TIME;
            }

            public String getNAME() {
                return NAME;
            }

            public void setNAME(String NAME) {
                this.NAME = NAME;
            }

            public String getPAY_TIME() {
                return PAY_TIME;
            }

            public void setPAY_TIME(String PAY_TIME) {
                this.PAY_TIME = PAY_TIME;
            }

            public String getZB_PIC() {
                return ZB_PIC;
            }

            public void setZB_PIC(String ZB_PIC) {
                this.ZB_PIC = ZB_PIC;
            }

            public String getEND_TINE() {
                return END_TINE;
            }

            public void setEND_TINE(String END_TINE) {
                this.END_TINE = END_TINE;
            }

            public String getSTATUS() {
                return STATUS;
            }

            public void setSTATUS(String STATUS) {
                this.STATUS = STATUS;
            }

            public String getADDRESS() {
                return ADDRESS;
            }

            public void setADDRESS(String ADDRESS) {
                this.ADDRESS = ADDRESS;
            }

            public String getORDER_TIME() {
                return ORDER_TIME;
            }

            public void setORDER_TIME(String ORDER_TIME) {
                this.ORDER_TIME = ORDER_TIME;
            }

            public String getPAY_METHOD() {
                return PAY_METHOD;
            }

            public void setPAY_METHOD(String PAY_METHOD) {
                this.PAY_METHOD = PAY_METHOD;
            }
        }
    }
}
