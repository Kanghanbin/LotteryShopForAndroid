package com.jiuyuhulian.lotteryshop.activity;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.config.Constant;
import com.zhy.autolayout.AutoLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgotPwdActivity extends AppCompatActivity implements View.OnClickListener ,View.OnLayoutChangeListener{

    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.tv_phone)
    EditText tvPhone;
    @BindView(R.id.forget_et_code)
    EditText forgotetCode;
    @BindView(R.id.btn_getCode)
    Button btnGetCode;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.et_pwdConfirm)
    EditText etPwdConfirm;
    @BindView(R.id.btn_findPwd)
    Button btnFindPwd;
    @BindView(R.id.forget_btn_login)
    TextView forgetBtnLogin;
    @BindView(R.id.activity_forgot_pwd)
    AutoLinearLayout activityForgotPwd;

    private CountDownTimer countDownTimer;



    private int mLogoHeight;
    //屏幕高度
    private int screenHeight = 0;
    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pwd);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        //获取屏幕高度
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight/3;
        forgetBtnLogin.setOnClickListener(this);
        btnGetCode.setOnClickListener(this);
    }



    private void performAnimation(final View target, final int start, final int end) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 100);
        final IntEvaluator mEvaluator = new IntEvaluator();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int current = (int) animation.getAnimatedValue();
                float fraction = current / 100f;

                target.getLayoutParams().height = mEvaluator.evaluate(fraction, start, end);
                target.requestLayout();
            }
        });
        valueAnimator.setDuration(500).start();

    }



    @Override
    public void onClick(View v) {
        Intent intent;
        if (v.getId() == R.id.forget_btn_login) {
            intent = new Intent(ForgotPwdActivity.this, LoginActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_getCode) {

            countDownTimer = new CountDownTimer(Constant.MILLISINFUTURE, Constant.COUNTDOWNINTERVAL) {
                @Override
                public void onTick(long millisUntilFinished) {
                    btnGetCode.setClickable(false);
                    btnGetCode.setBackgroundResource(R.drawable.wjmm_bj_wx);
                    btnGetCode.setTextColor(getResources().getColor(R.color.six));
                    btnGetCode.setText(millisUntilFinished / Constant.COUNTDOWNINTERVAL + " S");
                }

                @Override
                public void onFinish() {
                    btnGetCode.setClickable(true);
                    btnGetCode.setBackgroundResource(R.drawable.wangjimima_fasongyanzhengma_beijing);
                    btnGetCode.setTextColor(getResources().getColor(R.color.white));
                    btnGetCode.setText("重新获取");
                }
            };
            countDownTimer.start();

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        activityForgotPwd.addOnLayoutChangeListener(this);
    }

    //监听软键盘弹起和收缩动作
    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        //old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
        //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
        if(oldBottom != 0 && bottom != 0 &&(oldBottom - bottom > keyHeight)){
            mLogoHeight = img.getHeight();
            performAnimation(img,mLogoHeight,0);
        }else if(oldBottom != 0 && bottom != 0 &&(bottom - oldBottom > keyHeight)){
            performAnimation(img,0,mLogoHeight);

        }
    }
}
