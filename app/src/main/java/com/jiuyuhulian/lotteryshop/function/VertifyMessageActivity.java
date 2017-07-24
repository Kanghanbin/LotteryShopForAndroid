package com.jiuyuhulian.lotteryshop.function;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.config.Constant;
import com.jiuyuhulian.lotteryshop.model.BankCardList;
import com.jiuyuhulian.lotteryshop.view.TopBar;
import com.zhy.autolayout.AutoLinearLayout;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class VertifyMessageActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.topbar)
    TopBar topbar;
    @BindView(R.id.bank_img)
    ImageView bankImg;
    @BindView(R.id.bank_name)
    TextView bankName;
    @BindView(R.id.tv_tixian)
    TextView tvTixian;
    @BindView(R.id.tv_jine)
    TextView tvJine;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.id_card)
    EditText idCard;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.getCode)
    Button getCode;
    @BindView(R.id.btn_tixian_Sure)
    Button btnTixianSure;
    @BindView(R.id.activity_trannsfer_out_money)
    AutoLinearLayout activityTrannsferOutMoney;

    private CountDownTimer count;
    private double amount;
    private BankCardList.DataBean dataBean;
    private String bankNumSubstring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_message);
        ButterKnife.bind(this);
        initViews();
        initListeners();
    }

    private void initListeners() {
        getCode.setOnClickListener(this);
        btnTixianSure.setOnClickListener(this);
    }

    private void initViews() {
        dataBean= (BankCardList.DataBean) getIntent().getSerializableExtra("dataBean");
        handleItemImg(dataBean.getBank());
        bankNumSubstring=dataBean.getCard_number().substring(dataBean.getCard_number().length()-4,dataBean.getCard_number().length());
        bankName.setText(dataBean.getBank()+"("+bankNumSubstring+")");
        amount=getIntent().getDoubleExtra("amount",0);
        tvJine.setText("￥"+new DecimalFormat("0.00").format(amount));
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

    private void handleItemImg(String bank) {
        if (bank.equals("农业银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_abc_logo);
        } else if (bank.equals("中国银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_boc_logo);
        } else if (bank.equals("交通银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_bcm_logo);
        } else if (bank.equals("建设银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_ccb_logo);
        } else if (bank.equals("兴业银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_cib_logo);
        } else if (bank.equals("中信实业银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_citic_logo);
        } else if (bank.equals("招商银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_cmb_logo);
        } else if (bank.equals("民生银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_cmbc_logo);
        } else if (bank.equals("上海浦东发展银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_cpdb_logo);
        } else if (bank.equals("工商银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_icbc_logo);
        } else if (bank.equals("北京银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_bjbank_logo);
        } else if (bank.equals("北京农商银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_bjrcb_logo);
        } else if (bank.equals("中国光大银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_ceb_logo);
        } else if (bank.equals("宁波银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_nbbank_logo);
        } else if (bank.equals("深圳发展银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_sdb_logo);
        } else if (bank.equals("华夏银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_hxbank_logo);
        } else if (bank.equals("浦发银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_cpdb_logo);
        } else if (bank.equals("中国邮政储蓄银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_psbc_logo);
        } else if (bank.equals("杭州银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_hzcb_logo);
        } else if (bank.equals("南京银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_njcb_logo);
        } else if (bank.equals("广发银行")) {
            bankImg.setImageResource(R.drawable.mybankcard_gdb_logo);
        }else if (bank.equals("浙商银行")) {
            bankImg.setImageResource(R.drawable.zeshang);
        }else if (bank.equals("河北银行")) {
            bankImg.setImageResource(R.drawable.hebei);
        } else if (bank.equals("潍坊银行")) {
            bankImg.setImageResource(R.drawable.weifang);
        } else if (bank.equals("温州银行")) {
            bankImg.setImageResource(R.drawable.wenzong);
        } else if (bank.equals("青岛银行")) {
            bankImg.setImageResource(R.drawable.qingdao);
        } else if (bank.equals("星展银行")) {
            bankImg.setImageResource(R.drawable.xingzhan);
        }else if (bank.equals("上海银行")) {
            bankImg.setImageResource(R.drawable.shanghai);
        }else if (bank.equals("平安银行")) {
            bankImg.setImageResource(R.drawable.pingan);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.getCode:
                count=new CountDownTimer(Constant.MILLISINFUTURE, Constant.COUNTDOWNINTERVAL) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        getCode.setClickable(false);
                        getCode.setText(millisUntilFinished/ Constant.COUNTDOWNINTERVAL+"S");;
                        getCode.setBackgroundResource(R.drawable.yzxx_fsyzm_wx);
                    }

                    @Override
                    public void onFinish() {
                        getCode.setText("重新获取");
                        getCode.setClickable(true);
                        getCode.setBackgroundResource(R.drawable.yzxx_fsyzm);
                    }
                };
                count.start();
                break;
            case R.id.btn_tixian_Sure:
                new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("提现申请成功")
                        .setContentText("您的提现申请已经受理，您可以通过提现记录查看申请状态")
                        .setConfirmText("知道了")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                finish();
                            }
                        })
                        .show();
                break;
        }
    }
}
