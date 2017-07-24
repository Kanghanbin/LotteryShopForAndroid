package com.jiuyuhulian.lotteryshop.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.adapter.WelcomeAdapter;
import com.jiuyuhulian.lotteryshop.config.Constant;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.vp_welcome)
    ViewPager vpWelcome;
    @BindView(R.id.goComing)
    TextView goComing;
    @BindView(R.id.activity_welcome)
    AutoRelativeLayout activityWelcome;


    private List<ImageView> views=new ArrayList<>();
    private WelcomeAdapter adapter;

    private int length;

    /**
     * 底部指示器(小圆点)的图片
     */
    private ImageView[] points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        goComing.setVisibility(View.GONE);

        length= Constant.pics.length;
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        for (int i = 0; i < Constant.pics.length; i++) {
            ImageView iv=new ImageView(this);
            iv.setImageResource(Constant.pics[i]);
            iv.setLayoutParams(params);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            views.add(iv);
        }
        adapter=new WelcomeAdapter(views);
        vpWelcome.setAdapter(adapter);
        vpWelcome.addOnPageChangeListener(this);
       //initPos();

    }


    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setCurrentPos(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void setCurrentPos(int currentPos){
//        for (int i = 0; i <points.length ; i++) {
//            if(i==currentPos){
//                points[i].setBackgroundColor(getResources().getColor(R.color.btn_orange));
//            }else {
//                points[i].setBackgroundColor(getResources().getColor(R.color.green));
//            }
//
//        }
        if(currentPos==length-1){
            goComing.setVisibility(View.VISIBLE);
            goComing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //进入
                    Intent intent = new Intent();
                    intent.setClass(WelcomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }else {
            goComing.setVisibility(View.GONE);
        }


    }
}
