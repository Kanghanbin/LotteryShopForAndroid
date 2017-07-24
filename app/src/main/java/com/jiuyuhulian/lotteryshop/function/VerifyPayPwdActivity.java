package com.jiuyuhulian.lotteryshop.function;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.utils.SPUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.api.ApiServices;
import com.jiuyuhulian.lotteryshop.config.Constant;
import com.jiuyuhulian.lotteryshop.model.ResponseCode;
import com.jiuyuhulian.lotteryshop.rest.RetrofitHttp;
import com.jiuyuhulian.lotteryshop.utils.DialogUtil;
import com.jiuyuhulian.lotteryshop.view.TopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class VerifyPayPwdActivity extends AppCompatActivity{

    @BindView(R.id.topbar)
    TopBar topbar;
    @BindView(R.id.et_saftyPwd)
    EditText etSaftyPwd;

    private SPUtils spUtils;
    private ApiServices apiServices;
    private String shopId,payPwd;
    private Context mContext;
    private static int VERFYPAYPWD = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_pay);
        ButterKnife.bind(this);
        initViews();
    }



    private void initViews() {
        apiServices = RetrofitHttp.getApiServiceWithToken();
        spUtils = new SPUtils("regist");
        shopId = spUtils.getString("shopid");
        mContext = this;
        topbar.setOnTopBarClickListener(new TopBar.OnTopBarClickListener() {
            @Override
            public void onTopBarRightClick(View v) {

            }

            @Override
            public void onTopBarLeftClick(View v) {
                finish();
            }
        });
        etSaftyPwd.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                payPwd=etSaftyPwd.getText().toString().trim();
                if(payPwd!=null&&!payPwd.equals("")){
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        new DialogUtil(mContext).show();
                        apiServices.verifyPay(shopId,payPwd).subscribeOn(Schedulers.io())
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
                                        handPayPwdResult(responseCode);
                                    }
                                });

                    }
                }else {
                    ToastUtils.showShortToast("财务安全码不能为空");
                }
                return false;
            }
        });
    }

    private void handPayPwdResult(ResponseCode responseCode) {
        if(responseCode.getCode()==0){
            ToastUtils.showShortToast("验证成功");
            setResult(VERFYPAYPWD);
            finish();

        }else {
            ToastUtils.showShortToast(responseCode.getMessage());
        }
    }



}
