package com.jiuyuhulian.lotteryshop.function;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.utils.ToastUtils;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.api.ApiServices;
import com.jiuyuhulian.lotteryshop.config.Constant;
import com.jiuyuhulian.lotteryshop.model.ResponseCode;
import com.jiuyuhulian.lotteryshop.rest.RetrofitHttp;
import com.jiuyuhulian.lotteryshop.view.TopBar;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zhy.autolayout.AutoRelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TicketActiveActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.topbar)
    TopBar topbar;
    @BindView(R.id.scan_arl)
    AutoRelativeLayout scanArl;
    @BindView(R.id.hand_arl)
    AutoRelativeLayout handArl;

    private ApiServices mApiservice;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_active);
        ButterKnife.bind(this);
        initViews();
        initListener();
    }

    private void initListener() {
        handArl.setOnClickListener(this);
        scanArl.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.scan_arl:
                intent = new Intent(TicketActiveActivity.this, CaptureActivity.class);
                intent.putExtra("from",2);
                startActivityForResult(intent, 0);
                break;
            case R.id.hand_arl:
                intent = new Intent(TicketActiveActivity.this, TicketNumtActiveActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Log.d("active", "onActivityResult: "+result);

                    gotoScanActive(result);


                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("扫码失败")
                            .setContentText("解析二维码失败")
                            .setConfirmText("知道了")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    finish();
                                }
                            })
                            .show();
                }
            }


        }
    }

    private void gotoScanActive(String result) {
        mApiservice.activeTicket(result, Constant.SCAN_ACTIVE).subscribeOn(Schedulers.io())
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
