package com.jiuyuhulian.lotteryshop.function;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.utils.ToastUtils;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.api.ApiServices;
import com.jiuyuhulian.lotteryshop.config.Constant;
import com.jiuyuhulian.lotteryshop.model.ResponseCode;
import com.jiuyuhulian.lotteryshop.rest.RetrofitHttp;
import com.jiuyuhulian.lotteryshop.view.TopBar;
import com.zhy.autolayout.AutoLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TicketNumtActiveActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    TopBar topbar;
    @BindView(R.id.et_ticket_num)
    EditText etTicketNum;
    @BindView(R.id.btn_Active)
    Button btnActive;
    @BindView(R.id.activity_ticke_numt_active)
    AutoLinearLayout activityTickeNumtActive;

    private ApiServices mApiservice;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_numt_active);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        mContext=this;
        mApiservice= RetrofitHttp.getApiServiceWithToken();
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
    @OnClick(R.id.btn_Active)
    public void handAcitive(){
        mApiservice.activeTicket(etTicketNum.getText().toString().trim(), Constant.HAND_ACTIVE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseCode>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseCode responseCode) {
                        if(responseCode.getCode()==0){
                            new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("激活成功")
                                    .setConfirmText("知道了")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismissWithAnimation();
                                            finish();
                                        }
                                    })
                                    .show();
                        }else {
                            ToastUtils.showShortToast(responseCode.getMessage());
                        }

                    }
                });
    }

}
