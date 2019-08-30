package com.yascn.smartpark.utils;

import android.os.Environment;

/**
 * Created by YASCN on 2017/8/28.
 */

public class AppContants {
    public static final int DEFAULT_OFFSCREEN_PAGES = 3;
    public static final String REFILLABLE = "1";
    public static final String REFILLUNABLE = "0";


    public static final String MONTHCARDTYPEBUY = "0";
    public static final String MONTHCARDTYPEREFILL = "1";


    public static final String PAYDIALOGMONEY = "paydialogmoney";
    public static final String LAYOUTID = "layoutid";
    public static final int ORDERLEFTTIME = 1004;

    public static final String PRAKDETAILRELOAD  = "PRAKDETAILRELOAD";
    public static final String UPDATEURL = "GetVersion";

    public static final String DOWNLOADLOCALLASTURL = "/yascn";
    public static final String DOWNLOADLOCALURL = Environment.getExternalStorageDirectory().getAbsolutePath()+DOWNLOADLOCALLASTURL;
    public static final String DOWNLOADAPKNAME = "smartpark.apk";
    public static final String WEATHERCITY = "weathercity";

    /**
     * JQK
     */
    public static final String TAG = "NewPark";
    public static final int CLOSE_RECORD_ITEM = 1000;
    public static final int REFRESH_MYCAR = 1001;
    public static final int REFRESH_REMOVECAR_DELAY = 500;
    public static final int ADDCAR_REQUEST = 1002;
    public static final int ADDCAR_RESULT = 1003;

    //umeng
    public static final String APPKEY = "1cf09e06bab3a";
    public static final String APPSECRET = "923f7329072fbb87d8d797cd007b3ddf";
    public static final int SMS_SUBMIT_SUCCESS = 1000;
    public static final int SMS_GETCODE_SUCCESS = 1001;
    public static final int SMS_FAIL = 1002;
    public static final int SMS_COUNTDOWN = 1003;

    public static final int TOAST_DOUBLE_TIME_LIMIT = 1000;

    public static final String CARDTYPEMOENTH="月卡";
    public static final String CARDTYPEYEAR="年卡";
    public static final String CARDTYPESEASON="季卡";

    public static final int RECORD_ITEM_ANIM_DURATION = 200;
    public static final int RECORD_BOTTOM_HEIGHT = 100;
    public static final int IMAGE_SELECT_REQUEST_CODE = 1;
    public static final int ITEM_CAR_CONTENT = 2;
    public static final int ITEM_CAR_FOOTER = 3;

    public static final int CONSUMPTION = 5;
    public static final int RECHARGE = 6;
    public static final int WECHATPAY = 7;
    public static final int ALIPAY = 8;
    public static final int AUTOMATICPAY = 9;


    public static final int TIMEOUT = 10;

    public static final String SPLITSYMBOL=" ";

    public static final String ROOTURL ="http://app.yascn.com/wrzs/";



//    public static final String ROOTURL = "http://192.168.1.25:8080/wrzs/";
    public static final String URL = ROOTURL+"app/";
    public static final String SECONDURL = ROOTURL+"app2/";
    public static final String MESSAGEDETAILROOTURL = ROOTURL+"static/jsp/usermessage.html?msg_id=";
    public static final String CARLIMITURL = "CarLimit";

    public static final String KEY_USERID = "com.yascn.newpark.userid";
    public static final String KEY_LOGIN = "com.yascn.newpark.login";
    public static final String KEY_NUMBER = "com.yascn.newpark.number";
    public static final String KEY_PHONE = "com.yascn.newpark.phone";
    public static final String KEY_JPUSH = "keyjpush";
    public static final int ERROR = 0;
    public static final int SUCCESS = 1;
    public static final int PROGRESS = 2;

    public static final String UPLOAD = "EditU";

    public static final String HOMEURL = "Index";


    public static final String PRIVACYAGREEMENT = "privacyagreement";



    public static final String NOORDER = "-1";
    public static final String  ORDERRESERVE = "0";
    public static final String ORDERIN = "1";
    public static final String ORDERPAIDNOTOUT = "2-1";
    public static final String ORDERPAIDTIMEOUT = "2-2";
    public static final String ORDEREVALUATE = "3";


    public static final String QUERYCARLICENSEPAID = "2";

    public static final String QUERYCARLICENSENOTPAID = "1";
    public static final int CARLICENSENUMSLIMIT=3;

    public static final String []CARDTYPENAME = {"周卡","月卡","季卡","年卡"};

    public static final String []CARDTYPEDAYS = {"7","30","90","365"};

    public static final String KEY_USERAVATAR = "com.yascn.newpark.useravatar";
    public static final String KEY_ISHAVEPWD = "com.yascn.newpark.ishavepwd";

    public static final String MONTHCARDCARNUMBER = "mothcardcarnumber";

    public static final String REFRESHUSERINFO = "com.yascn.newpark.refreshuserinfo";
    public static final String REFRESHCARNUMBER = "com.yascn.newpark.refreshcarnumber";
    public static final String REFRESHDEFULTNUMBER = "refreshdefultnumber";
    public static final String REFRESHDEFULTNUMBERID = "refreshdefaultnumberid";
    public static final String REFRESHMESSAGENUMBER = "refreshmessagenum";

    public static final String REFLESHMONEY = "com.yascn.newpark.refreshmoney";
    public static final String REFLESHORDER = "com.yascn.newpark.refreshorder";

    public static final String HIDEPAYDIALOG = "com.yascn.newpark.hidepaydialog";

    public static final String SHOURU = "0";
    public static final String ZHICHU = "1";
    public static final String ZHIFUBAO = "0";
    public static final String WEIXIN = "1";
    public static final String YVE = "2";

    public static final String WXAPPID = "wx6745325be6b8e71a";


    public static final String QUERYSTATUSLOADING = "QUERYSTATUSLOADING";
    public static final String QUERYSTATUSFIILED = "QUERYSTATUSFIILED";
    public static final String QUERYSTATUSSUCCESS = "QUERYSTATUSSUCCESS";

    public static final String QUERYSTATUSNODATA = "QUERYSTATUSNODATA";
    public static final String QUERYSTATUSREFRESHFINISH="QUERYSTATUSREFRESHFINISH";
    public static final String QUERYSTATUSREFRESHERROR="QUERYSTATUSREFRESHERROR";
    public static final String QUERYSTATUSLOADMOREFINISH="QUERYSTATUSLOADMOREFINISH";
    public static final String QUERYSTATUSLOADMOREERROR="QUERYSTATUSLOADMOREERROR";
    public static final String QUERYSTATUSNOMOREDATA = "QUERYSTATUSNOMOREDATA";



    /**
     * 王大雷
     */
    public static final String INDEXURL = "IndexList";
    public static final String SERVERERROR = "网络错误";
    public static final int PARKFREENUMREDLINE =50;
    public static final String NOWlOCATIONlAT = "nowlocationlat";
    public static final String NOWlOCATIONlON = "nowlocationlon";
    public static final String MARKERlOCATIONlAT = "markerlocationlat";
    public static final String MARKERlOCATIONlon = "markerlocationlon";
    public static final String EXPORTID = "exportid";
    public static final String SELECTPARKDETAILBEAN = "SELECTPARKDETAILBEAN";
    public static final String PARKCOMMENTURL="CommentList";
    public static final String PARKID = "parkId";
    public static final String COMMMENTIMGS = "commentimgs";
    public static final String COMMENTIMGINDEX = "commentimgindex";
    public static final String NOMOREDATA = "没有更多数据了";
    public static final int MIN_RECOMMAND_DISTANCE = 3000;
    public static final String CARLICENSELISTURL = "NumberList";
    public static final String STARTPARKPOINTURL = "YJYY";
    public static final String MONTHCARDLISTURL="MCParkingList";


    public static final String[] PARKPOINTRESULT= new String[]{"没有剩余车位","预约成功","此用户已经预约过一个停车场","已在当前停车场","此车牌已经预约过"};
    public static final String SEARCHPARKURL = "Search";
    public static final String BROADCASTACTIONSEARCHPARK ="BROADCASTACTIONSEARCHPARK";
    public static final String BROADCASTDATASEARCHPARK = "BROADCASTDATASEARCHPARK";

    public static final String BROADCASTGAODE = "BROADCASTGAODE";
    public static final String BROADCASTGAODENAME = "BROADCASTGAODENAME";
    public static final String BROADCASTGAODESNIPPT = "BROADCASTGAODESNIPPT";
    public static final String BROADCASTGAODELAT = "BROADCASTGAODELAT";
    public static final String BROADCASTGAODELON = "BROADCASTGAODELON";


    public static final String RECENTPARKURL = "GetNearlyParkRecord";
    public static final String BROADCASTNOWLOCATION = "broadcastnowlocation";
    public static final String NOWLOCATIONLAT = "nowlocationlat";
    public static final String NOWLOACTIONLON ="nowlocationlon";
    public static final int NODATA = 3;
    public static final String ISFIRSTLOADAPP = "isfirstloadapp";
    public static final String PARKDETAILURL = "ParkingDetail";
    public static final String COMMENT = "Comment";
    public static final String REFLESHOUT = "com.yascn.newpark.refleshout";
    public static final String BROADCASTMAINTOMAPENTRANCE = "broadcastmaintomapentrance";
    public static final String BROADCASTAPPOINTNOW ="broadcastappointnow";
    public static final String BROADCASTAPPOINTNOWID = "broadcastappointnowid";

    public static final String SHAREDTITLE = "郑在停车";
    public static final String SHAREDTEXT = "郑在停车,停车真方便";
    public static final String SHAREDIMGURL = "http://smartpark.image.alimmdn.com/logo/Icon-90.png?t=1510381728163";
    public static final String SHAREDURL = "http://app.mi.com/details?id=com.yascn.smartpark";
    public static final String PARKFREENUM = "parkfreenum";
    public static final String OPINIONROOTURL = "http://app.yascn.com/wrzs/static/ys/index.html?userid=";
    public static final String GETPHONECODE = "SendSMS";
    public static final String PHONGCODEOVERLIMIT = "0";
    public static final String PHONECODEOVERLIMITWARNING = "验证码次数超过限制,休息一会儿再来~";
    public static final String PHONECODEFAILED = "获取验证码失败";
    public static final String PHONECODEINPUTERROR = "验证码输入错误";

    public static final String GETSMSCODESUCCEED = "1";

    public static final String MARKERTITLERECOMMAND= "0";
    public static final String MARKERTITLEBLUE= "1";
    public static final String MARKERTITLERED= "2";
    public static final float PARKSCALENUM = 1.4f;
    public static final String EMPTYPARKNUM = "车位已满";
    public static final int MAXPARKNUMLINE = 999;
    public static final String ENOUGHPARKNUM = "999+";
    public static final String SETDEFULTCARLICENSE ="设置默认";
    public static final String CONFIRMDEFULTCARLICENSE = "确定";
    public static final String NOTSETYET = "未设置";

    public static final String AUTOPAYDATAUPDATEBROADCAST = "AUTOPAYDATAUPDATEBROADCAST";
    public static final String AUTOPAYPARKID = "AUTOPAYPARKID";
    public static final String AUTOPAYPARKSTATE = "AUTOPAYPARKSTATE";
    public static final String AFTERNOTIFYRESULTBROADCAST = "afternotifyresultbroadcast";
    public static final String AFTERNOTIFYRESULTRESULT = "afternotifyresult";

    public static final String CARLICENSE = "carlicense";
    public static final String CARLICENSEID = "CARLICENSEID";

    public static final String ISBAIDUNAVIAVAILABLE ="isbaidunaviavailable";
    public static final String ISGAODENAVIAVAILABLE = "isgaodenaviavailable";
    public static final String CHANGEPHONEURL = "UpdatePhone";

    public  static final int NAVITAPEBAIDU = 1;
    public static final int NAVITYPEGAODE = 2;
    public static final int NAVITYPELOCAL = 0;

    public static final String LICENSENOTCERTIFIED = "0";
    public static final String LICENSECERTIFIED = "1";
    public static final String LICENSENOTMATCH = "2";

    public static final int IMGPHOTO =0;
    public static final int IMGALBUM = 1;

    public static final String BROADCASTLICENSE = "broadcastlicense";
    public static final String BROADCASTLICENSEDATA = "BROADCASTLICENSEDATA";

    public static final String LINCENSECERTIFICATIONURL = "XSZCompare";
    public static final String LINCENSECERTIFICATIONPARAMLICENSEID = "LICENSE_PLATE_ID";
    public static final String LINCENSECERTIFICATIONPARAMIMG = "IMG";
    public static final int SERVERSUCCESSSTATUSCODE = 1;

    public static final String UPLOADLICENSEFAILCODE = "0";
    public static final String CERTIFICATIONLICENSESUCCESSCODE = "1";
    public static final String CERTIFICATIONLICENSENOTMATCHCODE = "2";
    public static final String CERTIFICATIONLICENSEFAILCODE = "3";



    public static final String UPLOADLICENSEFAIL = "上传失败";
    public static final String CERTIFICATIONLICENSESUCCESS = "认证成功";
    public static final String CERTIFICATIONLICENSENOTMATCH = "认证失败:行驶证与车牌不匹配";
    public static final String CERTIFICATIONLICENSEFAIL = "识别失败";


    public static final String DEFULTCITY = "郑州";

    public static final int LASTARROUNDDISTANCE = 3000;

    public static final String BROADCASTSEARCHHISTORYEMPTY = "broadcastsearchhistoryempty";

    public static final String GENDERMALE = "n";

    public static final String GENDERFEMALE = "w";


    public static final String CARDEFULTRESERVETIME = "30分钟";

    public static final String PARKNAMETRANS = "parknametrans";
    public static final String PARKFREENUMS = "parkfreenums";
    public static final String PARKIDTRANS = "paridtrans";
    public static  final String ISREFILL = "isrefill";
    public static final String MONTHCARDREMAINDAYS = "monthcardremaindays";
    public static final String MONTHCARDRECORDID = "monthcardrecordid";

    public static final String MINEDATADEFULT = " maindatadefult";

    public static final String MONTHCARDDETAILURL = "MonthCardList";

    public static final String MONTHCARDDETAILBEAN = "monthcarddetailbean";

    public static final String GETMONTHCARDPAYINFO = "BuyMonthCard";
    public static final String MONTHCARDPAYTYPEALI = "0";
    public static final String MONTHCARDPAYTYPEWECHAT = "1";


    public static final String HASCOMMENT  = "1";
    public static final String NOTCOMMENT  = "0";
    public static final String ORDERFORMID = "ORDER_FORM_ID";
    public static final String NOCARLIMIT = "0";
    public static final String CARLIMIT = "1";

    public static final String CHANGEPHONESUCCESS = "1";
    public static final String CHANGEPHONEFAILED = "0";
    public static final String QUERYCARLICENSEFEE = "GetPayInfo";
    public static final String COUPONINGURL = "GetCoupon";

    public static final String  COUPONINGTYPEMONEY = "1";
    public static final String COUPONINGTYPETIME = "0";
    public static final String ORDERINFOURL = "GetOrderinfo";

    public static final String PAYSUCCESSBROADCAST = "wxpaysuccessbroadcast";

    public static final String IMGSELECTBROADCAST= "IMAGESELECTBROADCAST";
    public static final String IMGSELECTREQUESTCODE = "imgselectrequestcode";
    public static final String IMGSELECTRESULTCODE = "imgselectresultcode";
    public static final String IMGSELECTDATA = "imgselectdata";

    public static final String  SENDOPINIONURL = "Feedback";
    public static final String COMMINTSUCCESS = "1";

    public static final String LOCATIONPERMISSIONSUCCESS = "locationpermissionsuccess";

    public static final String ISSUPPORTWALLETPAY = "issupportwalletpay";

    public static final String ISSUPPORTCOUPON = "issupportcoupon";

    public static final String ORDERSTATUS = "orderstatus";

    public static final String WALLETPAYURL = "BalancePay";
    public static final String WALLETPAYCATEPWD = "0";

    public static final String ORDERBEAN = "orderbean";
    public static final String ORDERINFOID = "orderinfoid";

    public static final String ISQRCODE = "isqrcode";
    public static final String HASCLICKMINE = "hasclickmine";
    public static final String HASCLICKUSERGUIDE = "hasclickuserguide";
    public static final String JPUSHTYPEPARKINFO = "0";
    public static final String JPUSHTYPEMESSAGE = "1";
    public static final String MESSAGEDETAILURL = "GetMsgDetail";
    public static final String MESSAGEDELETEURL = "SetMsgFlag";
    public static final int MAXUPLOADIMGNUMBER =3 ;
    public static final String SEARCHNORESULT = "没有结果";
    public static final String SEARCHFIELD = "搜索失败";
    public static String ORDERDETAILBEAN = "orderdetailbean";
    public static final String MESSAGEDELETEFLAG = "0";


    public static final String HASCOMMENTSTRING = "已评论";
    public static final String NOTCOMMENTSTRING = "未评论";
    public static String ORDERSTATUSCANCEL = "-1";
    public static final String ORDERSTATUSCANCELSTRING = "已取消";
    public static final String ORDERSTATUSFINISHSTRING="已完成";

    public static final String WALLETPAYSUCCESS = "支付成功";
    public static final String WALLETPAYMONEYNOTENOUGH = "余额不足";
    public static final String WALLETPAYNOTINPARK = "未入场";
    public static final String WALLETPAYPASSWORDWRONG = "密码错误";
    public static final String WALLETMONEYZERO ="费用为0无需支付";

    public static final String WALLETPAYSUCCESSSTATUS = "1";
    public static final String WALLETPAYMONEYNOTENOUGHSTATUS = "2";
    public static final String WALLETPAYNOTINPARKSTATUS = "0";
    public static final String WALLETPAYPASSWORDWRONGSTATUS = "3";
    public static final String WALLETMONEYZEROSTATUS ="4";


    public static final String COUPONINGBROADCAST = "couponingbroadcast";
    public static final String COUPONINGBEAN = "couponingbean";

    public static final String PARKDURINGTIME = "parkduringtime";

    public static final String PAY0MONEY  = "Pay0Money";

    public static final String PAYFREESUCCESS= "1";

    public static final String PAYFREEFAILED = "0";

    public static final String MESSAGEURL = "GetMsgList";

//    public static final String MESSAGEBEANTRANS = "messagebeantrans";

    public static final String MESSAGEBEANIDTRANS = "messagebeanidtrans";

    public static final String MSGHASREADED = "1";
    public static final String MSGNOTREAD = "0";

    public static final String PAYTYPEAPP = "1";//app支付
    public static final String PAYTYPEQRCODE = "2"; //app扫码支付

    public static final String ORDERTYPE = "ORDERTYPE";
    public static final String ORDERTYPEQRCODE = "ORDERTYPEQRCODE";
    public static final String ORDERTYPECARLICENSE = "ORDERTYPECARLICENSE";
    public static final String ORDERTYPELIST = "ORDERTYPELIST";

    public static final String ORDERINFOBEAN = "orderInfoBean";

    public static final String CARLICENSEBEAN = "carlicensebean";

    public static final String ISNEEDRETURNCOUPON = "isneedreturncoupon";


    public static final String VIEWTYPEERROR = "viewtypeerror";

    public static final String VIEWTYPECONTENT = "viewtypecontent";

    public static final String VIEWTYPENODATA = "viewtypenodata";




}
