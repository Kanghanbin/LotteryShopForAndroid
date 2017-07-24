package com.jiuyuhulian.lotteryshop.engine.exchange;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.blankj.utilcode.utils.ToastUtils;
import com.jiuyuhulian.lotteryshop.BaseFragment;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.engine.exchange.activity.SubTotalActivity;
import com.jiuyuhulian.lotteryshop.view.TopBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExchangeHomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {


    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.activity_exchange_home)
    LinearLayout activityExchangeHome;
    @BindView(R.id.topbar)
    TopBar topBar;
    @BindView(R.id.id_tab1)
    RadioButton tab1;
    @BindView(R.id.id_tab2)
    RadioButton tab2;
    @BindView(R.id.id_tab3)
    RadioButton tab3;
    @BindView(R.id.id_radioGroup)
    RadioGroup radioGroup;
    private MyPagerAdapter adapter;
    private Class classes[] = {ExchangeFragemnet.class, FunctionFragment.class, MineFragment.class};
    private Fragment fragments[] = {BaseFragment.newInstance(classes[0], null), BaseFragment.newInstance(classes[1], null), BaseFragment.newInstance(classes[2], null)};
    private String titles[] = {"兑换", "功能", "我的"};
    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private int IconImg[] = {
            R.drawable.dj_selector, R.drawable.gn_selector, R.drawable.wd_selector
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_exchange_home);
        ButterKnife.bind(this);
        initviews();
    }

    public void initviews() {
        topBar.setRightText("小计");
        topBar.setLeftText("");
        topBar.setCenterText(titles[0]);
        topBar.setOnTopBarClickListener(new TopBar.OnTopBarClickListener() {
            @Override
            public void onTopBarRightClick(View v) {
                startActivity(new Intent(ExchangeHomeActivity.this, SubTotalActivity.class));
            }

            @Override
            public void onTopBarLeftClick(View v) {

            }
        });
        //添加页卡标题
        mTitleList.add("兑换");
        mTitleList.add("功能");
        mTitleList.add("我的");
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        radioGroup=(RadioGroup) findViewById(R.id.id_radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.id_tab1:
                        viewPager.setCurrentItem(0);//选择某一页
                        break;
                    case R.id.id_tab2:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.id_tab3:
                        viewPager.setCurrentItem(2);
                        break;

                }
            }
        });
        fragmentList.add(new ExchangeFragemnet());
        fragmentList.add(new FunctionFragment());
        fragmentList.add(new MineFragment());
        viewPager.setAdapter(adapter);
        //ViewPager显示第一个Fragment
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(this);

    }


    private long exitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            ToastUtils.showShortToast("再按一次退出应用");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        topBar.setCenterText(titles[position]);
        switch (position) {
            case 0:
                topBar.setRightText("小计");
                tab1.setChecked(true);
                tab2.setChecked(false);
                tab3.setChecked(false);
                break;
            case 1:
                topBar.setRightText("");
                tab2.setChecked(true);
                tab1.setChecked(false);
                tab3.setChecked(false);
                break;
            case 2:
                topBar.setRightText("");
                tab1.setChecked(false);
                tab2.setChecked(false);
                tab3.setChecked(true);
                break;

        }
    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }


    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);

        }
        @Override
        public int getCount() {
            return fragmentList.size();
        }


        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }


    }



}
