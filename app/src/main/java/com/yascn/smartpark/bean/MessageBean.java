package com.yascn.smartpark.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by YASCN on 2018/7/6.
 */

public class MessageBean implements Serializable{

    /**
     * msg : 正确返回数据
     * object : [{"date":"2018-07-06 11:16:02","flag":"0","pic":"http://192.168.1.25:8080/wrzs/uploadFiles/uploadImgs/20180705/0a0edf245ff5423a930fedc4559630e3.jpg","msg_id":"1","title":"456","content":"456"},{"date":"2018-07-06 11:16:01","flag":"0","pic":"http://192.168.1.25:8080/wrzs/uploadFiles/uploadImgs/20180705/095b1f235c9f4c7285c98188bad368aa.jpg","msg_id":"2","title":"789","content":"789"},{"date":"2018-07-06 11:16:00","flag":"0","pic":"http://192.168.1.25:8080/wrzs/uploadFiles/uploadImgs/20180705/1c8b5a77d0ec402e8f792997cbdfdb0d.jpg","msg_id":"3","title":"小巷子","content":"藓苔班驳的垫脚石\r\n\r\n小巷子里呆过了多少个年头\r\n\r\n它沉默不语\r\n\r\n白发老人也众口不一\r\n\r\n总有说不完的世事沧桑\r\n\r\n没有人知道石板路\r\n\r\n承载了多少无言的蹉跎岁月\r\n\r\n但见每一个朝朝暮暮\r\n\r\n迎来送往，行人络绎不绝\r\n\r\n·\r\n\r\n时光匆匆，蓦然回首\r\n\r\n仿佛又回到小巷平淡的旧日子\r\n\r\n晨光斜照，庭院深深\r\n\r\n鸡鸣而起，又见炊烟袅袅\r\n\r\n几缕骑墙青藤空中摇曳\r\n\r\n古旧的小巷深处\r\n\r\n满目青砖黛瓦，井井有条\r\n\r\n依窗户和阳台，晾衣架下方\r\n\r\n摆放郁金香，海棠花，君子兰\r\n\r\n随风轻摆，别具浓郁的韵味\r\n\r\n留下一抹，抹不去的记忆痕迹\r\n\r\n·\r\n\r\n岁月婆娑，小巷子\r\n\r\n是否，还能够记得起\r\n\r\n当年，有一个楞头楞脑的小子\r\n\r\n满脑子莫名的奇思幻想\r\n\r\n叩开心中那一叶扇窗\r\n\r\n渴望插上一对翱翔蓝天的翅膀\r\n\r\n乘风飞向远方\r\n\r\n祈求每一个今天\r\n\r\n挣脱生命沉闷的蕃笼\r\n\r\n不给来日留下一缕新的遗憾\r\n\r\n·\r\n\r\n班驳的小巷子\r\n\r\n夕照下，卸下时季的伪装\r\n\r\n眼底一派暮景桑榆\r\n\r\n笼罩一抹鹅黄，浅浅的，淡淡的\r\n\r\n渐渐地变深，变老\r\n\r\n夜色里，满满的安详愉悦\r\n\r\n沉默中，那一份承诺和执着\r\n\r\n伴我一生，走过海角天涯"}]
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

    public static class ObjectBean implements Serializable {
        /**
         * date : 2018-07-06 11:16:02
         * flag : 0
         * pic : http://192.168.1.25:8080/wrzs/uploadFiles/uploadImgs/20180705/0a0edf245ff5423a930fedc4559630e3.jpg
         * msg_id : 1
         * title : 456
         * content : 456
         */

        private String date;
        private String flag;
        private String pic;
        private String msg_id;
        private String title;
        private String content;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getMsg_id() {
            return msg_id;
        }

        public void setMsg_id(String msg_id) {
            this.msg_id = msg_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
