package com.jiuyuhulian.lotteryshop.function;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.utils.SizeUtils;
import com.jiuyuhulian.lotteryshop.EnterTicketEvent;
import com.jiuyuhulian.lotteryshop.LotteryShopApplication;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.local.LocalShoppingCarInfo;
import com.jiuyuhulian.lotteryshop.view.BizierEvaluator2;
import com.jiuyuhulian.lotteryshop.view.TopBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EnterTicketActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    TopBar topbar;
    @BindView(R.id.et_search)
    EditText etSearch;

    @BindView(R.id.activity_enter_ticket)
    LinearLayout activityEnterTicket;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private EnterTicketPagerAdapter pagerAdapter;
    private List<LocalShoppingCarInfo> localList;
    private int msg;
    private int[] startPos;
    private int[] endPos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_ticket);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        //添加页卡标题
        mTitleList.add("2元");
        mTitleList.add("5元");
        mTitleList.add("10元");
        mTitleList.add("20元");
        mTitleList.add("30元");


        tabs.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        tabs.addTab(tabs.newTab().setText(mTitleList.get(0)));//添加tab选项卡
        tabs.addTab(tabs.newTab().setText(mTitleList.get(1)));//添加tab选项卡
        tabs.addTab(tabs.newTab().setText(mTitleList.get(2)));//添加tab选项卡
        tabs.addTab(tabs.newTab().setText(mTitleList.get(3)));//添加tab选项卡
        tabs.addTab(tabs.newTab().setText(mTitleList.get(4)));//添加tab选项卡

        fragmentList.add(EnterTicketFragment.newInstance("2"));
        fragmentList.add(EnterTicketFragment.newInstance("5"));
        fragmentList.add(EnterTicketFragment.newInstance("10"));
        fragmentList.add(EnterTicketFragment.newInstance("20"));
        fragmentList.add(EnterTicketFragment.newInstance("30"));


        pagerAdapter = new EnterTicketPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabs.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来。
        tabs.setTabsFromPagerAdapter(pagerAdapter);//给Tabs设置适配器

    }

    @Override
    protected void onStart() {
        super.onStart();
        initViews();
    }

    private void initViews() {
        getShopCarDB();
        topbar.setOnTopBarClickListener(new TopBar.OnTopBarClickListener() {
            @Override
            public void onTopBarRightClick(View v) {
                Intent intent = new Intent(EnterTicketActivity.this, ShoppingCarActivity.class);
                //intent.putExtra("shopcar", (Serializable) intentShopCar);
                startActivity(intent);
            }

            @Override
            public void onTopBarLeftClick(View v) {
                finish();
            }
        });


    }

    private void getShopCarDB() {

        localList = LotteryShopApplication.liteOrm.query(LocalShoppingCarInfo.class);
        int count = 0;
        double totalPrice = 0.00;// 购买的商品总价
        for (LocalShoppingCarInfo local : localList) {

            count = count + local.getNum();
            totalPrice+=local.getPrice()*local.getNum();
        }
        if (count == 0) {
            topbar.setShowMessage(false);
            topbar.setShowPrice(false);
        } else {
            topbar.setShowMessage(true);
            topbar.setShowPrice(true);
            topbar.setMessageText(count + "");
            topbar.setPriceText("￥"+ new DecimalFormat("0.00").format(totalPrice));

        }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateTopbar(EnterTicketEvent event) {
        addAnimation(event);

    }

    /**
     * 添加购物车动画,动画结束后更改购物车数量
     */
    private void addAnimation(EnterTicketEvent event) {
        final ImageView mImg = new ImageView(this);
        mImg.setImageResource(R.drawable.xin);
        mImg.setScaleType(ImageView.ScaleType.MATRIX);
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        rootView.addView(mImg);

        endPos = new int[2];
        topbar.getmRightIv().getLocationInWindow(endPos);
        /*动画开始位置，也就是物品的位置;动画结束的位置，也就是购物车的位置 */
        startPos = event.getStartPos();
        Point startPosition = new Point(startPos[0], startPos[1]);
        Point endPosition = new Point(endPos[0] + topbar.getmRightIv().getWidth() / 2, endPos[1] + topbar.getmRightIv().getHeight() / 2);
        int pointX = (startPosition.x + endPosition.x) / 2 - SizeUtils.dp2px(100);
        int pointY = startPosition.y - SizeUtils.dp2px(100);
        Point controllPoint = new Point(pointX, pointY);
 /*
        * 属性动画，依靠TypeEvaluator来实现动画效果，其中位移，缩放，渐变，旋转都是可以直接使用
        * 这里是自定义了TypeEvaluator， 我们通过point记录运动的轨迹，然后，物品随着轨迹运动，
        * 一旦轨迹发生变化，就会调用addUpdateListener这个方法，我们不断的获取新的位置，是物品移动
        * */
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new BizierEvaluator2(controllPoint), startPosition, endPosition);
        valueAnimator.setDuration(500);
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Point point = (Point) valueAnimator.getAnimatedValue();
                mImg.setX(point.x);
                mImg.setY(point.y);
            }
        });

        /**
         * 动画结束，移除掉小圆圈
         */
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ViewGroup rootView = (ViewGroup) EnterTicketActivity.this.getWindow().getDecorView();
                rootView.removeView(mImg);
            }
        });
        //更改购物车数量
        getShopCarDB();



    }




    private class EnterTicketPagerAdapter extends FragmentPagerAdapter {

        public EnterTicketPagerAdapter(FragmentManager fm) {
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
