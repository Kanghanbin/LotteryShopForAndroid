package com.jiuyuhulian.lotteryshop.engine.exchange.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.utils.ToastUtils;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.api.ApiServices;
import com.jiuyuhulian.lotteryshop.config.Constant;
import com.jiuyuhulian.lotteryshop.model.ScanOrHandCash;
import com.jiuyuhulian.lotteryshop.rest.RetrofitHttp;
import com.jiuyuhulian.lotteryshop.view.ScanResultDialog;
import com.jiuyuhulian.lotteryshop.view.TopBar;
import com.zhy.autolayout.AutoLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class HandActivity extends AppCompatActivity {


    @BindView(R.id.topbar)
    TopBar topbar;
    @BindView(R.id.et_ticket_num)
    EditText etTicketNum;
    @BindView(R.id.et_ticket_pwd)
    EditText etTicketPwd;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.activity_ticke_numt_active)
    AutoLinearLayout activityTickeNumtActive;

    private String ticketNum,ticketPwd;
    private ApiServices apiServices;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout .content_hand);
        ButterKnife.bind(this);
        initViews();
        apiServices= RetrofitHttp.getApiServiceWithToken();
        mContext=this;
    }


    private void initViews() {
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

    @OnClick(R.id.btn_submit)
    public void submit(){
        ticketNum=etTicketNum.getText().toString().trim();
        ticketPwd=etTicketPwd.getText().toString().trim();
        apiServices.handCash(ticketNum,ticketPwd,"cash").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ScanOrHandCash>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShortToast(Constant.SERVER_ERROR);
                    }

                    @Override
                    public void onNext(ScanOrHandCash scanOrHandCash) {
                        handScanCrashResult(scanOrHandCash);
                    }
                });
    }

    private void handScanCrashResult(ScanOrHandCash scanOrHandCash) {

        ScanResultDialog dialog=new ScanResultDialog(mContext);

        dialog.setUserName(scanOrHandCash.getData().getUsername());
        dialog.setEncashDesc(scanOrHandCash.getData().getEncashDesc());
        dialog.setScanDesc(scanOrHandCash.getData().getScanDesc());
        dialog.setImgPrize(scanOrHandCash.getData().getCodeStatus());
        dialog.setIvOkText("好的");
        dialog.show();

    }


}
