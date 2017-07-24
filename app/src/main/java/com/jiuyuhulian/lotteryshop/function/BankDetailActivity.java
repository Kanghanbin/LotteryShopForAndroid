package com.jiuyuhulian.lotteryshop.function;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.api.ApiServices;
import com.jiuyuhulian.lotteryshop.config.Constant;
import com.jiuyuhulian.lotteryshop.model.BankCardList;
import com.jiuyuhulian.lotteryshop.model.ResponseCode;
import com.jiuyuhulian.lotteryshop.rest.RetrofitHttp;
import com.jiuyuhulian.lotteryshop.utils.DialogUtil;
import com.jiuyuhulian.lotteryshop.view.ActionSheet;
import com.jiuyuhulian.lotteryshop.view.TopBar;
import com.zhy.autolayout.AutoLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.graphics.drawable.GradientDrawable.Orientation.TOP_BOTTOM;

public class BankDetailActivity extends AppCompatActivity {

    @BindView(R.id.limitView)
    AutoLinearLayout limitView;
    @BindView(R.id.stars)
    TextView stars;
    private PopupWindow popupWindow;

    @BindView(R.id.topbar)
    TopBar topbar;
    @BindView(R.id.bankImg)
    ImageView bankImg;
    @BindView(R.id.bankName)
    TextView bankName;
    @BindView(R.id.bankNum)
    TextView bankNum;
    @BindView(R.id.singe)
    TextView singe;
    @BindView(R.id.day)
    TextView day;


    private ActionSheet actionSheet;
    private BankCardList.DataBean dataBean;
    private String bankname, banNum,bankId;
    private GradientDrawable gradientDrawable;
    private ApiServices apiServices;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idcard_detail);
        ButterKnife.bind(this);
        initViews();

    }

    private void initViews() {
        mContext=this;
        apiServices= RetrofitHttp.getApiServiceWithToken();
        dataBean = (BankCardList.DataBean) getIntent().getSerializableExtra("bank");
        bankname = dataBean.getBank();
        banNum=dataBean.getCard_number();
        bankId=dataBean.getBank_id();
        bankName.setText(bankname+dataBean.getCard_type());
        bankNum.setText(banNum.substring(banNum.length()-4));
        handleItemBackground();
        topbar.setOnTopBarClickListener(new TopBar.OnTopBarClickListener() {
            @Override
            public void onTopBarRightClick(View v) {
                if (actionSheet == null) {
                    actionSheet = new ActionSheet(BankDetailActivity.this);
                    actionSheet.addMenuItem("解除绑定");
                }
                actionSheet.show();

                actionSheet.setMenuListener(new ActionSheet.MenuListener() {
                    @Override
                    public void onItemSelected(int position, String item) {
                        new DialogUtil(mContext).show();
                        apiServices.unbindBankCard(bankId).subscribeOn(Schedulers.io())
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
                                        handUnbindBankcard(responseCode);
                                    }
                                });
                    }

                    @Override
                    public void onCancel() {

                    }
                });

            }

            @Override
            public void onTopBarLeftClick(View v) {
                finish();
            }
        });
    }

    private void handUnbindBankcard(ResponseCode responseCode) {
        if(responseCode.getCode()==0){
            ToastUtils.showShortToast("解绑成功");
        }else {
            ToastUtils.showShortToast(responseCode.getMessage());
        }
    }


    private void handleItemBackground() {

        if (bankname.equals("农业银行")) {
            createBackground(limitView, "#089db7", "#1cb38f");
            bankImg.setImageResource(R.drawable.mybankcard_abc_logo);
        } else if (bankname.equals("中国银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_boc_logo);
            createBackground(limitView, "#e9507d", "#e75c64");
        } else if (bankname.equals("交通银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_bcm_logo);
            createBackground(limitView, "#0f2d4e", "#1459a5");
        } else if (bankname.equals("建设银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_ccb_logo);
            createBackground(limitView, "#3d5eaa", "#4f83be");
        } else if (bankname.equals("兴业银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_cib_logo);
            createBackground(limitView, "#3d5eaa", "#4f83be");
        } else if (bankname.equals("中信实业银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_citic_logo);
            createBackground(limitView, "#e9507d", "#e75c64");
        } else if (bankname.equals("招商银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_cmb_logo);
            createBackground(limitView, "#e9507d", "#e75c64");
        } else if (bankname.equals("民生银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_cmbc_logo);
            createBackground(limitView, "#0e6baf", "#269e5b");
        } else if (bankname.equals("上海浦东发展银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_cpdb_logo);
            createBackground(limitView, "#164658", "#e78a0c");
        } else if (bankname.equals("工商银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_icbc_logo);
            createBackground(limitView, "#e9507d", "#e75c64");
        } else if (bankname.equals("北京银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_bjbank_logo);
            createBackground(limitView, "#e9507d", "#e75c64");
        } else if (bankname.equals("北京农商银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_bjrcb_logo);
            createBackground(limitView, "#e9507d", "#e75c64");
        } else if (bankname.equals("中国光大银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_ceb_logo);
            createBackground(limitView, "#6a1686", "#e4a800");
        } else if (bankname.equals("宁波银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_nbbank_logo);
            createBackground(limitView, "#d87309", "#fde96e");
        } else if (bankname.equals("深圳发展银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_sdb_logo);
            createBackground(limitView, "#0973a4", "#00a0e9");
        } else if (bankname.equals("华夏银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_hxbank_logo);
            createBackground(limitView, "#e9507d", "#e75c64");
        } else if (bankname.equals("浦发银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_cpdb_logo);
            createBackground(limitView, "#164658", "#e78a0c");
        } else if (bankname.equals("中国邮政储蓄银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_psbc_logo);
            createBackground(limitView, "#009944", "#3bc779");
        } else if (bankname.equals("杭州银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_hzcb_logo);
            createBackground(limitView, "#006dbb", "#6bbd48");
        } else if (bankname.equals("南京银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_njcb_logo);
            createBackground(limitView, "#e9507d", "#e75c64");
        } else if (bankname.equals("广发银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_gdb_logo);
            createBackground(limitView, "#e9507d", "#e75c64");
        }
    }

    private void createBackground(View layout, String first, String second) {
        // 外部圆角矩形的圆角圆半径，上面俩个角是圆
        int firstcolor = Color.parseColor(first);
        int secondcolor = Color.parseColor(second);
        bankName.setTextColor(secondcolor);
        stars.setTextColor(secondcolor);
        bankNum.setTextColor(firstcolor);
        gradientDrawable = new GradientDrawable(TOP_BOTTOM,
                new int[]{firstcolor, secondcolor});
        gradientDrawable.setCornerRadius(25);
        layout.setBackgroundDrawable(gradientDrawable);
    }


}
