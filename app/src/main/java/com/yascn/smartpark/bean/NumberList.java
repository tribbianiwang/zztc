package com.yascn.smartpark.bean;

import java.util.List;

/**
 * Created by YASCN on 2017/9/8.
 */

public class NumberList {
    /**
     * msg : 正确返回数据
     * object : [{"NUMBER":"豫EUUDFF","MCARD_LIST":[{"ETIME":"2018-12-09 00:00:00","MCARDRECORD_ID":"15b71e90a01647ab9ceeeac0cb41e45a","PARKING_ID":"2335e1f5d2384e889bc857582309a186","PARK_NAME":"郑东新区CBD1号停车场","FLAG":"1","SY_DAYS":27,"STIME":"2018-11-09 00:00:00"}],"LICENSE_PLATE_ID":"17f33358dd474e49804391f9389a2626","IS_DEFAULT":"0","AUTOPAY":"0","IS_AUTH":"0"},{"NUMBER":"豫UUUUUU","MCARD_LIST":[{"ETIME":"2019-11-09 00:00:00","MCARDRECORD_ID":"60245a83b8184d7c8a22a174cd155e14","PARKING_ID":"2335e1f5d2384e889bc857582309a186","PARK_NAME":"郑东新区CBD1号停车场","FLAG":"1","SY_DAYS":362,"STIME":"2018-11-09 00:00:00"},{"ETIME":"2019-02-07 00:00:00","MCARDRECORD_ID":"7411ec180f4143aca9e1b877d722d6c0","PARKING_ID":"2335e1f5d2384e889bc857582309a186","PARK_NAME":"郑东新区CBD1号停车场","FLAG":"1","SY_DAYS":87,"STIME":"2018-11-09 00:00:00"}],"LICENSE_PLATE_ID":"95acc5da9f6b46959489c2fe838fd965","IS_DEFAULT":"1","AUTOPAY":"0","IS_AUTH":"0"},{"NUMBER":"豫YUPPPJ","MCARD_LIST":[],"LICENSE_PLATE_ID":"4ab43645faa9467b84c3702c5471c899","IS_DEFAULT":"0","AUTOPAY":"0","IS_AUTH":"0"}]
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
         * NUMBER : 豫EUUDFF
         * MCARD_LIST : [{"ETIME":"2018-12-09 00:00:00","MCARDRECORD_ID":"15b71e90a01647ab9ceeeac0cb41e45a","PARKING_ID":"2335e1f5d2384e889bc857582309a186","PARK_NAME":"郑东新区CBD1号停车场","FLAG":"1","SY_DAYS":27,"STIME":"2018-11-09 00:00:00"}]
         * LICENSE_PLATE_ID : 17f33358dd474e49804391f9389a2626
         * IS_DEFAULT : 0
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
             * ETIME : 2018-12-09 00:00:00
             * MCARDRECORD_ID : 15b71e90a01647ab9ceeeac0cb41e45a
             * PARKING_ID : 2335e1f5d2384e889bc857582309a186
             * PARK_NAME : 郑东新区CBD1号停车场
             * FLAG : 1
             * SY_DAYS : 27
             * STIME : 2018-11-09 00:00:00
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


//    /**
//     * msg : 正确返回数据
//     * object : [{"NUMBER":"豫HJJKYY","LICENSE_PLATE_ID":"bb74681919ed4f3cbca0d3019a3b1323","IS_DEFAULT":"0","AUTOPAY":"0","IS_AUTH":"0"},{"NUMBER":"豫THJJJH","LICENSE_PLATE_ID":"900a9286ca86482f98ba2b5a2d9f36fa","IS_DEFAULT":"1","AUTOPAY":"0","IS_AUTH":"0"}]
//     * statusCode : 1
//     */
//
//    private String msg;
//    private int statusCode;
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
//        /**
//         * NUMBER : 豫HJJKYY
//         * LICENSE_PLATE_ID : bb74681919ed4f3cbca0d3019a3b1323
//         * IS_DEFAULT : 0
//         * AUTOPAY : 0
//         * IS_AUTH : 0
//         */
//
//        private String NUMBER;
//        private String LICENSE_PLATE_ID;
//        private String IS_DEFAULT;
//        private String AUTOPAY;
//        private String IS_AUTH;
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
//
//        public String getIS_AUTH() {
//            return IS_AUTH;
//        }
//
//        public void setIS_AUTH(String IS_AUTH) {
//            this.IS_AUTH = IS_AUTH;
//        }
//    }
//
//
////    /**
////     * msg : 正确返回数据
////     * object : [{"NUMBER":"豫A11111","LICENSE_PLATE_ID":"1d148e46e92843509684fdb360bf9d8a","IS_DEFAULT":"1","AUTOPAY":"0"},{"NUMBER":"豫A33333","LICENSE_PLATE_ID":"f61e754fea8a42d984372a15e424efda","IS_DEFAULT":"0","AUTOPAY":"0"}]
////     * statusCode : 1
////     */
////
////    private String msg;
////    private int statusCode;
////    private List<ObjectBean> object;
////
////    public String getMsg() {
////        return msg;
////    }
////
////    public void setMsg(String msg) {
////        this.msg = msg;
////    }
////
////    public int getStatusCode() {
////        return statusCode;
////    }
////
////    public void setStatusCode(int statusCode) {
////        this.statusCode = statusCode;
////    }
////
////    public List<ObjectBean> getObject() {
////        return object;
////    }
////
////    public void setObject(List<ObjectBean> object) {
////        this.object = object;
////    }
////
////    public static class ObjectBean {
////        /**
////         * NUMBER : 豫A11111
////         * LICENSE_PLATE_ID : 1d148e46e92843509684fdb360bf9d8a
////         * IS_DEFAULT : 1
////         * AUTOPAY : 0
////         */
////
////        private String NUMBER;
////        private String LICENSE_PLATE_ID;
////        private String IS_DEFAULT;
////        private String AUTOPAY;
////
////        public String getNUMBER() {
////            return NUMBER;
////        }
////
////        public void setNUMBER(String NUMBER) {
////            this.NUMBER = NUMBER;
////        }
////
////        public String getLICENSE_PLATE_ID() {
////            return LICENSE_PLATE_ID;
////        }
////
////        public void setLICENSE_PLATE_ID(String LICENSE_PLATE_ID) {
////            this.LICENSE_PLATE_ID = LICENSE_PLATE_ID;
////        }
////
////        public String getIS_DEFAULT() {
////            return IS_DEFAULT;
////        }
////
////        public void setIS_DEFAULT(String IS_DEFAULT) {
////            this.IS_DEFAULT = IS_DEFAULT;
////        }
////
////        public String getAUTOPAY() {
////            return AUTOPAY;
////        }
////
////        public void setAUTOPAY(String AUTOPAY) {
////            this.AUTOPAY = AUTOPAY;
////        }
////
////        @Override
////        public String toString() {
////            return "ObjectBean{" +
////                    "NUMBER='" + NUMBER + '\'' +
////                    ", LICENSE_PLATE_ID='" + LICENSE_PLATE_ID + '\'' +
////                    ", IS_DEFAULT='" + IS_DEFAULT + '\'' +
////                    ", AUTOPAY='" + AUTOPAY + '\'' +
////                    '}';
////        }
////    }
////
////    @Override
////    public String toString() {
////        return "NumberList{" +
////                "msg='" + msg + '\'' +
////                ", statusCode=" + statusCode +
////                ", object=" + object +
////                '}';
////    }
}
