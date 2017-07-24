package com.jiuyuhulian.lotteryshop.function;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.utils.SPUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.api.ApiServices;
import com.jiuyuhulian.lotteryshop.config.Constant;
import com.jiuyuhulian.lotteryshop.model.AddBankCard;
import com.jiuyuhulian.lotteryshop.model.ResponseCode;
import com.jiuyuhulian.lotteryshop.rest.RetrofitHttp;
import com.jiuyuhulian.lotteryshop.utils.DialogUtil;
import com.jiuyuhulian.lotteryshop.view.TopBar;
import com.zhy.autolayout.AutoLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddIdCard2Activity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.topbar)
    TopBar topbar;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.btn_getCode)
    Button btnGetCode;
    @BindView(R.id.btn_vertify_next)
    Button btnVertifyNext;
    @BindView(R.id.notGetCode)
    TextView notGetCode;
    @BindView(R.id.all_vertify_phone)
    AutoLinearLayout allVertifyPhone;

    private String card_holder, card_number, bank, mobile, code;
    private SPUtils spUtils;
    private ApiServices apiServices;
    private String shopId;
    private Context mContext;

    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_id_card3);
        ButterKnife.bind(this);
        initViews();
        initListeners();
    }

    private void initViews() {
        Intent from = getIntent();
        card_holder = from.getStringExtra("card_holder");
        bank = from.getStringExtra("bank");
        card_number = from.getStringExtra("card_number");

        mContext = this;
        apiServices = RetrofitHttp.getApiServiceWithToken();
        spUtils = new SPUtils("regist");
        shopId = spUtils.getString("shopid");
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

    private void initListeners() {
        btnGetCode.setOnClickListener(this);
        btnVertifyNext.setOnClickListener(this);
        notGetCode.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_vertify_next:
                mobile = etPhone.getText().toString().trim();
                code = etCode.getText().toString().trim();
                new DialogUtil(mContext).show();
                apiServices.addBankCard(shopId, card_holder, bank, card_number, mobile, code)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<AddBankCard>() {
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
                            public void onNext(AddBankCard addBankCard) {
                                handAddBankCard(addBankCard);
                            }
                        });
                break;
            case R.id.notGetCode:
                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("收不到验证码")
                        .setContentText("验证码发送至您的银行预留手机号(1.请确认当前是否使用银行预留手机号码" +
                                "2.请检查短信是否被手机安全软件拦截" +
                                "3.若预留号码已停用,请联系银行客服咨询" +
                                "4.获取更多帮助，请拨打电话********)")
                        .setConfirmText("知道了")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();

                            }
                        })
                        .show();
                break;
            case R.id.btn_getCode:
                mobile=etPhone.getText().toString().trim();
                new DialogUtil(mContext).show();
                apiServices.sendCode("bank-add",mobile).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseCode>() {
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
                            public void onNext(ResponseCode responseCode) {
                                handSendCode(responseCode);
                            }
                        });
                break;
        }
    }

    private void handSendCode(ResponseCode responseCode) {
        if(responseCode.getCode()==0){
            ToastUtils.showShortToast("成功发送验证码");
            startCountDown();
        }else {
            ToastUtils.showShortToast(responseCode.getMessage());
        }
    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(Constant.MILLISINFUTURE, Constant.COUNTDOWNINTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                btnGetCode.setClickable(false);
                btnGetCode.setText(millisUntilFinished / Constant.COUNTDOWNINTERVAL + "S");
               /* btnGetCode.setTextColor(getResources().getColor(R.color.six));
                btnGetCode.setBackgroundResource(R.drawable.yzm_beijing_wx);*/
            }

            @Override
            public void onFinish() {
                btnGetCode.setText("重新获取");
                btnGetCode.setClickable(true);
              /*  btnGetCode.setTextColor(getResources().getColor(R.color.white));
                btnGetCode.setBackgroundResource(R.drawable.yzm_beijing);*/
            }
        };
        countDownTimer.start();
    }

    private void handAddBankCard(AddBankCard addBankCard) {
        if (addBankCard.getCode() == 0) {
            ToastUtils.showShortToast("添加银行卡成功");
        } else {
            ToastUtils.showShortToast(addBankCard.getMessage());
        }
    }
}
