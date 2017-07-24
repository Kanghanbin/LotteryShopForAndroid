package com.jiuyuhulian.lotteryshop.function;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.utils.SPUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.api.ApiServices;
import com.jiuyuhulian.lotteryshop.config.Constant;
import com.jiuyuhulian.lotteryshop.model.BankCardList;
import com.jiuyuhulian.lotteryshop.rest.RetrofitHttp;
import com.jiuyuhulian.lotteryshop.utils.DialogUtil;
import com.jiuyuhulian.lotteryshop.view.TopBar;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.graphics.drawable.GradientDrawable.Orientation.TOP_BOTTOM;

public class BankCardListActivity extends AppCompatActivity  {

    @BindView(R.id.topbar)
    TopBar topbar;

    @BindView(R.id.activity_bind_idcard)
    AutoLinearLayout activityBindIdcard;
    @BindView(R.id.rv_bank)
    RecyclerView rvBank;


    private List<BankCardList.DataBean> mbankCardList;
    private SPUtils spUtils;
    private ApiServices apiServices;
    private String shopId;
    private Context mContext;
    private int transfer;
    private static int IN = 0;
    private static int OUT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_idcard);
        ButterKnife.bind(this);
        transfer = getIntent().getIntExtra("transferMoney",-1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initViews();
    }
    private void initViews() {
        mContext = this;
        apiServices = RetrofitHttp.getApiServiceWithToken();
        spUtils = new SPUtils("regist");
        shopId = spUtils.getString("shopid");
        getBankData();
        topbar.setOnTopBarClickListener(new TopBar.OnTopBarClickListener() {
            @Override
            public void onTopBarRightClick(View v) {
                startActivity(new Intent(mContext, AddIdCard1Activity.class));
            }

            @Override
            public void onTopBarLeftClick(View v) {
                finish();
            }
        });

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

    private void handBankCardList(BankCardList bankCardList) {
        if (bankCardList.getCode() == 0) {
            mbankCardList = bankCardList.getData();
            updateUI();
        } else {

        }
    }

    private void updateUI() {

        rvBank.setLayoutManager(new LinearLayoutManager(this));
        rvBank.setAdapter(new CommonAdapter<BankCardList.DataBean>(this, R.layout.bind_idcard_adapter_item, mbankCardList) {

            String bankname;
            ImageView imageView;
            View cardbg;
            GradientDrawable gradientDrawable;

            @Override
            protected void convert(ViewHolder holder, final BankCardList.DataBean bankCardEntity, int position) {
                // holder.setImageDrawable(R.id.bank_img,getResources().getDrawable(R.drawable.yhk_logo))\
                bankname = bankCardEntity.getBank();
                ;
                holder.setText(R.id.bank_kind, bankCardEntity.getCard_type());
                holder.setText(R.id.bank_property, bankCardEntity.getBank());
                String banNum = bankCardEntity.getCard_number();
                holder.setText(R.id.bank_num, banNum.substring(banNum.length() - 4, banNum.length()));
                cardbg = holder.getView(R.id.bankcardView);
                handleItemBackground(holder);

                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        if (transfer==OUT) {
                            intent.putExtra("bankCard", bankCardEntity);
                            setResult(OUT, intent);
                            finish();
                        } else if (transfer==IN) {
                            intent.putExtra("bankCard", bankCardEntity);
                            setResult(IN, intent);
                            finish();
                        } else {
                            intent.setClass(BankCardListActivity.this, BankDetailActivity.class);
                            intent.putExtra("bank", bankCardEntity);
                            startActivity(intent);
                        }

                    }
                });

            }

            private void handleItemBackground(ViewHolder holder) {
                imageView = holder.getView(R.id.bank_img);
                if (bankname.equals("农业银行")) {
                    createBackground(cardbg, "#089db7", "#1cb38f");
                    imageView.setImageResource(R.drawable.mybankcard_abc_logo);
                } else if (bankname.equals("中国银行")) {
                    imageView.setImageResource(R.drawable.mybankcard_boc_logo);
                    createBackground(cardbg, "#e9507d", "#e75c64");
                } else if (bankname.equals("交通银行")) {
                    imageView.setImageResource(R.drawable.mybankcard_bcm_logo);
                    createBackground(cardbg, "#0f2d4e", "#1459a5");
                } else if (bankname.equals("建设银行")) {
                    imageView.setImageResource(R.drawable.mybankcard_ccb_logo);
                    createBackground(cardbg, "#3d5eaa", "#4f83be");
                } else if (bankname.equals("兴业银行")) {
                    imageView.setImageResource(R.drawable.mybankcard_cib_logo);
                    createBackground(cardbg, "#3d5eaa", "#4f83be");
                } else if (bankname.equals("中信实业银行")) {
                    imageView.setImageResource(R.drawable.mybankcard_citic_logo);
                    createBackground(cardbg, "#e9507d", "#e75c64");
                } else if (bankname.equals("招商银行")) {
                    imageView.setImageResource(R.drawable.mybankcard_cmb_logo);
                    createBackground(cardbg, "#e9507d", "#e75c64");
                } else if (bankname.equals("民生银行")) {
                    imageView.setImageResource(R.drawable.mybankcard_cmbc_logo);
                    createBackground(cardbg, "#0e6baf", "#269e5b");
                } else if (bankname.equals("上海浦东发展银行")) {
                    imageView.setImageResource(R.drawable.mybankcard_cpdb_logo);
                    createBackground(cardbg, "#164658", "#e78a0c");
                } else if (bankname.equals("工商银行")) {
                    imageView.setImageResource(R.drawable.mybankcard_icbc_logo);
                    createBackground(cardbg, "#e9507d", "#e75c64");
                } else if (bankname.equals("北京银行")) {
                    imageView.setImageResource(R.drawable.mybankcard_bjbank_logo);
                    createBackground(cardbg, "#e9507d", "#e75c64");
                } else if (bankname.equals("北京农商银行")) {
                    imageView.setImageResource(R.drawable.mybankcard_bjrcb_logo);
                    createBackground(cardbg, "#e9507d", "#e75c64");
                } else if (bankname.equals("中国光大银行")) {
                    imageView.setImageResource(R.drawable.mybankcard_ceb_logo);
                    createBackground(cardbg, "#6a1686", "#e4a800");
                } else if (bankname.equals("宁波银行")) {
                    imageView.setImageResource(R.drawable.mybankcard_nbbank_logo);
                    createBackground(cardbg, "#d87309", "#fde96e");
                } else if (bankname.equals("深圳发展银行")) {
                    imageView.setImageResource(R.drawable.mybankcard_sdb_logo);
                    createBackground(cardbg, "#0973a4", "#00a0e9");
                } else if (bankname.equals("华夏银行")) {
                    imageView.setImageResource(R.drawable.mybankcard_hxbank_logo);
                    createBackground(cardbg, "#e9507d", "#e75c64");
                } else if (bankname.equals("浦发银行")) {
                    imageView.setImageResource(R.drawable.mybankcard_cpdb_logo);
                    createBackground(cardbg, "#164658", "#e78a0c");
                } else if (bankname.equals("中国邮政储蓄银行")) {
                    imageView.setImageResource(R.drawable.mybankcard_psbc_logo);
                    createBackground(cardbg, "#009944", "#3bc779");
                } else if (bankname.equals("杭州银行")) {
                    imageView.setImageResource(R.drawable.mybankcard_hzcb_logo);
                    createBackground(cardbg, "#006dbb", "#6bbd48");
                } else if (bankname.equals("南京银行")) {
                    imageView.setImageResource(R.drawable.mybankcard_njcb_logo);
                    createBackground(cardbg, "#e9507d", "#e75c64");
                } else if (bankname.equals("广发银行")) {
                    imageView.setImageResource(R.drawable.mybankcard_gdb_logo);
                    createBackground(cardbg, "#e9507d", "#e75c64");
                }else if (bankname.equals("浙商银行")) {
                    imageView.setImageResource(R.drawable.zeshang);
                    createBackground(cardbg, "#e60012", "#ff3040");
                }else if (bankname.equals("河北银行")) {
                    imageView.setImageResource(R.drawable.hebei);
                    createBackground(cardbg, "#055b74", "#0983a7");
                } else if (bankname.equals("潍坊银行")) {
                    imageView.setImageResource(R.drawable.weifang);
                    createBackground(cardbg, "#e60012", "#ff3040");
                } else if (bankname.equals("温州银行")) {
                    imageView.setImageResource(R.drawable.wenzong);
                    createBackground(cardbg, "#f6ab00", "#ffd237");
                } else if (bankname.equals("青岛银行")) {
                    imageView.setImageResource(R.drawable.qingdao);
                    createBackground(cardbg, "#a73d47", "#d6535e");
                } else if (bankname.equals("星展银行")) {
                    imageView.setImageResource(R.drawable.xingzhan);
                    createBackground(cardbg, "#e6001f", "#ff333d");
                }else if (bankname.equals("上海银行")) {
                    imageView.setImageResource(R.drawable.shanghai);
                    createBackground(cardbg, "#293c8f", "#1061ce");
                }else if (bankname.equals("平安银行")) {
                    imageView.setImageResource(R.drawable.pingan);
                    createBackground(cardbg, "#ff3204", "#ff7316");
                }
            }

            private void createBackground(View layout, String first, String second) {
                // 外部圆角矩形的圆角圆半径，上面俩个角是圆
                int firstcolor = Color.parseColor(first);
                int secondcolor = Color.parseColor(second);
                gradientDrawable = new GradientDrawable(TOP_BOTTOM,
                        new int[]{firstcolor, secondcolor});
                gradientDrawable.setCornerRadius(25);
                layout.setBackgroundDrawable(gradientDrawable);
            }

        });
    }



}
