package com.jiuyuhulian.lotteryshop.engine.exchange;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.utils.ToastUtils;
import com.jiuyuhulian.lotteryshop.BaseFragment;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.api.ApiServices;
import com.jiuyuhulian.lotteryshop.config.Constant;
import com.jiuyuhulian.lotteryshop.engine.exchange.activity.HandActivity;
import com.jiuyuhulian.lotteryshop.model.ScanOrHandCash;
import com.jiuyuhulian.lotteryshop.rest.RetrofitHttp;
import com.jiuyuhulian.lotteryshop.view.ScanResultDialog;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Admin on 2017/3/7.
 */

public class ExchangeFragemnet extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.scan_btn)
    AutoLinearLayout scanBtn;
    @BindView(R.id.hand_btn)
    AutoLinearLayout handBtn;
    @BindView(R.id.iv_smdj)
    ImageView ivSmdj;
    @BindView(R.id.iv_sddj)
    ImageView ivSddj;



    View view;
    private ApiServices apiServices;
    private SweetAlertDialog sweet;
    private Context context;

    private static final int REQUEST_CODE = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_exchange, null);
        ButterKnife.bind(this, view);
        apiServices = RetrofitHttp.getApiServiceWithToken();
        context = getActivity();
        initListeners();
        return view;

    }

    private void initListeners() {
        handBtn.setOnClickListener(this);
        scanBtn.setOnClickListener(this);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            /* 处理二维码扫描结果 */
            if (requestCode == REQUEST_CODE) {
                //处理扫描结果（在界面上显示）
                if (null != data) {
                    Bundle bundle = data.getExtras();
                    if (bundle == null) {
                        return;
                    }
                    if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                        String result = bundle.getString(CodeUtils.RESULT_STRING);

                        getScanResult(result);
                        Log.d("result", result);
                        //ToastUtils.showShortToast("解析结果:" + result);
                    } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                        ToastUtils.showShortToast("解析二维码失败");
                    }
                }
            }

        }
    }

    private void getScanResult(String result) {
        apiServices.scanCash(result, Constant.HTTPURL_CRASH).subscribeOn(Schedulers.io())
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

        ScanResultDialog dialog=new ScanResultDialog(context);

        dialog.setUserName(scanOrHandCash.getData().getUsername());
        dialog.setEncashDesc(scanOrHandCash.getData().getEncashDesc());
        dialog.setScanDesc(scanOrHandCash.getData().getScanDesc());
        dialog.setImgPrize(scanOrHandCash.getData().getCodeStatus());
        dialog.show();

    }
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    @Override
    public void onResume() {
        super.onResume();
        ivSmdj.setImageResource(R.drawable.dj_smdj);
        ivSddj.setImageResource(R.drawable.dj_sddm);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String text ) {
        if(text.equals(Constant.CONTINUE_SCAN)){
            Intent openCameraIntent = new Intent(getActivity(), CaptureActivity.class);
            openCameraIntent.putExtra("from", 1);
            startActivityForResult(openCameraIntent, REQUEST_CODE);
        }

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.hand_btn:
                ivSddj.setImageResource(R.drawable.dj_sddm_xz);
                intent = new Intent(getActivity(), HandActivity.class);
                startActivity(intent);
                break;
            case R.id.scan_btn:
                //打开扫描界面扫描条形码或二维码
                ivSmdj.setImageResource(R.drawable.dj_smdj_xz);
                Intent openCameraIntent = new Intent(getActivity(), CaptureActivity.class);
                openCameraIntent.putExtra("from", 1);
                startActivityForResult(openCameraIntent, REQUEST_CODE);
                break;
        }
    }


}
