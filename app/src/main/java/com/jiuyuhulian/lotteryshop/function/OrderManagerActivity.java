package com.jiuyuhulian.lotteryshop.function;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.view.TopBar;

public class OrderManagerActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    TopBar topbar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.activity_order_manager)
    LinearLayout activityOrderManager;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private OrderPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_manager);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        topbar.setCenterText("我的订单");
        topbar.setOnTopBarClickListener(new TopBar.OnTopBarClickListener() {
            @Override
            public void onTopBarRightClick(View v) {
                startActivity(new Intent(OrderManagerActivity.this,OrderSearchActivity.class));
            }

            @Override
            public void onTopBarLeftClick(View v) {
                finish();
            }
        });
        //添加页卡标题
        mTitleList.add("全部");
        mTitleList.add("待付款");
        mTitleList.add("待发货");
        mTitleList.add("待收货");

        tabs.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        tabs.addTab(tabs.newTab().setText(mTitleList.get(0)));//添加tab选项卡
        tabs.addTab(tabs.newTab().setText(mTitleList.get(1)));//添加tab选项卡
        tabs.addTab(tabs.newTab().setText(mTitleList.get(2)));//添加tab选项卡
        tabs.addTab(tabs.newTab().setText(mTitleList.get(3)));//添加tab选项卡

        fragmentList.add(new OrderStateFragment());
        fragmentList.add(new PayOrderStateFragment());
        fragmentList.add(new ShipOrderStateFragment());
        fragmentList.add(new ReceiptOrderStateFragment());

        adapter=new OrderPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来。
        tabs.setTabsFromPagerAdapter(adapter);//给Tabs设置适配器
    }

    private class OrderPagerAdapter extends FragmentPagerAdapter {

        public OrderPagerAdapter(FragmentManager fm) {
            super(fm);

        }


        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }


    }
}
