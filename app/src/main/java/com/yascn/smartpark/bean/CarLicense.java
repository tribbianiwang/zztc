package com.yascn.smartpark.bean;

import java.util.List;

/**
 * Created by YASCN on 2017/9/11.
 */

public class CarLicense {
    /**
     * msg : 正确返回数据
     * object : [{"NUMBER":"京A11111","MCARD_LIST":[{"ETIME":"2018-12-14 00:00:00","MCARDRECORD_ID":"610e976500894f18ac0bb90726a93c50","PARKING_ID":"2335e1f5d2384e889bc857582309a186","PARK_NAME":"郑东新区CBD1号停车场","FLAG":"1","SY_DAYS":30,"STIME":"2018-11-14 00:00:00"},{"ETIME":"2018-12-14 00:00:00","MCARDRECORD_ID":"6fd3d5881e35491699726adbc0f9eee5","PARKING_ID":"2335e1f5d2384e889bc857582309a186","PARK_NAME":"郑东新区CBD1号停车场","FLAG":"1","SY_DAYS":30,"STIME":"2018-11-14 00:00:00"}],"LICENSE_PLATE_ID":"157bd0e18d6a445d957b49365676c2bf","IS_DEFAULT":"1","AUTOPAY":"0","IS_AUTH":"0"},{"NUMBER":"津A11111","MCARD_LIST":[{"ETIME":"2018-12-14 00:00:00","MCARDRECORD_ID":"052a4c06368a47169614cd71c1f5c723","PARKING_ID":"3","PARK_NAME":"郑东新区CBD2号停车场","FLAG":"1","SY_DAYS":30,"STIME":"2018-11-14 00:00:00"}],"LICENSE_PLATE_ID":"96cd42d2a8454bf4810beec54fd464a1","IS_DEFAULT":"0","AUTOPAY":"0","IS_AUTH":"0"}]
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
         * NUMBER : 京A11111
         * MCARD_LIST : [{"ETIME":"2018-12-14 00:00:00","MCARDRECORD_ID":"610e976500894f18ac0bb90726a93c50","PARKING_ID":"2335e1f5d2384e889bc857582309a186","PARK_NAME":"郑东新区CBD1号停车场","FLAG":"1","SY_DAYS":30,"STIME":"2018-11-14 00:00:00"},{"ETIME":"2018-12-14 00:00:00","MCARDRECORD_ID":"6fd3d5881e35491699726adbc0f9eee5","PARKING_ID":"2335e1f5d2384e889bc857582309a186","PARK_NAME":"郑东新区CBD1号停车场","FLAG":"1","SY_DAYS":30,"STIME":"2018-11-14 00:00:00"}]
         * LICENSE_PLATE_ID : 157bd0e18d6a445d957b49365676c2bf
         * IS_DEFAULT : 1
         * AUTOPAY : 0
         * IS_AUTH : 0
         */

        private String NUMBER;
        private String LICENSE_PLATE_ID;
        private String IS_DEFAULT;
        private String AUTOPAY;
        private String IS_AUTH;
        private List<MCARDLISTBean> MCARD_LIST;

        public String getNUMBER() {
            return NUMBER;
        }

        public void setNUMBER(String NUMBER) {
            this.NUMBER = NUMBER;
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

        public List<MCARDLISTBean> getMCARD_LIST() {
            return MCARD_LIST;
        }

        public void setMCARD_LIST(List<MCARDLISTBean> MCARD_LIST) {
            this.MCARD_LIST = MCARD_LIST;
        }

        public static class MCARDLISTBean {
            /**
             * ETIME : 2018-12-14 00:00:00
             * MCARDRECORD_ID : 610e976500894f18ac0bb90726a93c50
             * PARKING_ID : 2335e1f5d2384e889bc857582309a186
             * PARK_NAME : 郑东新区CBD1号停车场
             * FLAG : 1
             * SY_DAYS : 30
             * STIME : 2018-11-14 00:00:00
             */

            private String ETIME;
            private String MCARDRECORD_ID;
            private String PARKING_ID;
            private String PARK_NAME;
            private String FLAG;
            private int SY_DAYS;
            private String STIME;

            public String getETIME() {
                return ETIME;
            }

            public void setETIME(String ETIME) {
                this.ETIME = ETIME;
            }

            public String getMCARDRECORD_ID() {
                return MCARDRECORD_ID;
            }

            public void setMCARDRECORD_ID(String MCARDRECORD_ID) {
                this.MCARDRECORD_ID = MCARDRECORD_ID;
            }

            public String getPARKING_ID() {
                return PARKING_ID;
            }

            public void setPARKING_ID(String PARKING_ID) {
                this.PARKING_ID = PARKING_ID;
            }

            public String getPARK_NAME() {
                return PARK_NAME;
            }

            public void setPARK_NAME(String PARK_NAME) {
                this.PARK_NAME = PARK_NAME;
            }

            public String getFLAG() {
                return FLAG;
            }

            public void setFLAG(String FLAG) {
                this.FLAG = FLAG;
            }

            public int getSY_DAYS() {
                return SY_DAYS;
            }

            public void setSY_DAYS(int SY_DAYS) {
                this.SY_DAYS = SY_DAYS;
            }

            public String getSTIME() {
                return STIME;
            }

            public void setSTIME(String STIME) {
                this.STIME = STIME;
            }
        }
    }


//
//
//
//    /**
//     * msg : 正确返回数据
//     * object : [{"NUMBER":"豫T44688","LICENSE_PLATE_ID":"4cd2a826080f4804a43db62d72233d4d","IS_DEFAULT":"0","AUTOPAY":"0"}]
//     * statusCode : 1
//     */
//
//    private String msg;
//    private int statusCode;
//    /**
//     * NUMBER : 豫T44688
//     * LICENSE_PLATE_ID : 4cd2a826080f4804a43db62d72233d4d
//     * IS_DEFAULT : 0
//     * AUTOPAY : 0
//     */
//
//    private List<ObjectBean> object;
//
//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
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
//    public List<ObjectBean> getObject() {
//        return object;
//    }
//
//    public void setObject(List<ObjectBean> object) {
//        this.object = object;
//    }
//
//    public static class ObjectBean {
//        private String NUMBER;
//        private String LICENSE_PLATE_ID;
//        private String IS_DEFAULT;
//        private String AUTOPAY;
//
//        public String getNUMBER() {
//            return NUMBER;
//        }
//
//        public void setNUMBER(String NUMBER) {
//            this.NUMBER = NUMBER;
//        }
//
//        public String getLICENSE_PLATE_ID() {
//            return LICENSE_PLATE_ID;
//        }
//
//        public void setLICENSE_PLATE_ID(String LICENSE_PLATE_ID) {
//            this.LICENSE_PLATE_ID = LICENSE_PLATE_ID;
//        }
//
//        public String getIS_DEFAULT() {
//            return IS_DEFAULT;
//        }
//
//        public void setIS_DEFAULT(String IS_DEFAULT) {
//            this.IS_DEFAULT = IS_DEFAULT;
//        }
//
//        public String getAUTOPAY() {
//            return AUTOPAY;
//        }
//
//        public void setAUTOPAY(String AUTOPAY) {
//            this.AUTOPAY = AUTOPAY;
//        }
//    }
}
