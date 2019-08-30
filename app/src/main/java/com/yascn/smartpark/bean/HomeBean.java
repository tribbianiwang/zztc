package com.yascn.smartpark.bean;

import java.util.List;

/**
 * Created by YASCN on 2018/6/1.
 */

public class HomeBean {
    /**
     * msg : 正确返回数据
     * object : {"msgNum":4,"banner_list":[{"PIC":"http://z1i6276594.imwork.net/wrzs/uploadFiles/uploadImgs/20181116/c3a4282b5a9541ec9c1434bffb7e4a66.jpg","URL":""},{"PIC":"http://z1i6276594.imwork.net/wrzs/uploadFiles/uploadImgs/20181116/d359d361254b453e8fd2208a751f7807.jpg","URL":""}],"orderNum":"0","car_limit":{"flag":"1","limitNo":"5/0","day_week":"星期五","limitCon":"限行尾号"}}
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
         * msgNum : 4
         * banner_list : [{"PIC":"http://z1i6276594.imwork.net/wrzs/uploadFiles/uploadImgs/20181116/c3a4282b5a9541ec9c1434bffb7e4a66.jpg","URL":""},{"PIC":"http://z1i6276594.imwork.net/wrzs/uploadFiles/uploadImgs/20181116/d359d361254b453e8fd2208a751f7807.jpg","URL":""}]
         * orderNum : 0
         * car_limit : {"flag":"1","limitNo":"5/0","day_week":"星期五","limitCon":"限行尾号"}
         */

        private int msgNum;
        private String orderNum;
        private CarLimitBean car_limit;
        private List<BannerListBean> banner_list;

        public int getMsgNum() {
            return msgNum;
        }

        public void setMsgNum(int msgNum) {
            this.msgNum = msgNum;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public CarLimitBean getCar_limit() {
            return car_limit;
        }

        public void setCar_limit(CarLimitBean car_limit) {
            this.car_limit = car_limit;
        }

        public List<BannerListBean> getBanner_list() {
            return banner_list;
        }

        public void setBanner_list(List<BannerListBean> banner_list) {
            this.banner_list = banner_list;
        }

        public static class CarLimitBean {
            /**
             * flag : 1
             * limitNo : 5/0
             * day_week : 星期五
             * limitCon : 限行尾号
             */

            private String flag;
            private String limitNo;
            private String day_week;
            private String limitCon;

            public String getFlag() {
                return flag;
            }

            public void setFlag(String flag) {
                this.flag = flag;
            }

            public String getLimitNo() {
                return limitNo;
            }

            public void setLimitNo(String limitNo) {
                this.limitNo = limitNo;
            }

            public String getDay_week() {
                return day_week;
            }

            public void setDay_week(String day_week) {
                this.day_week = day_week;
            }

            public String getLimitCon() {
                return limitCon;
            }

            public void setLimitCon(String limitCon) {
                this.limitCon = limitCon;
            }
        }

        public static class BannerListBean {
            /**
             * PIC : http://z1i6276594.imwork.net/wrzs/uploadFiles/uploadImgs/20181116/c3a4282b5a9541ec9c1434bffb7e4a66.jpg
             * URL :
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
        }
    }


//    /**
//     * msg : 正确返回数据
//     * object : {"banner_list":[{"PIC":"http://app.yascn.com/wrzs/uploadFiles/uploadImgs/sys/2.jpg","URL":""}],"orderNum":"1","car_limit":{"flag":"1","limitNo":"5/0"}}
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
//         * banner_list : [{"PIC":"http://app.yascn.com/wrzs/uploadFiles/uploadImgs/sys/2.jpg","URL":""}]
//         * orderNum : 1
//         * car_limit : {"flag":"1","limitNo":"5/0"}
//         */
//

//
//        private String orderNum;
//        private CarLimitBean car_limit;
//        private List<BannerListBean> banner_list;
//
//        public String getOrderNum() {
//            return orderNum;
//        }
//
//        public void setOrderNum(String orderNum) {
//            this.orderNum = orderNum;
//        }
//
//        public CarLimitBean getCar_limit() {
//            return car_limit;
//        }
//
//        public void setCar_limit(CarLimitBean car_limit) {
//            this.car_limit = car_limit;
//        }
//
//        public List<BannerListBean> getBanner_list() {
//            return banner_list;
//        }
//
//        public void setBanner_list(List<BannerListBean> banner_list) {
//            this.banner_list = banner_list;
//        }
//
//        public static class CarLimitBean {
//            /**
//             * flag : 1
//             * limitNo : 5/0
//             */
//
//            private String flag;
//            private String limitNo;
//
//            public String getFlag() {
//                return flag;
//            }
//
//            public void setFlag(String flag) {
//                this.flag = flag;
//            }
//
//            public String getLimitNo() {
//                return limitNo;
//            }
//
//            public void setLimitNo(String limitNo) {
//                this.limitNo = limitNo;
//            }
//        }
//
//        public static class BannerListBean {
//            /**
//             * PIC : http://app.yascn.com/wrzs/uploadFiles/uploadImgs/sys/2.jpg
//             * URL :
//             */
//
//            private String PIC;
//            private String URL;
//
//            public String getPIC() {
//                return PIC;
//            }
//
//            public void setPIC(String PIC) {
//                this.PIC = PIC;
//            }
//
//            public String getURL() {
//                return URL;
//            }
//
//            public void setURL(String URL) {
//                this.URL = URL;
//            }
//        }
//    }
}
