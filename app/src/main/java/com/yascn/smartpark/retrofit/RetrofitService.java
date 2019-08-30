package com.yascn.smartpark.retrofit;


import com.yascn.smartpark.bean.ALIPay;
import com.yascn.smartpark.bean.AliRecharge;
import com.yascn.smartpark.bean.BandNumber;
import com.yascn.smartpark.bean.CancelOrder;
import com.yascn.smartpark.bean.CarLicense;
import com.yascn.smartpark.bean.CarLimitBean;
import com.yascn.smartpark.bean.ChangePhoneBean;
import com.yascn.smartpark.bean.Comment;
import com.yascn.smartpark.bean.CouponingBean;
import com.yascn.smartpark.bean.DelNumber;
import com.yascn.smartpark.bean.DeleteMessageBean;
import com.yascn.smartpark.bean.EditU;
import com.yascn.smartpark.bean.GetBanner;
import com.yascn.smartpark.bean.GetPayMoney;
import com.yascn.smartpark.bean.HomeBean;
import com.yascn.smartpark.bean.LicenseCertificationResultBean;
import com.yascn.smartpark.bean.Login;
import com.yascn.smartpark.bean.MessageBean;
import com.yascn.smartpark.bean.MessageItemBean;
import com.yascn.smartpark.bean.MonthCardDetailBean;
import com.yascn.smartpark.bean.MonthCardListBean;
import com.yascn.smartpark.bean.NumberList;
import com.yascn.smartpark.bean.OpinionBean;
import com.yascn.smartpark.bean.OrderInfoBean;
import com.yascn.smartpark.bean.OrderList;
import com.yascn.smartpark.bean.Ordering;
import com.yascn.smartpark.bean.ParkComment;
import com.yascn.smartpark.bean.ParkDetailBean;
import com.yascn.smartpark.bean.ParkPointBean;
import com.yascn.smartpark.bean.PhoneSmsBean;
import com.yascn.smartpark.bean.QueryCarLicenseFeeBean;
import com.yascn.smartpark.bean.ReRecordList;
import com.yascn.smartpark.bean.RecentParkBean;
import com.yascn.smartpark.bean.SearchResult;
import com.yascn.smartpark.bean.SetAutoPay;
import com.yascn.smartpark.bean.SetDefaultNo;
import com.yascn.smartpark.bean.UpdateBean;
import com.yascn.smartpark.bean.Userinfo;
import com.yascn.smartpark.bean.WXPay;
import com.yascn.smartpark.bean.WalletPayBean;
import com.yascn.smartpark.bean.WxRecharge;
import com.yascn.smartpark.bean.YascnParkListBean;
import com.yascn.smartpark.model.Pay0Bean;
import com.yascn.smartpark.utils.AppContants;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.rest.Post;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

import static com.yascn.smartpark.utils.AppContants.CARLICENSELISTURL;
import static com.yascn.smartpark.utils.AppContants.CARLIMITURL;
import static com.yascn.smartpark.utils.AppContants.CHANGEPHONEURL;
import static com.yascn.smartpark.utils.AppContants.COMMENTIMGINDEX;
import static com.yascn.smartpark.utils.AppContants.COUPONINGURL;
import static com.yascn.smartpark.utils.AppContants.GETMONTHCARDPAYINFO;
import static com.yascn.smartpark.utils.AppContants.GETPHONECODE;
import static com.yascn.smartpark.utils.AppContants.LINCENSECERTIFICATIONPARAMLICENSEID;
import static com.yascn.smartpark.utils.AppContants.MONTHCARDDETAILURL;
import static com.yascn.smartpark.utils.AppContants.MONTHCARDLISTURL;
import static com.yascn.smartpark.utils.AppContants.ORDERINFOURL;
import static com.yascn.smartpark.utils.AppContants.PARKDETAILURL;
import static com.yascn.smartpark.utils.AppContants.QUERYCARLICENSEFEE;
import static com.yascn.smartpark.utils.AppContants.UPDATEURL;

/**
 * Created by YASCN on 2017/1/4.
 */

public interface RetrofitService {

    /**
     * 登录
     *
     * @param username
     * @return
     */
    @GET("Login")
    Observable<Login> login(@Query("USERNAME") String username);

    @GET("Ordering")
    Observable<Ordering> ordering(@Query("USER_ID") String userID);

    @GET("GetBanner")
    Observable<GetBanner> getBanner();


    @GET("Userinfo")
    Observable<Userinfo> userinfo(@Query("USER_ID") String userID);


    @GET("BandNumber")
    Observable<BandNumber> bandNumber(@Query("USER_ID") String userID,
                                      @Query("NUMBER") String number);


    @GET("DelNumber")
    Observable<DelNumber> delNumber(@Query("LICENSE_PLATE_ID") String numberID);


    @GET("SetDefaultNo")
    Observable<SetDefaultNo> setDefaultNo(@Query("LICENSE_PLATE_ID") String numberID,
                                          @Query("USER_ID") String userID);


    @GET("SetAutoPay")
    Observable<SetAutoPay> setAutoPay(@Query("LICENSE_PLATE_ID") String numberID,
                                      @Query("CATE") String cate);



    @GET("ReRecordList")
    Observable<ReRecordList> reRecordList(@Query("pageNum") int pageNum,
                                          @Query("USER_ID") String userID);
    @GET(CARLICENSELISTURL)
    Observable<CarLicense> getCarLicense(@Query("USER_ID") String userID);



    @GET(AppContants.PARKCOMMENTURL)
    Observable<ParkComment> getParkComment(@Query("PARKING_ID")String parkId,@Query("pageNum")int pageNum);
    @GET(AppContants.STARTPARKPOINTURL)
    Observable<ParkPointBean> startPointPark(@Query("PARKING_ID")String parkId,@Query("USER_ID")String USER_ID,@Query("NUMBER")String carLicense);
    @GET(AppContants.SEARCHPARKURL)
    Observable<SearchResult> startSearch(@Query("KEYWORDS")String keywords);

    /**
     * 更改用户信息
     *
     * @param url
     * @param Body
     * @return
     */
    @POST()
    Observable<EditU> upLoad(
            @Url() String url,
            @Body RequestBody Body);

    @GET(UPDATEURL)
    Observable<UpdateBean> getUpdateData();

    @POST()
    Observable<LicenseCertificationResultBean> uploadLincense(@Url()String url,@Body RequestBody Body);

    @GET("NumberList")
    Observable<NumberList> numberList(@Query("USER_ID") String userID);

    @GET("ReRecordList")
    Observable<ReRecordList> reRecordList(@Query("pageNum") int pageNum,
                                          @Query("USER_ID") String userID,
                                          @Query("CATE") String cate);

    @GET("aliRecharge")
    Observable<AliRecharge> aliRecharge(@Query("MONEY") String money,
                                        @Query("USER_ID") String userID);

    @GET("wxRecharge")
    Observable<WxRecharge> wxRecharge(@Query("MONEY") String money,
                                      @Query("USER_ID") String userID);

    @GET(GETMONTHCARDPAYINFO)
    Observable<AliRecharge> getAliMonthCardPayInfo(@Query("MONTHCARD_ID")String monthcardId,@Query("USER_ID")String userId,@Query("NUMBER")String carNumber,@Query("STIME")String startTime,@Query("FLAG")String aliFlag);

    @GET(GETMONTHCARDPAYINFO)
    Observable<WxRecharge> getWeChatMonthCardPayInfo(@Query("MONTHCARD_ID")String monthcardId,@Query("USER_ID")String userId,@Query("NUMBER")String carNumber,@Query("STIME")String startTime,@Query("FLAG")String weChatFlag);



    @GET(GETMONTHCARDPAYINFO)
    Observable<AliRecharge> getAliBuyMonthCardPayInfo(@Query("MONTHCARD_ID")String monthcardId,@Query("USER_ID")String userId,@Query("NUMBER")String carNumber,@Query("FLAG")String aliFlag,@Query("TYPE")String type);

    @GET(GETMONTHCARDPAYINFO)
    Observable<WxRecharge> getWeChatBuyMonthCardPayInfo(@Query("MONTHCARD_ID")String monthcardId,@Query("USER_ID")String userId,@Query("NUMBER")String carNumber,@Query("FLAG")String weChatFlag,@Query("TYPE")String type);

    @GET(GETMONTHCARDPAYINFO)
    Observable<AliRecharge> getAliRefillMonthCardPayInfo(@Query("MONTHCARD_ID")String monthcardId,@Query("MCARDRECORD_ID")String monthCardRecordId,@Query("FLAG")String aliFlag,@Query("TYPE")String type);

    @GET(GETMONTHCARDPAYINFO)
    Observable<WxRecharge> getWeChatRefillMonthCardPayInfo(@Query("MONTHCARD_ID")String monthcardId,@Query("MCARDRECORD_ID")String monthCardRecordId,@Query("FLAG")String weChatFlag,@Query("TYPE")String type);


    @GET("CancelOrder")
    Observable<CancelOrder> cancelOrder(@Query("ORDER_FORM_ID") String orderID);

    @GET("GetPayMoney")
    Observable<GetPayMoney> getPayMoney(@Query("ORDER_FORM_ID") String orderID,@Query("COUPON_ID")String couponId);

    /**
     * 微信支付
     *
     * @param orderID
     * @return
     */
    @GET("wxPay")
    Observable<WXPay> wxPay(@Query("ORDER_FORM_ID") String orderID,@Query("COUPON_ID") String conponId,@Query("TYPE")String type);

    /**
     * 支付宝支付
     *
     * @param orderID
     * @return
     */
    @GET("Alipay")
    Observable<ALIPay> aliPay(@Query("ORDER_FORM_ID") String orderID,@Query("COUPON_ID") String conponId,@Query("TYPE")String type);

    @GET(AppContants.RECENTPARKURL)
    Observable<RecentParkBean> getRecentPark(@Query("USER_ID") String USER_ID);

    @GET("OrderList")
    Observable<OrderList> orderList(@Query("USER_ID") String userID,
                                    @Query("pageNum") String pageNum);

    @GET(PARKDETAILURL)
    Observable<ParkDetailBean> getParkDetail(@Query("PARKING_ID") String parkId);
    @GET(GETPHONECODE)
    Observable<PhoneSmsBean> getPhoneSmsBean(@Query("PHONE") String phone,@Query("CODE")String code);

    @GET(CHANGEPHONEURL)
    Observable<ChangePhoneBean> changePhone(@Query("USER_ID")String userId,@Query("PHONE")String phone);


    @GET(QUERYCARLICENSEFEE)
    Observable<QueryCarLicenseFeeBean> queryCarLicenseFee(@Query("number")String carLincense,@Query("USER_ID")String userId);


    @GET(COUPONINGURL)
    Observable<CouponingBean> getCouponing(@Query("USER_ID")String userId);

    @GET(ORDERINFOURL)
    Observable<OrderInfoBean> getOrderInfo(@Query("ORDER_FORM_ID")String orderInfoId,@Query("USER_ID")String userId);


//    @GET(AppContants.WALLETPAYURL)
//    Observable<WalletPayBean> getwalletPayResult(@Query("ORDER_FROM_ID")String orderid,@Query("PWD")String pwd,@Query("CATE")String cate);


    @GET(AppContants.WALLETPAYURL)
    Observable<WalletPayBean> getwalletPayResult(@Query("ORDER_FORM_ID")String orderid,@Query("COUPON_ID") String conponId, @Query("TYPE")String type);

    @GET(AppContants.HOMEURL)
    Observable<HomeBean> getHomeIndex(@Query("USER_ID")String userid);

    @GET(AppContants.PAY0MONEY)
    Observable<Pay0Bean> startPayFree(@Query("ORDER_FORM_ID")String orderFormId,@Query("COUPON_ID")String couponId);


    @GET(AppContants.MESSAGEURL)
    Observable<MessageBean> getMessage(@Query("USER_ID")String userId,@Query("pageNum")String pageNum);


    @GET(AppContants.MESSAGEDETAILURL)
    Observable<MessageItemBean> getMessageDetail(@Query("msg_id")String msgId);


    @GET(AppContants.MESSAGEDELETEURL)
    Observable<DeleteMessageBean> deleteMessage(@Query("msg_id")String msgId,@Query("flag")String flag);



    @GET(AppContants.INDEXURL)
    Observable<YascnParkListBean> getYascnParkList();
    /**
     * 提交评论
     *
     * @param url
     * @param Body
     * @return
     */
    @POST()
    Observable<Comment> comment(
            @Url() String url,
            @Body RequestBody Body);

    @POST()
    Observable<OpinionBean> sentOpinion(
            @Url() String url,
            @Body RequestBody Body);

    @GET(CARLIMITURL)
    Observable<CarLimitBean> getCarLimitBean();


    @GET(MONTHCARDLISTURL)
    Observable<MonthCardListBean> getMonthCardList();

    @GET(MONTHCARDDETAILURL)
    Observable<MonthCardDetailBean> getMonthCardDetail(@Query("PARKING_ID")String parkId);
}
