package com.jiuyuhulian.lotteryshop.function;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.view.TopBar;

public class StockQueryActivity extends AppCompatActivity {


    @BindView(R.id.topbar)
    TopBar topbar;
    @BindView(R.id.activity_exchange_detail)
    AutoLinearLayout activityExchangeDetail;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private ArrayList<Fragment> fragmentList = new ArrayList<>();

    private StockQuerayPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        ButterKnife.bind(this);
        initViews();
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
        //添加页卡标题
        mTitleList.add("全部");
        mTitleList.add("已激活");
        mTitleList.add("未激活");

        tabs.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        tabs.addTab(tabs.newTab().setText(mTitleList.get(0)));//添加tab选项卡
        tabs.addTab(tabs.newTab().setText(mTitleList.get(1)));//添加tab选项卡
        tabs.addTab(tabs.newTab().setText(mTitleList.get(2)));//添加tab选项卡

        fragmentList.add(new StockQueryAllFragment());
        fragmentList.add(new StockQueryActivedFragment());
        fragmentList.add(new StockQueryNotActiveFragment());

        adapter=new StockQuerayPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来。
        tabs.setTabsFromPagerAdapter(adapter);//给Tabs设置适配器
    }




    private class StockQuerayPagerAdapter extends FragmentPagerAdapter {
        public StockQuerayPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);
        }
    }
}
