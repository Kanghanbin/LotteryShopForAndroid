package com.jiuyuhulian.lotteryshop.function;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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

public class TransferOutMoneyActivity extends AppCompatActivity implements View.OnClickListener,TextWatcher {


    @BindView(R.id.topbar)
    TopBar topbar;
    @BindView(R.id.bank_img)
    ImageView bankImg;
    @BindView(R.id.bank_name)
    TextView bankName;
    @BindView(R.id.bank_samll_img)
    ImageView bankSamllImg;
    @BindView(R.id.tv_tixian)
    TextView tvTixian;
    @BindView(R.id.et_tixian)
    EditText etTixian;
    @BindView(R.id.subline)
    View subline;
    @BindView(R.id.tv_use)
    TextView tvUse;
    @BindView(R.id.btn_all)
    TextView btnAll;
    @BindView(R.id.bank_adress)
    EditText bankAdress;
    @BindView(R.id.btn_tixian)
    Button btnTixian;
    @BindView(R.id.activity_trannsfer_out_money)
    AutoLinearLayout activityTrannsferOutMoney;
    @BindView(R.id.all_select)
    AutoRelativeLayout allSelect;
    @BindView(R.id.iv_Delete)
    ImageView ivDelete;
    @BindView(R.id.rll_add_idcard)
    AutoRelativeLayout rllAddIdcard;


    private BankCardList.DataBean dataBean;
    private static int OUT_MONEY = 1;
    private double leftBalance;
    private List<BankCardList.DataBean> mbankCardList;
    private SPUtils spUtils;
    private ApiServices apiServices;
    private String shopId;
    private Context mContext;
    private String bankNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trannsfer_out_money);
        ButterKnife.bind(this);
        initViews();
        initListeners();
    }

    private void initListeners() {
        btnTixian.setOnClickListener(this);
        allSelect.setOnClickListener(this);
        etTixian.addTextChangedListener(this);
        ivDelete.setOnClickListener(this);
        btnAll.setOnClickListener(this);
        rllAddIdcard.setOnClickListener(this);
    }


    private void initViews() {
        mContext = this;
        apiServices = RetrofitHttp.getApiServiceWithToken();
        spUtils = new SPUtils("regist");
        leftBalance=Double.parseDouble(spUtils.getString("leftMoney"));
        tvUse.setText("可用余额:"+new DecimalFormat("0.00").format(leftBalance)+"元");
        shopId = spUtils.getString("shopid");
        topbar.setRightText("提现记录");
        topbar.setOnTopBarClickListener(new TopBar.OnTopBarClickListener() {
            @Override
            public void onTopBarRightClick(View v) {
                Intent intent = new Intent(TransferOutMoneyActivity.this, ChargeRecordsActivity.class);
                intent.putExtra("from", "transerout");
                startActivity(intent);
            }

            @Override
            public void onTopBarLeftClick(View v) {
                finish();
            }
        });
        getBankData();
    }
    @Override
    protected void onResume() {
        super.onResume();

    }
    private void getBankData() {

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
        Intent vertifyIntent=new Intent(this, VertifyMessageActivity.class);
        switch (v.getId()) {
            case R.id.btn_all:
                vertifyIntent.putExtra("amount",leftBalance);
                vertifyIntent.putExtra("dataBean",dataBean);
                startActivity(vertifyIntent);
                break;
            case R.id.btn_tixian:
                vertifyIntent.putExtra("amount",leftBalance);
                vertifyIntent.putExtra("dataBean",dataBean);
                startActivity(vertifyIntent);
                break;
            case R.id.all_select:
                Intent intent=new Intent(this, BankCardListActivity.class);
                intent.putExtra("transferMoney",OUT_MONEY);
                startActivityForResult(intent,OUT_MONEY);
                break;
            case R.id.rll_add_idcard:
                Intent intentAdd = new Intent(this,AddIdCard1Activity.class);
                intentAdd.putExtra("transferMoney", OUT_MONEY);
                startActivityForResult(intentAdd, OUT_MONEY);
                break;
            case R.id.iv_Delete:
                etTixian.setText("");
                ivDelete.setVisibility(View.GONE);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            dataBean = (BankCardList.DataBean) data.getSerializableExtra("bankCard");
            bankNum = dataBean.getCard_number();
            handleItemBackground(dataBean.getBank());
            bankName.setText(dataBean.getBank() + "(" +
                    bankNum.substring(bankNum.length() - 4, bankNum.length()) + ")");
        }
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        ivDelete.setVisibility(View.VISIBLE);
    }
}
