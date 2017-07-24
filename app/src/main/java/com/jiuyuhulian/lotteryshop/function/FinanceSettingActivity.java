package com.jiuyuhulian.lotteryshop.function;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zhy.autolayout.AutoLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.view.TopBar;

public class FinanceSettingActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.topbar)
    TopBar topbar;
    @BindView(R.id.all_idcard)
    AutoLinearLayout allIdcard;
    @BindView(R.id.all_dj)
    AutoLinearLayout allDj;
    @BindView(R.id.all_adress)
    AutoLinearLayout allAdress;
    @BindView(R.id.activity_finace_setting)
    AutoLinearLayout activityFinaceSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finace_setting);
        ButterKnife.bind(this);
        initViews();
        initListener();
    }

    private void initListener() {
        allAdress.setOnClickListener(this);
        allDj.setOnClickListener(this);
        allIdcard.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.all_adress:
                startActivity(new Intent(FinanceSettingActivity.this,AddressListActivity.class));
                break;
            case R.id.all_dj:
                startActivity(new Intent(FinanceSettingActivity.this,DuijiangLimitActivity.class));
                break;
            case R.id.all_idcard:
                startActivity(new Intent(FinanceSettingActivity.this,BankCardListActivity.class));
                break;
        }
    }
}
