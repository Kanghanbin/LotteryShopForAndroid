package com.jiuyuhulian.lotteryshop.function;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.blankj.utilcode.utils.SPUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.api.ApiServices;
import com.jiuyuhulian.lotteryshop.config.Constant;
import com.jiuyuhulian.lotteryshop.model.CreateOrderResponse;
import com.jiuyuhulian.lotteryshop.model.PayResponse;
import com.jiuyuhulian.lotteryshop.model.WeChatPrepay;
import com.jiuyuhulian.lotteryshop.model.getBalance;
import com.jiuyuhulian.lotteryshop.rest.RetrofitHttp;
import com.jiuyuhulian.lotteryshop.utils.DialogUtil;
import com.jiuyuhulian.lotteryshop.view.TopBar;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.autolayout.AutoRelativeLayout;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ConfirmPayActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.topbar)
    TopBar topbar;

    @BindView(R.id.freight_price)
    TextView freightPrice;
    @BindView(R.id.arl_freight)
    AutoRelativeLayout arlFreight;
    @BindView(R.id.tv_payKind)
    TextView tvPayKind;
    @BindView(R.id.tv_pay_confirm)
    TextView tvPayConfirm;
    @BindView(R.id.tv_show_price)
    TextView tvShowPrice;
    @BindView(R.id.rl_bottom)
    AutoRelativeLayout rlBottom;
    @BindView(R.id.activity_shopping_car)
    AutoRelativeLayout activityShoppingCar;
    @BindView(R.id.rb_ye)
    RadioButton rbYe;
    @BindView(R.id.rb_wx)
    RadioButton rbWx;
    @BindView(R.id.rb_zfb)
    RadioButton rbZfb;
    @BindView(R.id.rb_yhk)
    RadioButton rbYhk;
    @BindView(R.id.rg_channel)
    RadioGroup rgChannel;

    private SPUtils spUtils;
    private ApiServices apiServices;
    private String shopId;
    private Context mContext;

    private CreateOrderResponse.DataBean dataBean;
    private double total;
    private SweetAlertDialog payResultDialog;
    private Intent payResultIntent;
    private double leftMoney;
    private String orderId;

    //微信支付
    private IWXAPI msgApi;
    private PayReq request;
    private static int VERFYPAYPWD = 10;

    /**
     * 1.余额支付 2.微信支付 3.支付宝支付 4.银行卡支付
     */
    private int kind = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_pay);
        ButterKnife.bind(this);
        mContext = this;
        apiServices = RetrofitHttp.getApiServiceWithToken();
        spUtils = new SPUtils("regist");
        shopId = spUtils.getString("shopid");
        initView();
        initListeners();
        getBalance();
        msgApi = WXAPIFactory.createWXAPI(this, Constant.APP_ID);
        request = new PayReq();

    }

    private void getBalance() {
        apiServices.getBalance().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<getBalance>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShortToast(Constant.SERVER_ERROR);
                    }

                    @Override
                    public void onNext(getBalance getBalance) {
                        if(getBalance.getCode()==0){
                            leftMoney=Double.parseDouble(getBalance.getData().getMoney());
                            rbYe.setText("当前可用余额：" + new DecimalFormat("0.00").format(leftMoney));
                        }else {
                            ToastUtils.showShortToast(getBalance.getMessage());
                        }
                    }
                });
    }

    private void initListeners() {
        tvPayConfirm.setOnClickListener(this);
        rgChannel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == rbYe.getId()) {
                    kind = 1;
                } else if (checkedId == rbWx.getId()) {
                    kind = 2;
                } else if (checkedId == rbYhk.getId()) {
                    kind = 3;
                } else if (checkedId == rbZfb.getId()) {
                    kind = 4;
                }
            }
        });
    }

    private void initView() {
        Intent shoppingCar = getIntent();
        switch (shoppingCar.getIntExtra("from", 0)) {
            case 1:
                total = shoppingCar.getDoubleExtra("total", 10);
                tvShowPrice.setText("￥  " + new DecimalFormat("0.00").format(total + 10));
                dataBean = (CreateOrderResponse.DataBean) shoppingCar.getSerializableExtra("createOrder");
                orderId=dataBean.getOrder_id();
                break;
            case 2:
                total = shoppingCar.getDoubleExtra("total",10)-10;
                tvShowPrice.setText("￥  " + new DecimalFormat("0.00").format(total+10));
                orderId=shoppingCar.getStringExtra("orderId");


                break;
        }


        topbar.setOnTopBarClickListener(new TopBar.OnTopBarClickListener() {
            @Override
            public void onTopBarRightClick(View v) {

            }

            @Override
            public void onTopBarLeftClick(View v) {
                finish();
            }
        });
    }

    /**
     * 验证通过调用余额支付请求
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == VERFYPAYPWD && resultCode == VERFYPAYPWD) {
            if ((total + 10) <= leftMoney) {
                new DialogUtil(mContext).show();
                apiServices.pay(shopId, "4", "1",orderId, total + 10).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<PayResponse>() {
                            @Override
                            public void onCompleted() {
                                new DialogUtil(mContext).dismiss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                new DialogUtil(mContext).dismiss();
                                ToastUtils.showShortToast(Constant.SERVER_ERROR);
                            }

                            @Override
                            public void onNext(PayResponse responseCode) {
                                handlePayResult(responseCode);

                            }
                        });
            } else {
                ToastUtils.showShortToast("可用余额不足，请先充值");
            }
        }


    }

    private void handlePayResult(PayResponse responseCode) {
        if (responseCode.getCode() == 0){
            tvPayConfirm.setClickable(false);
            tvPayConfirm.setBackgroundColor(getResources().getColor(R.color.pay_cannot));
            rbYe.setText("当前可用余额:" + new DecimalFormat("0.00").format(responseCode.getData().getMoney()));
            payResultDialog=new SweetAlertDialog(mContext,SweetAlertDialog.CUSTOM_IMAGE_TYPE)
            .setCustomImage(R.drawable.duhao)
            .setTitleText("支付成功");
            payResultDialog.setConfirmText("查看订单").setCancelText("继续购买").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    payResultDialog.dismissWithAnimation();
                    payResultIntent=new Intent(mContext,OrderManagerActivity.class);
                    startActivity(payResultIntent);

                }
            }).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    payResultDialog.dismissWithAnimation();
                    payResultIntent=new Intent(mContext,EnterTicketActivity.class);
                    startActivity(payResultIntent);
                }
            }).setCanceledOnTouchOutside(true);
            payResultDialog.show();
        }else {
            ToastUtils.showShortToast(responseCode.getMessage());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_pay_confirm:
                switch (kind) {
                    case 1:
                        Intent intent = new Intent(this, VerifyPayPwdActivity.class);
//                        intent.putExtra("databean", dataBean);
                        startActivityForResult(intent, VERFYPAYPWD);
                        break;
                    case 2:
                        wechatPrepay();
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
                break;
        }


    }


    private void wechatPrepay() {
        new DialogUtil(mContext).show();
        apiServices.wechatPrepay(total + 10).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WeChatPrepay>() {
                    @Override
                    public void onCompleted() {
                        new DialogUtil(mContext).dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        new DialogUtil(mContext).dismiss();
                        ToastUtils.showShortToast(Constant.SERVER_ERROR);

                    }

                    @Override
                    public void onNext(WeChatPrepay weChatPrepay) {
                        handWeChatPrepayResult(weChatPrepay);
                    }
                });
    }

    private void handWeChatPrepayResult(WeChatPrepay weChatPrepay) {
        if (weChatPrepay.getCode() == 0) {
            ToastUtils.showShortToast("微信预支付订单成功");
            //TODO:调起微信支付

            // 将该app注册到微信
            msgApi.registerApp(Constant.APP_ID);
            request.appId = weChatPrepay.getData().getAppId();
            request.partnerId = weChatPrepay.getData().getPartnerId();
            request.prepayId = weChatPrepay.getData().getPrepayId();
            request.packageValue = weChatPrepay.getData().getPackageX();
            request.nonceStr = weChatPrepay.getData().getNonceStr();
            request.timeStamp = weChatPrepay.getData().getTimestamp();
            request.sign = weChatPrepay.getData().getSign();
            msgApi.sendReq(request);
        } else {
            ToastUtils.showShortToast(weChatPrepay.getMessage());
        }
    }

}
