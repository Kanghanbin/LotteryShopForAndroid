package com.jiuyuhulian.lotteryshop.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.utils.SPUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.api.ApiServices;
import com.jiuyuhulian.lotteryshop.config.Constant;
import com.jiuyuhulian.lotteryshop.model.ResponseCode;
import com.jiuyuhulian.lotteryshop.rest.RetrofitHttp;
import com.jiuyuhulian.lotteryshop.utils.DialogUtil;
import com.jiuyuhulian.lotteryshop.view.TopBar;
import com.zhy.autolayout.AutoLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UpdateSaftyPwdActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.topbar)
    TopBar topbar;
    @BindView(R.id.btn_update)
    Button btnUpdate;
    @BindView(R.id.activity_update_pwd)
    AutoLinearLayout activityUpdatePwd;
    @BindView(R.id.btn_getCode)
    Button btnGetCode;
    @BindView(R.id.et_newPwd)
    EditText etNewPwd;
    @BindView(R.id.delete)
    ImageView delete;
    @BindView(R.id.delete_sure)
    ImageView deleteSure;
    @BindView(R.id.et_new_sure_pwd)
    EditText etNewSurePwd;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;

    private CountDownTimer countDownTimer;
    private Context mContext;
    private SPUtils sp;
    private ApiServices apiServices;
    private boolean checkResult;
    private String phone;
    private String code;
    private String newPwd;
    private String newSurePwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_saftypwd);
        ButterKnife.bind(this);
        apiServices = RetrofitHttp.getApiServiceWithToken();
        sp = new SPUtils("regist");
        mContext=this;
        initViews();
        initListeners();

    }

    private void initListeners() {
        btnUpdate.setOnClickListener(this);
        btnGetCode.setOnClickListener(this);
        delete.setOnClickListener(this);
        deleteSure.setOnClickListener(this);
        etNewSurePwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                deleteSure.setVisibility(View.VISIBLE);
            }
        });
        etNewPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                delete.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initViews() {
        topbar.setCenterText("修改财务安全码");
        topbar.setBackGroundColor(R.color.updatPwd_bg);
        topbar.setLeftSrc(R.drawable.xgmm_return);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete:
                etNewPwd.setText("");
                delete.setVisibility(View.GONE);
                break;
            case R.id.delete_sure:
                etNewSurePwd.setText("");
                deleteSure.setVisibility(View.GONE);
                break;
            case R.id.btn_getCode:
                phone = etPhone.getText().toString().trim();
                new DialogUtil(this).show();
                apiServices.sendCode("find-password",phone)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseCode>() {
                            @Override
                            public void onCompleted() {
                                new DialogUtil(UpdateSaftyPwdActivity.this).dismiss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                new DialogUtil(UpdateSaftyPwdActivity.this).dismiss();
                                ToastUtils.showShortToast(Constant.SERVER_ERROR);
                            }

                            @Override
                            public void onNext(ResponseCode sendCode) {
                                handleSendResult(sendCode);
                            }
                        });

                break;
            case R.id.btn_update:
                updateData();
                String starffId = sp.getString("staffid");
                String shopId = sp.getString("shopid");
                if (checkResult) {
                    new DialogUtil(mContext).show();
                    apiServices.changePayPwd(shopId,starffId,phone,code,newPwd)
                            .subscribeOn(Schedulers.io())
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
                                    hanleUpdatePwdResult(responseCode);
                                }
                            });

                }
        }
    }

    private void handleSendResult(ResponseCode sendCode) {
        if (sendCode.getCode()==0) {
            startCountDownTimer();
            ToastUtils.showShortToast("验证码发送成功");
        } else {
            ToastUtils.showShortToast(sendCode.getMessage());
        }
    }
    private void hanleUpdatePwdResult(ResponseCode responseCode) {
        if(responseCode.getCode()==0){
            ToastUtils.showShortToast("修改财务安全码成功");
        }else {
            ToastUtils.showShortToast(responseCode.getMessage());
        }
    }


    private void updateData() {
        phone = etPhone.getText().toString().trim();
        code = etCode.getText().toString().trim();
        newPwd = etNewPwd.getText().toString().trim();
        newSurePwd = etNewSurePwd.getText().toString().trim();
        if (phone.equals("")) {
            ToastUtils.showShortToast("请输入正确的手机号");
            checkResult = false;
            return;
        } else {
            checkResult = true;
        }

        if (code.equals("")) {
            ToastUtils.showShortToast("请输入正确的验证码");
            checkResult = false;
            return;
        } else {
            checkResult = true;
        }
        if (!newPwd.equals("") && !newSurePwd.equals("") && newSurePwd.equals(newPwd)) {
            checkResult = true;
        } else {
            ToastUtils.showShortToast("两次输入的密码必须一致且不为空");
            checkResult = false;
            return;
        }

    }


    private void startCountDownTimer() {
        countDownTimer = new CountDownTimer(Constant.MILLISINFUTURE, Constant.COUNTDOWNINTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                btnGetCode.setClickable(false);
                btnGetCode.setText(millisUntilFinished / Constant.COUNTDOWNINTERVAL + "S");
                btnGetCode.setBackgroundResource(R.drawable.xgcwmm_bj_wx);
            }
            @Override
            public void onFinish() {
                btnGetCode.setText("重新获取");
                btnGetCode.setClickable(true);
                btnGetCode.setBackgroundResource(R.drawable.hqyzm);
            }
        };
        countDownTimer.start();
    }
}
