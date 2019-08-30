package com.yascn.smartpark.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by YASCN on 2017/9/9.
 */

public class ParkComment {


    /**
     * msg : 正确返回数据
     * object : [{"DATE":"2017-08-21 10:01:47","imgs":[],"U_IMG":"http://192.168.1.25:8080/wrzs/uploadFiles/uploadImgs/20170908/73edb226259e4ef4a2edc0d0f2ef8ec5.jpg","STAR":"4","USERNAME":"15690846236","CONTENT":""},{"DATE":"2017-04-28 14:27:33","imgs":[{"IMG":"http://192.168.1.25:8080/YYParking/uploadFiles/uploadImgs/20170428/a1a43540722149febbed854de211faf5.jpg"}],"U_IMG":"http://192.168.1.25:8080/wrzs/uploadFiles/uploadImgs/20170906/ea91e501279040fa9341c65d4a776323.jpg","STAR":"5","USERNAME":"18539245237","CONTENT":"停车场真棒!"},{"DATE":"2017-03-12 17:19:38","imgs":[{"IMG":"http://192.168.1.25:8080/YYParking/uploadFiles/uploadImgs/20170312/cf664b4ec20244a6a29f1e9e1f64b92d.jpg"},{"IMG":"http://192.168.1.25:8080/YYParking/uploadFiles/uploadImgs/20170312/b543e83d53864126b6f050700926b960.jpg"},{"IMG":"http://192.168.1.25:8080/YYParking/uploadFiles/uploadImgs/20170312/d3c0eab83b604446a8e02943ce560436.jpg"},{"IMG":"http://192.168.1.25:8080/YYParking/uploadFiles/uploadImgs/20170312/fecdf71729e84af0801578670202434e.png"},{"IMG":"http://192.168.1.25:8080/YYParking/uploadFiles/uploadImgs/20170312/052a70235d4b454988548c2939c62a41.jpg"},{"IMG":"http://192.168.1.25:8080/YYParking/uploadFiles/uploadImgs/20170312/f19f07b8a5e344ccb1adb2ecf70bf8ed.png"},{"IMG":"http://192.168.1.25:8080/YYParking/uploadFiles/uploadImgs/20170312/a3134fe546ce434fbc261dbb038a401e.jpg"},{"IMG":"http://192.168.1.25:8080/YYParking/uploadFiles/uploadImgs/20170312/7135b88747d748acb83fa1be1c7331d4.jpg"}],"U_IMG":"http://192.168.1.25:8080/YYParking/uploadFiles/uploadImgs/20170428/e883245504c14bb89540f93c5f7ad420.jpg","STAR":"5","USERNAME":"15238322537","CONTENT":""},{"DATE":"2017-02-22 14:11:16","imgs":[],"U_IMG":"http://192.168.1.25:8080/wrzs/uploadFiles/uploadImgs/20170906/ea91e501279040fa9341c65d4a776323.jpg","STAR":"0","USERNAME":"18539245237","CONTENT":"停车场真棒!"},{"DATE":"2017-02-21 17:31:11","imgs":[{"IMG":"http://192.168.1.25:8080/YYParking/uploadFiles/uploadImgs/20170221/c9e2919d46d74f2ea41a93a78983a0f5.jpg"}],"U_IMG":"","STAR":"4","USERNAME":"130F42183270338EBDF72698E8BFE616","CONTENT":"你嗯破功"},{"DATE":"2017-02-06 15:36:49","imgs":[{"IMG":"http://192.168.1.25:8080/YYParking/uploadFiles/uploadImgs/20170206/0b6e73606d2d455db825b6a89052846b.jpg"}],"U_IMG":"http://192.168.1.25:8080/wrzs/uploadFiles/uploadImgs/20170906/ea91e501279040fa9341c65d4a776323.jpg","STAR":"5","USERNAME":"18539245237","CONTENT":"劝君上当一回"},{"DATE":"2017-02-06 09:31:38","imgs":[{"IMG":"http://192.168.1.25:8080/YYParking/uploadFiles/uploadImgs/20170206/b141bd3e3fc34d5084b7291030e48416.jpg"},{"IMG":"http://192.168.1.25:8080/YYParking/uploadFiles/uploadImgs/20170206/9852b578450640dcbf525d897579f643.jpg"}],"U_IMG":"http://192.168.1.25:8080/wrzs/uploadFiles/uploadImgs/20170906/ea91e501279040fa9341c65d4a776323.jpg","STAR":"5","USERNAME":"18539245237","CONTENT":"停车场真棒!"},{"DATE":"2017-01-23 15:23:47","imgs":[{"IMG":"http://192.168.1.25:8080/YYParking/uploadFiles/uploadImgs/20170123/86e5cad8f50844fcb0b21cc1a6786c52.jpg"},{"IMG":"http://192.168.1.25:8080/YYParking/uploadFiles/uploadImgs/20170123/2aab9c79fc5b45cdbde1ffdf5198cf2e.jpg"}],"U_IMG":"http://192.168.1.25:8080/wrzs/uploadFiles/uploadImgs/20170906/ea91e501279040fa9341c65d4a776323.jpg","STAR":"5","USERNAME":"18539245237","CONTENT":"停车场真棒!"},{"DATE":"2017-01-22 11:38:08","imgs":[{"IMG":"http://192.168.1.25:8080/YYParking/uploadFiles/uploadImgs/20170122/e59449d42d614541995b82e4bdee882e.jpg"},{"IMG":"http://192.168.1.25:8080/YYParking/uploadFiles/uploadImgs/20170122/77ecc89a48aa41bd9d5cb4b655a11c02.jpg"},{"IMG":"http://192.168.1.25:8080/YYParking/uploadFiles/uploadImgs/20170122/6324b3ef1ce94a1c9e065293c5b06ef6.jpg"},{"IMG":"http://192.168.1.25:8080/YYParking/uploadFiles/uploadImgs/20170122/054146414a0143abb87ac3c5eca0c0d1.jpg"}],"U_IMG":"http://192.168.1.25:8080/wrzs/uploadFiles/uploadImgs/20170906/ea91e501279040fa9341c65d4a776323.jpg","STAR":"5","USERNAME":"18539245237","CONTENT":"停车场真棒!"},{"DATE":"2017-01-22 11:30:59","imgs":[{"IMG":"http://192.168.1.25:8080/YYParking/uploadFiles/uploadImgs/20170122/b38e511502bb4786b3f8ae7c593da9d3.jpg"},{"IMG":"http://192.168.1.25:8080/YYParking/uploadFiles/uploadImgs/20170122/7223fcb36e634dbf98b257fb9d0821e4.jpg"},{"IMG":"http://192.168.1.25:8080/YYParking/uploadFiles/uploadImgs/20170122/d1e9865491e94677bec3f9b2726f38ac.jpg"}],"U_IMG":"http://192.168.1.25:8080/wrzs/uploadFiles/uploadImgs/20170906/ea91e501279040fa9341c65d4a776323.jpg","STAR":"5","USERNAME":"18539245237","CONTENT":"停车场真棒!"}]
     * statusCode : 1
     */

    private String msg;
    private int statusCode;
    /**
     * DATE : 2017-08-21 10:01:47
     * imgs : []
     * U_IMG : http://192.168.1.25:8080/wrzs/uploadFiles/uploadImgs/20170908/73edb226259e4ef4a2edc0d0f2ef8ec5.jpg
     * STAR : 4
     * USERNAME : 15690846236
     * CONTENT :
     */

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
        private String DATE;
        private String U_IMG;
        private String STAR;
        private String USERNAME;
        private String CONTENT;
        public class Img implements Serializable{
            private String IMG;

            public String getIMG() {
                return IMG;
            }

            public void setIMG(String IMG) {
                this.IMG = IMG;
            }
        }

        private List<Img> imgs;

        public String getDATE() {
            return DATE;
        }

        public void setDATE(String DATE) {
            this.DATE = DATE;
        }

        public String getU_IMG() {
            return U_IMG;
        }

        public void setU_IMG(String U_IMG) {
            this.U_IMG = U_IMG;
        }

        public String getSTAR() {
            return STAR;
        }

        public void setSTAR(String STAR) {
            this.STAR = STAR;
        }

        public String getUSERNAME() {
            return USERNAME;
        }

        public void setUSERNAME(String USERNAME) {
            this.USERNAME = USERNAME;
        }

        public String getCONTENT() {
            return CONTENT;
        }

        public void setCONTENT(String CONTENT) {
            this.CONTENT = CONTENT;
        }

        public List<Img> getImgs() {
            return imgs;
        }

        public void setImgs(List<Img> imgs) {
            this.imgs = imgs;
        }



    }
}
