package com.jiuyuhulian.lotteryshop.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.config.Constant;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by khb on 2017/5/2.
 */

public class ScanResultDialog extends Dialog {

    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_scanDesc)
    TextView tvScanDesc;
    @BindView(R.id.tv_encashDesc)
    TextView tvEncashDesc;
    @BindView(R.id.iv_prize)
    ImageView ivPrize;
    @BindView(R.id.iv_ok)
    Button ivOk;
    /**
     * 加载的布局文件
     */
    private View rootView;
    /**
     * 含有Window的Content
     */
    private Context mContext;
    private static final int REQUEST_CODE = 0;

    public ScanResultDialog(Context context) {
        //自定义style主要去掉标题，标题将在setCustomView中自定义设置
        super(context, R.style.ActionSheetDialog);
        mContext = context;
        initView();
    }

    public ScanResultDialog(Context context, int themeResId, Context mContext) {
        super(context, R.style.ActionSheetDialog);
        this.mContext = mContext;
        initView();
    }

    public ScanResultDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.setCancelable(cancelable);
        this.setOnCancelListener(cancelListener);
        initView();
    }

    /**
     * inflate布局，加载到此布局
     */
    private void initView() {
        rootView = LayoutInflater.from(getContext()).inflate(R.layout.scan_result, null);
        super.setContentView(rootView);
        ButterKnife.bind(this);
    }

    public void setUserName(String name) {
        tvUserName.setText(name);

    }

    public void setScanDesc(String name) {
        tvScanDesc.setText(name);
    }

    public void setEncashDesc(String name) {
        tvEncashDesc.setText(name);
    }

    public void setImgPrize(int img) {
        if (img == 1) {
            //未中奖
            ivPrize.setImageResource(R.drawable.chatuu);
        } else if (img == 2) {
            //已中奖
            ivPrize.setImageResource(R.drawable.chatu);
        } else if (img == 4) {
            //已开奖
            ivPrize.setImageResource(R.drawable.chatuuu);
        } else {
            ivPrize.setImageResource(R.drawable.chatuuuu);
        }
    }

    public void setIvOkText(String text){
        ivOk.setText(text);
    }
    @OnClick(R.id.iv_close)
    public void closeDialog() {
        dismiss();
    }

    @OnClick(R.id.iv_ok)
    public void continueScan() {
        dismiss();
        if (ivOk.getText().toString().trim().equals("继续"))
            EventBus.getDefault().post(Constant.CONTINUE_SCAN);


    }


}
