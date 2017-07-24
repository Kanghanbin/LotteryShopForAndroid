package com.jiuyuhulian.lotteryshop.function;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.blankj.utilcode.utils.ToastUtils;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.api.ApiServices;
import com.jiuyuhulian.lotteryshop.config.Constant;
import com.jiuyuhulian.lotteryshop.model.WeChatPrepay;
import com.jiuyuhulian.lotteryshop.rest.RetrofitHttp;
import com.jiuyuhulian.lotteryshop.utils.DialogUtil;
import com.jiuyuhulian.lotteryshop.view.ChooseMoneyLayout;
import com.jiuyuhulian.lotteryshop.view.TopBar;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.autolayout.AutoLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *
 */
public class TransferInMoneyActivity extends AppCompatActivity {
    @BindView(R.id.money_chose_money)
    ChooseMoneyLayout moneyChoseMoney;
    @BindView(R.id.btn_charge)
    Button btnCharge;
    @BindView(R.id.topbar)
    TopBar topbar;
    @BindView(R.id.rb_wx)
    RadioButton rbWx;
    @BindView(R.id.rb_zfb)
    RadioButton rbZfb;
    @BindView(R.id.rb_yhk)
    RadioButton rbYhk;
    @BindView(R.id.rg_channel)
    RadioGroup rgChannel;
    @BindView(R.id.activity_transfer_money)
    AutoLinearLayout activityTransferMoney;
    @BindView(R.id.et_charge_Money)
    EditText etChargeMoney;
    private int money; //当前选择的金额
    private double chargeMoney;
    private ApiServices apiServices;
    private Context mContext;

    //微信支付
    private IWXAPI msgApi;
    private PayReq request;

    /**
     * 0.微信  1.支付宝 2.银行卡
     */
    private int status=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_money);
        ButterKnife.bind(this);
        apiServices= RetrofitHttp.getApiServiceWithToken();
        mContext=this;
        msgApi= WXAPIFactory.createWXAPI(this,Constant.APP_ID);
        request = new PayReq();
        initViews();
    }

    private void initViews() {
        rgChannel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
               if(checkedId==rbWx.getId()){
                   status=0;
               }else if(checkedId==rbZfb.getId()){
                   status=1;
               }else if(checkedId==rbYhk.getId()){
                   status=2;
               }
            }
        });
        topbar.setRightText("充值记录");
        topbar.setOnTopBarClickListener(new TopBar.OnTopBarClickListener() {
            @Override
            public void onTopBarRightClick(View v) {
                Intent intent = new Intent(TransferInMoneyActivity.this, ChargeRecordsActivity.class);
                intent.putExtra("from", "transerin");
                startActivity(intent);
            }

            @Override
            public void onTopBarLeftClick(View v) {
                finish();
            }
        });
        //数设置据源
        moneyChoseMoney.setMoneyData(new int[]{500, 1000, 2000, 5000});
        //设置默认选中项
        moneyChoseMoney.setDefaultPositon(0);
        //金额选择监听
        moneyChoseMoney.setOnChoseMoneyListener(new ChooseMoneyLayout.onChoseMoneyListener() {
            @Override
            public void chooseMoney(int position, boolean isCheck, int moneyNum) {
                if (isCheck) {
                    money = moneyNum;
                    etChargeMoney.setText(moneyNum+"");
                } else {
                    money = 0;
                }
            }
        });
    }
    @OnClick(R.id.btn_charge)
    public void charge(){
        chargeMoney=Double.parseDouble(etChargeMoney.getText().toString());
        Intent intent;
        switch (status){
            case 0:
                wechatPrepay();
              break;
            case 1:
                break;
            case 2:
                intent=new Intent(TransferInMoneyActivity.this,BankChargeActivity.class);
                intent.putExtra("charge",chargeMoney);
                startActivity(intent);
                break;
        }
    }

    private void wechatPrepay() {
        new DialogUtil(mContext).show();
        apiServices.wechatPrepay(chargeMoney).subscribeOn(Schedulers.io())
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
        if(weChatPrepay.getCode()==0){
            ToastUtils.showShortToast("微信预支付订单成功");
            //TODO:调起微信支付

            // 将该app注册到微信
            msgApi.registerApp(Constant.APP_ID);
            request.appId = Constant.APP_ID;
            request.partnerId =weChatPrepay.getData().getPartnerId();
            request.prepayId=weChatPrepay.getData().getPrepayId();
            request.packageValue = weChatPrepay.getData().getPackageX();
            request.nonceStr= weChatPrepay.getData().getNonceStr();
            request.timeStamp= weChatPrepay.getData().getTimestamp();
            request.sign= weChatPrepay.getData().getSign();
            msgApi.sendReq(request);
        }else {
            ToastUtils.showShortToast(weChatPrepay.getMessage());
        }
    }
}
