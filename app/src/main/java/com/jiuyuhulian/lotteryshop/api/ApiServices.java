package com.jiuyuhulian.lotteryshop.api;

import com.jiuyuhulian.lotteryshop.model.AddBankCard;
import com.jiuyuhulian.lotteryshop.model.AddStaff;
import com.jiuyuhulian.lotteryshop.model.AddressList;
import com.jiuyuhulian.lotteryshop.model.BankCardList;
import com.jiuyuhulian.lotteryshop.model.BankCardTypeList;
import com.jiuyuhulian.lotteryshop.model.CashDetail;
import com.jiuyuhulian.lotteryshop.model.CashSubtotal;
import com.jiuyuhulian.lotteryshop.model.CreateOrderRequest;
import com.jiuyuhulian.lotteryshop.model.CreateOrderResponse;
import com.jiuyuhulian.lotteryshop.model.ForgetPwd;
import com.jiuyuhulian.lotteryshop.model.Login;
import com.jiuyuhulian.lotteryshop.model.LotteryDetail;
import com.jiuyuhulian.lotteryshop.model.LotteryList;
import com.jiuyuhulian.lotteryshop.model.MineOrder;
import com.jiuyuhulian.lotteryshop.model.MineShopMessage;
import com.jiuyuhulian.lotteryshop.model.PayBankCard;
import com.jiuyuhulian.lotteryshop.model.PayResponse;
import com.jiuyuhulian.lotteryshop.model.QueryStock;
import com.jiuyuhulian.lotteryshop.model.Regist;
import com.jiuyuhulian.lotteryshop.model.ResponseCode;
import com.jiuyuhulian.lotteryshop.model.ScanOrHandCash;
import com.jiuyuhulian.lotteryshop.model.StaffList;
import com.jiuyuhulian.lotteryshop.model.WeChatPrepay;
import com.jiuyuhulian.lotteryshop.model.getBalance;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Admin on 2017/3/6.
 */

public interface
ApiServices {

  /*  @Query 、@QueryMap ：get请求的query参数
    @File、@FieldMap ：form-url-encoded参数
    @Part：文件
    @Url：可传入完整url*/



    @POST("app/login")
    @FormUrlEncoded
    Observable<Login> login(@Field("code")String code,
                            @Field("staff") String staff,
                            @Field("password") String password);

    @POST("app/register")
    @FormUrlEncoded
    Observable<Regist>register(@Field("username") String username,
                               @Field("id_card") String id_card,
                               @Field("mobile") String mobile,
                               @Field("code") String code,
                               @Field("address") String address,
                               @Field("password") String password,
                               @Field("pay_pwd") String pay_pwd);
    @POST("app/logout")
    Observable<ResponseCode>loginout();

    /***
     *  'register', // 注册
     'find-password' // 忘记密码
     * */
    @POST("app/send-verify-code")
    @FormUrlEncoded
    Observable<ResponseCode>sendCode(@Field("module") String module,
                                 @Field("mobile") String mobile);

    @POST("app/forget-password")
    @FormUrlEncoded
    Observable<ForgetPwd>forgetPwd(@Field("mobile") String mobile,
                                   @Field("code") String code,
                                   @Field("password") String password);

    @POST("app/change-password")
    @FormUrlEncoded
    Observable<ResponseCode>changePwd(@Field("staff_id") String staff_id,
                                      @Field("password") String password,
                                      @Field("pwd") String pwd);

    @POST("app/change-pay-pwd")
    @FormUrlEncoded
    Observable<ResponseCode>changePayPwd(@Field("shop_id") String shop_id,
                                         @Field("staff_id") String staff_id,
                                         @Field("mobile") String mobile,
                                         @Field("code") String code,
                                         @Field("pay_pwd") String pay_pwd);

    @POST("app/lottery-list")
    @FormUrlEncoded
    Observable<LotteryList>lotteryShopList(@Field("page") int page,
                                        @Field("price") String price);

    @POST("app/shop-message")
    @FormUrlEncoded
    Observable<MineShopMessage>mineShopMessage(@Field("staff_id") String staff_id);

    @POST("app/address-list")
    @FormUrlEncoded
    Observable<AddressList>addressList(@Field("shop_id") String shop_id);

    @POST("app/address-add")
    @FormUrlEncoded
    Observable<ResponseCode>addAddress(@Field("shop_id") String shop_id,
                                       @Field("username") String username,
                                       @Field("mobile") String mobile,
                                       @Field("city") String city,
                                       @Field("address") String address);
    @POST("app/address-update")
    @FormUrlEncoded
    Observable<ResponseCode>updateAddress(@Field("address_id") String id,
                                          @Field("shop_id") String shop_id,
                                          @Field("username") String username,
                                          @Field("mobile") String mobile,
                                          @Field("city") String city,
                                          @Field("address") String address);

    @POST("app/address-delete")
    @FormUrlEncoded
    Observable<ResponseCode>deleteAddress(@Field("address_id") String addressId);

    @POST("app/address-status")
    @FormUrlEncoded
    Observable<ResponseCode>defaultAddressStatus(@Field("address_id") String addressId,@Field("is_default") String is_default);


    @POST("app/stock-inquire")
    @FormUrlEncoded
    Observable<QueryStock>stockQuery(@Field("shop_id") String shop_id , @Field("activate_status") String activate_status );


    @POST("app/lottery-detail")
    @FormUrlEncoded
    Observable<LotteryDetail>lotteryDetail(@Field("lottery_id") int lottery_id);



    @POST("app/create-order")
    Observable<CreateOrderResponse>createOrder(@Body CreateOrderRequest request);


    @POST("app/staff-list")
    @FormUrlEncoded
    Observable<StaffList>staffList(@Field("shop_id")String shop_id );

    @POST("app/staff-add")
    @FormUrlEncoded
    Observable<AddStaff>addStaff(@Field("shop_id")String shop_id,
                                 @Field("staff")String staff,
                                 @Field("password")String password ,
                                 @Field("mobile")String mobile );

    @POST("app/staff-update")
    @FormUrlEncoded
    Observable<ResponseCode>editStaff(@Field("staff_id")String staff_id,
                                      @Field("staff")String staff ,
                                      @Field("password")String password ,
                                      @Field("mobile")String mobile);

    @POST("app/staff-delete")
    @FormUrlEncoded
    Observable<ResponseCode>deleteStaff(@Field("staff_id")String staff_id);

    @POST("app/bank-card-list")
    @FormUrlEncoded
    Observable<BankCardList>getBankCardList(@Field("shop_id")String shop_id);

    @POST("app/add-bank-card")
    @FormUrlEncoded
    Observable<AddBankCard>addBankCard(@Field("shop_id")String shop_id,
                                       @Field("card_holder")String card_holder,
                                       @Field("bank")String bank,
                                       @Field("card_number")String card_number,
                                       @Field("mobile")String mobile,
                                       @Field("code")String code);
    @GET("app/bank-list")
    Observable<BankCardTypeList>getBankTypeList();

    @POST("app/unbind-bank-card")
    @FormUrlEncoded
    Observable<ResponseCode>unbindBankCard(@Field("bank_id")String bank_id );

    @POST("app/verify-pay")
    @FormUrlEncoded
    Observable<ResponseCode>verifyPay(@Field("shop_id")String shop_id,@Field("pay_pwd")String pay_pwd);

    @POST("app/scan-cash")
    @FormUrlEncoded
    Observable<ScanOrHandCash>scanCash(@Field("code")String code, @Field("type")String type);

    @POST("app/pay-weixin")
    @FormUrlEncoded
    Observable<WeChatPrepay>wechatPrepay(@Field("money")double money );

    @POST("app/app/pay-card")
    @FormUrlEncoded
    Observable<PayBankCard>payBankcard(@Field("cvv")String cvv,
                                       @Field("money")double money ,
                                       @Field("cardName")String cardName ,
                                       @Field("expireDate")String expireDate ,
                                       @Field("cardPhone")String cardPhone ,
                                       @Field("cardNumber")String cardNumber);
    @POST("app/scan-cash")
    @FormUrlEncoded
    Observable<ScanOrHandCash>handCash(@Field("sn")String sn, @Field("password")String password, @Field("type")String type);

    @POST("app/pay")
    @FormUrlEncoded
    Observable<PayResponse>pay(@Field("shop_id")String shop_id, @Field("pay_type")String pay_type , @Field("pay_status")String pay_status,
                               @Field("order_id")String order_id  , @Field("money")double money );


    @POST("app/main-order")
    @FormUrlEncoded
    Observable<MineOrder>mineOrder(@Field("shop_id")String shop_id, @Field("type")int type,@Field("page") int page );

    @POST("app/search-order")
    @FormUrlEncoded
    Observable<MineOrder>searchOrder(@Field("shop_id")String shop_id,@Field("search")String search);

    @POST("app/cash-detail")
    @FormUrlEncoded
    Observable<CashDetail>cashDetail(@Field("shop_id")String shop_id,@Field("page")int page );

    @POST("app/cancel-order")
    @FormUrlEncoded
    Observable<ResponseCode>cancleOrder(@Field("order_id")String order_id );

    @POST("app/balance")
    Observable<getBalance>getBalance();

    @POST("app/confirm-receipt")
    @FormUrlEncoded
    Observable<ResponseCode>confirmReceipt(@Field("order_id")String order_id);

    @POST("app/cash-detail")
    @FormUrlEncoded
    Observable<CashDetail>cashSearch(@Field("shop_id")String shop_id, @Field("search")String search,@Field("page")int page );

    @POST("app/subtotal")
    @FormUrlEncoded
    Observable<CashSubtotal>cashSubtotal(@Field("page")int page);

    @POST("app/clear-subtotal")
    Observable<ResponseCode>clearSubtotal();

    @POST("app/activation")
    @FormUrlEncoded
    Observable<ResponseCode>activeTicket(@Field("number")String number , @Field("type")String type );

}
