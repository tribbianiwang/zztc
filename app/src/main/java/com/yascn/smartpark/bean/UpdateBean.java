package com.yascn.smartpark.bean;

/**
 * Created by YASCN on 2018/1/6.
 */

public class UpdateBean {


    /**
     * msg : 正确返回数据
     * object : {"vs_url":"http://app.yascn.com/steward/uploadFiles/file/xxx.apk","flag":"1","vs_no":"2.0.1","vs_content":"新版本更新"}
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
         * vs_url : http://app.yascn.com/steward/uploadFiles/file/xxx.apk
         * flag : 1
         * vs_no : 2.0.1
         * vs_content : 新版本更新
         */

        private String vs_url;
        private String flag;
        private String vs_no;
        private String vs_content;

        public String getVs_url() {
            return vs_url;
        }

        public void setVs_url(String vs_url) {
            this.vs_url = vs_url;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getVs_no() {
            return vs_no;
        }

        public void setVs_no(String vs_no) {
            this.vs_no = vs_no;
        }

        public String getVs_content() {
            return vs_content;
        }

        public void setVs_content(String vs_content) {
            this.vs_content = vs_content;
        }
    }
}
