package com.jiuyuhulian.lotteryshop.function;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.SPUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.api.ApiServices;
import com.jiuyuhulian.lotteryshop.config.Constant;
import com.jiuyuhulian.lotteryshop.model.BankCardList;
import com.jiuyuhulian.lotteryshop.model.PayBankCard;
import com.jiuyuhulian.lotteryshop.rest.RetrofitHttp;
import com.jiuyuhulian.lotteryshop.utils.DialogUtil;
import com.jiuyuhulian.lotteryshop.view.TopBar;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BankChargeActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.topbar)
    TopBar topbar;
    @BindView(R.id.bank_img)
    ImageView bankImg;
    @BindView(R.id.bank_name)
    TextView bankName;
    @BindView(R.id.bank_samll_img)
    ImageView bankSamllImg;
    @BindView(R.id.all_select)
    AutoRelativeLayout allSelect;
    @BindView(R.id.cvvCode)
    EditText cvvCode;
    @BindView(R.id.validPeriod)
    EditText validPeriod;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.arl_phone)
    AutoRelativeLayout arlPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.getCode)
    Button getCode;
    @BindView(R.id.btn_charge_Sure)
    Button btnChargeSure;
    @BindView(R.id.activity_bank_charge)
    AutoLinearLayout activityBankCharge;
    @BindView(R.id.tv_charge)
    TextView tvCharge;
    @BindView(R.id.rll_add_idcard)
    AutoRelativeLayout rllAddIdcard;

    private CountDownTimer count;

    private BankCardList.DataBean dataBean;
    private List<BankCardList.DataBean> mbankCardList;
    private SPUtils spUtils;
    private ApiServices apiServices;
    private String shopId;
    private Context mContext;
    private String bankNum;

    private String cvv, expireDate, cardPhone;

    private static int IN_MONEY = 2;
    private double money;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_charge);
        ButterKnife.bind(this);
        initViews();
        initListeners();
    }

    private void initViews() {
        mContext = this;
        apiServices = RetrofitHttp.getApiServiceWithToken();
        spUtils = new SPUtils("regist");
        shopId = spUtils.getString("shopid");
        money = getIntent().getDoubleExtra("charge", 0);
        tvCharge.setText("正在为该账户充值" + new DecimalFormat("0.00").format(money) + "元");
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
        getCode.setOnClickListener(this);
        btnChargeSure.setOnClickListener(this);
        allSelect.setOnClickListener(this);
        rllAddIdcard.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getNetWorkBankData();
    }

    private void getNetWorkBankData() {

        new DialogUtil(mContext).show();
        apiServices.getBankCardList(shopId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BankCardList>() {
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
                    public void onNext(BankCardList bankCardList) {
                        handBankCardList(bankCardList);
                    }
                });
    }

    private void handleItemBackground(String bank) {
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
        } else if (bank.equals("浙商银行")) {
            bankImg.setImageResource(R.drawable.zeshang);

        } else if (bank.equals("河北银行")) {
            bankImg.setImageResource(R.drawable.hebei);

        } else if (bank.equals("潍坊银行")) {
            bankImg.setImageResource(R.drawable.weifang);

        } else if (bank.equals("温州银行")) {
            bankImg.setImageResource(R.drawable.wenzong);

        } else if (bank.equals("青岛银行")) {
            bankImg.setImageResource(R.drawable.qingdao);

        } else if (bank.equals("星展银行")) {
            bankImg.setImageResource(R.drawable.xingzhan);

        } else if (bank.equals("上海银行")) {
            bankImg.setImageResource(R.drawable.shanghai);
        } else if (bank.equals("平安银行")) {
            bankImg.setImageResource(R.drawable.pingan);
        }
    }

    private void handBankCardList(BankCardList bankCardList) {
        if (bankCardList.getCode() == 0) {
            mbankCardList = bankCardList.getData();
            rllAddIdcard.setVisibility(View.GONE);
            allSelect.setVisibility(View.VISIBLE);
            dataBean = mbankCardList.get(0);
            bankNum = dataBean.getCard_number();
            bankName.setText(dataBean.getBank() + "(" +
                    bankNum.substring(bankNum.length() - 4, bankNum.length()) + ")");
            handleItemBackground(dataBean.getBank());

        }else if(bankCardList.getCode() == 30) {
            rllAddIdcard.setVisibility(View.VISIBLE);
            allSelect.setVisibility(View.GONE);
        }else {
            ToastUtils.showShortToast(bankCardList.getMessage());
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getCode:
                count = new CountDownTimer(Constant.MILLISINFUTURE, Constant.COUNTDOWNINTERVAL) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        getCode.setClickable(false);
                        getCode.setText(millisUntilFinished / Constant.COUNTDOWNINTERVAL + "S");
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
            case R.id.btn_charge_Sure:
                cvv = cvvCode.getText().toString().trim();
                cardPhone = etPhone.getText().toString().trim();
                expireDate = validPeriod.getText().toString().trim();
                apiServices.payBankcard(cvv, money, dataBean.getBank(), expireDate, cardPhone, dataBean.getCard_number())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<PayBankCard>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(PayBankCard payBankCard) {

                            }
                        });
                break;
            case R.id.all_select:
                Intent intent = new Intent(this, BankCardListActivity.class);
                intent.putExtra("transferMoney", IN_MONEY);
                startActivityForResult(intent, IN_MONEY);
                break;
            case R.id.rll_add_idcard:
                Intent intentAdd = new Intent(this,AddIdCard1Activity.class);
                intentAdd.putExtra("transferMoney", IN_MONEY);
                startActivityForResult(intentAdd, IN_MONEY);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == 2) {
            dataBean = (BankCardList.DataBean) data.getSerializableExtra("bankCard");
            bankNum = dataBean.getCard_number();
            handleItemBackground(dataBean.getBank());
            bankName.setText(dataBean.getBank() + "(" +
                    bankNum.substring(bankNum.length() - 4, bankNum.length()) + ")");
        }
    }
}
