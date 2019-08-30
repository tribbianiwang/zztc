package com.yascn.smartpark.bean;

/**
 * Created by YASCN on 2017/9/7.
 */

public class BandNumber {
    /**
     * msg : 正确返回数据
     * object : {"NUMBER":"豫ZABCDE","flag":"1","LICENSE_PLATE_ID":"a1e89e1ebbe641d69bb4b85e59cb77ae","IS_DEFAULT":"0","AUTOPAY":"0","IS_AUTH":"0"}
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
         * NUMBER : 豫ZABCDE
         * flag : 1
         * LICENSE_PLATE_ID : a1e89e1ebbe641d69bb4b85e59cb77ae
         * IS_DEFAULT : 0
         * AUTOPAY : 0
         * IS_AUTH : 0
         */

        private String NUMBER;
        private String flag;
        private String LICENSE_PLATE_ID;
        private String IS_DEFAULT;
        private String AUTOPAY;
        private String IS_AUTH;

        public String getNUMBER() {
            return NUMBER;
        }

        public void setNUMBER(String NUMBER) {
            this.NUMBER = NUMBER;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getLICENSE_PLATE_ID() {
            return LICENSE_PLATE_ID;
        }

        public void setLICENSE_PLATE_ID(String LICENSE_PLATE_ID) {
            this.LICENSE_PLATE_ID = LICENSE_PLATE_ID;
        }

        public String getIS_DEFAULT() {
            return IS_DEFAULT;
        }

        public void setIS_DEFAULT(String IS_DEFAULT) {
            this.IS_DEFAULT = IS_DEFAULT;
        }

        public String getAUTOPAY() {
            return AUTOPAY;
        }

        public void setAUTOPAY(String AUTOPAY) {
            this.AUTOPAY = AUTOPAY;
        }

        public String getIS_AUTH() {
            return IS_AUTH;
        }

        public void setIS_AUTH(String IS_AUTH) {
            this.IS_AUTH = IS_AUTH;
        }
    }


//
//    /**
//     * msg : 正确返回数据
//     * object : {"flag":"0"}
//     * statusCode : 1
//     */
//
//    private String msg;
//    private ObjectBean object;
//    private int statusCode;
//
//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }
//
//    public ObjectBean getObject() {
//        return object;
//    }
//
//    public void setObject(ObjectBean object) {
//        this.object = object;
//    }
//
//    public int getStatusCode() {
//        return statusCode;
//    }
//
//    public void setStatusCode(int statusCode) {
//        this.statusCode = statusCode;
//    }
//
//    public static class ObjectBean {
//        /**
//         * flag : 0
//         */
//
//        private String flag;
//
//        public String getFlag() {
//            return flag;
//        }
//
//        public void setFlag(String flag) {
//            this.flag = flag;
//        }
//
//        @Override
//        public String toString() {
//            return "ObjectBean{" +
//                    "flag='" + flag + '\'' +
//                    '}';
//        }
//    }
//
//    @Override
//    public String toString() {
//        return "BandNumber{" +
//                "msg='" + msg + '\'' +
//                ", object=" + object +
//                ", statusCode=" + statusCode +
//                '}';
//    }
}
