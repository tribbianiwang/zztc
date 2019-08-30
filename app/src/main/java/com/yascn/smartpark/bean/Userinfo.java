package com.yascn.smartpark.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by YASCN on 2017/9/7.
 */

public class Userinfo implements Serializable {
    /**
     * msg : 正确返回数据
     * object : {"birthday":null,"img":"http://app.yascn.com/wrzs/uploadFiles/uploadImgs/20171106/19acf2dcadcd42259f95281c2a480dd6.jpg","gender":"w","money":"998.97","phone":"15238322537","isHavePwd":"0","name":"猫病","numberList":[{"NUMBER":"豫HJJKYY","LICENSE_PLATE_ID":"bb74681919ed4f3cbca0d3019a3b1323","IS_DEFAULT":"0","AUTOPAY":"0","IS_AUTH":"0"},{"NUMBER":"豫THJJJH","LICENSE_PLATE_ID":"900a9286ca86482f98ba2b5a2d9f36fa","IS_DEFAULT":"1","AUTOPAY":"0","IS_AUTH":"0"}]}
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
         * birthday : null
         * img : http://app.yascn.com/wrzs/uploadFiles/uploadImgs/20171106/19acf2dcadcd42259f95281c2a480dd6.jpg
         * gender : w
         * money : 998.97
         * phone : 15238322537
         * isHavePwd : 0
         * name : 猫病
         * numberList : [{"NUMBER":"豫HJJKYY","LICENSE_PLATE_ID":"bb74681919ed4f3cbca0d3019a3b1323","IS_DEFAULT":"0","AUTOPAY":"0","IS_AUTH":"0"},{"NUMBER":"豫THJJJH","LICENSE_PLATE_ID":"900a9286ca86482f98ba2b5a2d9f36fa","IS_DEFAULT":"1","AUTOPAY":"0","IS_AUTH":"0"}]
         */
        private String msgNum;

        private Object birthday;
        private String img;
        private String gender;
        private String money;
        private String phone;
        private String isHavePwd;
        private String name;
        private List<NumberListBean> numberList;

        public String getMsgNum() {
            return msgNum;
        }

        public void setMsgNum(String msgNum) {
            this.msgNum = msgNum;
        }

        public Object getBirthday() {
            return birthday;
        }

        public void setBirthday(Object birthday) {
            this.birthday = birthday;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getIsHavePwd() {
            return isHavePwd;
        }

        public void setIsHavePwd(String isHavePwd) {
            this.isHavePwd = isHavePwd;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<NumberListBean> getNumberList() {
            return numberList;
        }

        public void setNumberList(List<NumberListBean> numberList) {
            this.numberList = numberList;
        }

        public static class NumberListBean implements Serializable {
            /**
             * NUMBER : 豫HJJKYY
             * LICENSE_PLATE_ID : bb74681919ed4f3cbca0d3019a3b1323
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
//     * object : {"birthday":null,"img":"http://192.168.1.25:8080/wrzs/uploadFiles/uploadImgs/20170911/f3b06de7ef304f41930adecddebf48b7.jpg","gender":"w","money":"973.40","phone":"15690846236","isHavePwd":"1","name":"哈哈哈","numberList":[{"NUMBER":"豫A11111","LICENSE_PLATE_ID":"2a1bd0b21d9b4244ae199f704638c903","IS_DEFAULT":"0","AUTOPAY":"0"}]}
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
//    public static class ObjectBean implements Serializable {
//        /**
//         * birthday : null
//         * img : http://192.168.1.25:8080/wrzs/uploadFiles/uploadImgs/20170911/f3b06de7ef304f41930adecddebf48b7.jpg
//         * gender : w
//         * money : 973.40
//         * phone : 15690846236
//         * isHavePwd : 1
//         * name : 哈哈哈
//         * numberList : [{"NUMBER":"豫A11111","LICENSE_PLATE_ID":"2a1bd0b21d9b4244ae199f704638c903","IS_DEFAULT":"0","AUTOPAY":"0"}]
//         */
//
//        private Object birthday;
//        private String img;
//        private String gender;
//        private String money;
//        private String phone;
//        private String isHavePwd;
//        private String name;
//        private List<NumberListBean> numberList;
//
//        public Object getBirthday() {
//            return birthday;
//        }
//
//        public void setBirthday(Object birthday) {
//            this.birthday = birthday;
//        }
//
//        public String getImg() {
//            return img;
//        }
//
//        public void setImg(String img) {
//            this.img = img;
//        }
//
//        public String getGender() {
//            return gender;
//        }
//
//        public void setGender(String gender) {
//            this.gender = gender;
//        }
//
//        public String getMoney() {
//            return money;
//        }
//
//        public void setMoney(String money) {
//            this.money = money;
//        }
//
//        public String getPhone() {
//            return phone;
//        }
//
//        public void setPhone(String phone) {
//            this.phone = phone;
//        }
//
//        public String getIsHavePwd() {
//            return isHavePwd;
//        }
//
//        public void setIsHavePwd(String isHavePwd) {
//            this.isHavePwd = isHavePwd;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public List<NumberListBean> getNumberList() {
//            return numberList;
//        }
//
//        public void setNumberList(List<NumberListBean> numberList) {
//            this.numberList = numberList;
//        }
//
//        public static class NumberListBean implements Serializable{
//            /**
//             * NUMBER : 豫A11111
//             * LICENSE_PLATE_ID : 2a1bd0b21d9b4244ae199f704638c903
//             * IS_DEFAULT : 0
//             * AUTOPAY : 0
//             */
//
//            private String NUMBER;
//            private String LICENSE_PLATE_ID;
//            private String IS_DEFAULT;
//            private String AUTOPAY;
//            private String IS_AUTH;
//
//            public String getIS_AUTH() {
//                return IS_AUTH;
//            }
//
//            public void setIS_AUTH(String IS_AUTH) {
//                this.IS_AUTH = IS_AUTH;
//            }
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
//                return "NumberListBean{" +
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
//                    "birthday=" + birthday +
//                    ", img='" + img + '\'' +
//                    ", gender='" + gender + '\'' +
//                    ", money='" + money + '\'' +
//                    ", phone='" + phone + '\'' +
//                    ", isHavePwd='" + isHavePwd + '\'' +
//                    ", name='" + name + '\'' +
//                    ", numberList=" + numberList +
//                    '}';
//        }
//    }
//
//    @Override
//    public String toString() {
//        return "Userinfo{" +
//                "msg='" + msg + '\'' +
//                ", object=" + object +
//                ", statusCode=" + statusCode +
//                '}';
//    }


}
