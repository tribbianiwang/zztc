package com.yascn.smartpark.bean;

import java.util.List;

/**
 * Created by YASCN on 2017/9/7.
 */

public class GetBanner {

    /**
     * msg : 正确返回数据
     * object : [{"PIC":"http://192.168.1.25:8080/wrzs/uploadFiles/uploadImgs/2.jpg","URL":"https://www.baidu.com"},{"PIC":"http://192.168.1.25:8080/wrzs/uploadFiles/uploadImgs/1.jpg","URL":"https://www.baidu.com"}]
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
         * PIC : http://192.168.1.25:8080/wrzs/uploadFiles/uploadImgs/2.jpg
         * URL : https://www.baidu.com
         */

        private String PIC;
        private String URL;

        public String getPIC() {
            return PIC;
        }

        public void setPIC(String PIC) {
            this.PIC = PIC;
        }

        public String getURL() {
            return URL;
        }

        public void setURL(String URL) {
            this.URL = URL;
        }

        @Override
        public String toString() {
            return "ObjectBean{" +
                    "PIC='" + PIC + '\'' +
                    ", URL='" + URL + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GetBanner{" +
                "msg='" + msg + '\'' +
                ", statusCode=" + statusCode +
                ", object=" + object +
                '}';
    }
}
