package com.yascn.smartpark.bean;

import java.util.List;

/**
 * Created by YASCN on 2017/9/7.
 */

public class SetDefaultNo {
    /**
     * msg : 正确返回数据
     * object : {"flag":"1","license_list":[{"NUMBER":"豫TGUUUU","LICENSE_PLATE_ID":"1ba2f3d10f0941879a5a818e39f8ca5e","IS_DEFAULT":"0","AUTOPAY":"0","IS_AUTH":"0"},{"NUMBER":"豫ZABCDE","LICENSE_PLATE_ID":"700aa39dac6e4eb69fbca0601a94c8b1","IS_DEFAULT":"1","AUTOPAY":"0","IS_AUTH":"0"}]}
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
         * flag : 1
         * license_list : [{"NUMBER":"豫TGUUUU","LICENSE_PLATE_ID":"1ba2f3d10f0941879a5a818e39f8ca5e","IS_DEFAULT":"0","AUTOPAY":"0","IS_AUTH":"0"},{"NUMBER":"豫ZABCDE","LICENSE_PLATE_ID":"700aa39dac6e4eb69fbca0601a94c8b1","IS_DEFAULT":"1","AUTOPAY":"0","IS_AUTH":"0"}]
         */

        private String flag;
        private List<LicenseListBean> license_list;

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public List<LicenseListBean> getLicense_list() {
            return license_list;
        }

        public void setLicense_list(List<LicenseListBean> license_list) {
            this.license_list = license_list;
        }

        public static class LicenseListBean {
            /**
             * NUMBER : 豫TGUUUU
             * LICENSE_PLATE_ID : 1ba2f3d10f0941879a5a818e39f8ca5e
             * IS_DEFAULT : 0
             * AUTOPAY : 0
             * IS_AUTH : 0
             */

            private String NUMBER;
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
    }


//    /**
//     * msg : 正确返回数据
//     * object : {"flag":"1","license_list":[{"NUMBER":"豫A11111","LICENSE_PLATE_ID":"2a1bd0b21d9b4244ae199f704638c903","IS_DEFAULT":"1","AUTOPAY":"0"}]}
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
//         * flag : 1
//         * license_list : [{"NUMBER":"豫A11111","LICENSE_PLATE_ID":"2a1bd0b21d9b4244ae199f704638c903","IS_DEFAULT":"1","AUTOPAY":"0"}]
//         */
//
//        private String flag;
//        private List<LicenseListBean> license_list;
//
//        public String getFlag() {
//            return flag;
//        }
//
//        public void setFlag(String flag) {
//            this.flag = flag;
//        }
//
//        public List<LicenseListBean> getLicense_list() {
//            return license_list;
//        }
//
//        public void setLicense_list(List<LicenseListBean> license_list) {
//            this.license_list = license_list;
//        }
//
//        public static class LicenseListBean {
//            /**
//             * NUMBER : 豫A11111
//             * LICENSE_PLATE_ID : 2a1bd0b21d9b4244ae199f704638c903
//             * IS_DEFAULT : 1
//             * AUTOPAY : 0
//             */
//
//            private String NUMBER;
//            private String LICENSE_PLATE_ID;
//            private String IS_DEFAULT;
//            private String AUTOPAY;
//
//            public String getNUMBER() {
//                return NUMBER;
//            }
//
//            public void setNUMBER(String NUMBER) {
//                this.NUMBER = NUMBER;
//            }
//
//            public String getLICENSE_PLATE_ID() {
//                return LICENSE_PLATE_ID;
//            }
//
//            public void setLICENSE_PLATE_ID(String LICENSE_PLATE_ID) {
//                this.LICENSE_PLATE_ID = LICENSE_PLATE_ID;
//            }
//
//            public String getIS_DEFAULT() {
//                return IS_DEFAULT;
//            }
//
//            public void setIS_DEFAULT(String IS_DEFAULT) {
//                this.IS_DEFAULT = IS_DEFAULT;
//            }
//
//            public String getAUTOPAY() {
//                return AUTOPAY;
//            }
//
//            public void setAUTOPAY(String AUTOPAY) {
//                this.AUTOPAY = AUTOPAY;
//            }
//
//            @Override
//            public String toString() {
//                return "LicenseListBean{" +
//                        "NUMBER='" + NUMBER + '\'' +
//                        ", LICENSE_PLATE_ID='" + LICENSE_PLATE_ID + '\'' +
//                        ", IS_DEFAULT='" + IS_DEFAULT + '\'' +
//                        ", AUTOPAY='" + AUTOPAY + '\'' +
//                        '}';
//            }
//        }
//
//        @Override
//        public String toString() {
//            return "ObjectBean{" +
//                    "flag='" + flag + '\'' +
//                    ", license_list=" + license_list +
//                    '}';
//        }
//    }
//
//    @Override
//    public String toString() {
//        return "SetDefaultNo{" +
//                "msg='" + msg + '\'' +
//                ", object=" + object +
//                ", statusCode=" + statusCode +
//                '}';
//    }
}
