package com.jiuyuhulian.lotteryshop.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.SPUtils;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.engine.exchange.ExchangeHomeActivity;
import com.jiuyuhulian.lotteryshop.utils.PreferenceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admin on 2017/3/20.
 */

public class ApplicationActivity extends AppCompatActivity {
//    @BindView(R.id.application_icon)
//    ImageView applicationIcon;
    @BindView(R.id.app_name)
    TextView appName;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.application_bg)
    ImageView applicationBg;
    /**
     * 定义三个切换动画
     */
    private Animation mFadeIn;

    private Animation mFadeOut;

    private Animation mFadeInScale;

    private SPUtils sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DoAction();
    }

    /**
     * 初始化动画
     */
    private void initAnim() {
        mFadeIn = AnimationUtils.loadAnimation(this, R.anim.application_fade_in);
        mFadeIn.setDuration(500);
        mFadeInScale = AnimationUtils.loadAnimation(this,
                R.anim.application_fade_in_scale);
        mFadeInScale.setDuration(2000);
        mFadeOut = AnimationUtils.loadAnimation(this, R.anim.application_fade_out);
        mFadeOut.setDuration(500);
        applicationBg.setAnimation(mFadeIn);

    }

    /**
     * 建立监听事件
     */
    private void setAnimListener() {
        mFadeIn.setAnimationListener(new Animation.AnimationListener() {

            public void onAnimationStart(Animation animation) {

            }

            public void onAnimationRepeat(Animation animation) {

            }

            public void onAnimationEnd(Animation animation) {
                applicationBg.startAnimation(mFadeInScale);
            }
        });
        mFadeInScale.setAnimationListener(new Animation.AnimationListener() {

            public void onAnimationStart(Animation animation) {


            }

            public void onAnimationRepeat(Animation animation) {

            }

            public void onAnimationEnd(Animation animation) {
                applicationBg.startAnimation(mFadeOut);
            }
        });
        mFadeOut.setAnimationListener(new Animation.AnimationListener() {

            public void onAnimationStart(Animation animation) {
                Intent intent = new Intent();
                if (sp.getString("token", "").equals("") || sp.getString("token", "") == null) {
                    intent.setClass(ApplicationActivity.this,LoginActivity.class);
                }else{
                    Log.e("token",sp.getString("token", ""));
                    intent.setClass(ApplicationActivity.this,ExchangeHomeActivity.class);
                }
                startActivity(intent);
                finish();
            }

            public void onAnimationRepeat(Animation animation) {

            }

            public void onAnimationEnd(Animation animation) {

            }
        });
    }


    private void DoAction() {
        boolean isFirst = PreferenceUtil.readBoolean(this, "First", "isFirst", true);
        sp=new SPUtils("regist");
        if (isFirst == true) {
            PreferenceUtil.write(this, "First", "isFirst", false);
            Welcome();
        } else {
            ComeingApp();
        }
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
    /**
     * 进入欢迎界面
     */
    private void Welcome() {
        Intent intent = new Intent();
        intent.setClass(this, WelcomeActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 直接进入app
     */
    private void ComeingApp() {
        setContentView(R.layout.applicationactivity_layout);
        ButterKnife.bind(this);
        initAnim();
        setAnimListener();
    }
}
