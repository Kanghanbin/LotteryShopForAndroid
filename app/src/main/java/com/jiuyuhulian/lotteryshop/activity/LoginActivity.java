package com.jiuyuhulian.lotteryshop.activity;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blankj.utilcode.utils.RegexUtils;
import com.blankj.utilcode.utils.SPUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.api.ApiServices;
import com.jiuyuhulian.lotteryshop.config.Constant;
import com.jiuyuhulian.lotteryshop.engine.exchange.ExchangeHomeActivity;
import com.jiuyuhulian.lotteryshop.model.ForgetPwd;
import com.jiuyuhulian.lotteryshop.model.Login;
import com.jiuyuhulian.lotteryshop.model.Regist;
import com.jiuyuhulian.lotteryshop.model.ResponseCode;
import com.jiuyuhulian.lotteryshop.rest.RetrofitHttp;
import com.jiuyuhulian.lotteryshop.utils.DialogUtil;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.zhy.autolayout.AutoLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import chihane.jdaddressselector.BottomDialog;
import chihane.jdaddressselector.OnAddressSelectedListener;
import chihane.jdaddressselector.model.City;
import chihane.jdaddressselector.model.County;
import chihane.jdaddressselector.model.Province;
import chihane.jdaddressselector.model.Street;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher, View.OnLayoutChangeListener {

    //登录界面
    @BindView(R.id.iv_login)
    ImageView ivLogin;
    @BindView(R.id.et_shop_id)
    EditText etShopId;
    @BindView(R.id.et_operator)
    EditText etOperator;
    @BindView(R.id.et_login_pwd)
    EditText etLoginPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.fogotPwd)
    TextView fogotPwd;
    @BindView(R.id.fastRegist)
    TextView fastRegist;
    @BindView(R.id.iv_Delete)
    ImageView ivDelete;
    @BindView(R.id.login_view)
    ScrollView loginView;
    @BindView(R.id.regist_view)
    ScrollView registView;
    @BindView(R.id.forget_view)
    ScrollView forgetView;
    @BindView(R.id.root_view)
    AutoLinearLayout rootView;


    /**
     * 0 登录界面
     * 1 注册界面
     * 2 忘记密码
     */
    private int mState;
    private int mLogoHeight;
    //屏幕高度
    private int screenHeight = 0;
    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;

    private String strShopId, strOperator, strPwd;
    private String shopId;
    private String operatorId;
    private String password;

    //注册界面
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_idcard)
    EditText etIdcard;
    @BindView(R.id.regist_et_phone)
    EditText registEtPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.btn_code)
    Button btnCode;
    @BindView(R.id.et_adress)
    TextView etAdress;
    @BindView(R.id.regist_et_login_pwd)
    EditText rigistEtLoginPwd;
    @BindView(R.id.et_saftyPwd)
    EditText etSaftyPwd;
    @BindView(R.id.btn_rg)
    Button btnRg;
    @BindView(R.id.regist_btn_login)
    AutoLinearLayout registBtnLogin;
    @BindView(R.id.delete)
    ImageView delete;
    @BindView(R.id.delete_safty)
    ImageView deleteSafty;


    private boolean checkUpResult = true;


    private String name;
    private String idcard;
    private String phone;
    private String code;
    private String address;
    private String pwd;
    private String saftyPwd;

    //忘记密码

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

    private String findPhone;
    private String findCode;
    private String findPwd;
    private String findSurePwd;



    private CountDownTimer countDownTimer;
    private ApiServices apiServices;
    private SPUtils sp;
    private String jytoken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initViews();
        initListeners();
        //initSystemBarTint();
    }


    private void initSystemBarTint() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // create our manager instance after the content view is set
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            // enable status bar tint
            tintManager.setStatusBarTintEnabled(true);
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            // enable navigation bar tint
            tintManager.setNavigationBarTintEnabled(true);
            tintManager.setTintColor(getResources().getColor(R.color.updatPwd_bg));
            tintManager.setTintAlpha(0f);
        }

    }

    private void initViews() {
        //获取屏幕高度
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight / 3;
        initTool();
    }

    private void initTool() {
        apiServices = RetrofitHttp.getApiServiceWithToken();
        sp = new SPUtils("regist");

        if (sp.getString("shopnum", "").equals("") || sp.getString("shopnum", "") == null) {
            etShopId.setHint("门店编号");
        } else {
            etShopId.setText(sp.getString("shopnum"));
        }

    }

    private void initListeners() {
        fastRegist.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        fogotPwd.setOnClickListener(this);
        ivDelete.setOnClickListener(this);
        etLoginPwd.addTextChangedListener(this);

        forgetBtnLogin.setOnClickListener(this);
        btnFindPwd.setOnClickListener(this);
        btnGetCode.setOnClickListener(this);

        registBtnLogin.setOnClickListener(this);
        btnCode.setOnClickListener(this);
        btnRg.setOnClickListener(this);
        etAdress.setOnClickListener(this);
        etSaftyPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                deleteSafty.setVisibility(View.VISIBLE);
            }
        });
        rigistEtLoginPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                delete.setVisibility(View.VISIBLE);
            }
        });
        deleteSafty.setOnClickListener(this);
        delete.setOnClickListener(this);
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

    Intent intent;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //登录相关
            case R.id.iv_Delete:
                etLoginPwd.setText("");
                ivDelete.setVisibility(View.INVISIBLE);
                break;
            case R.id.fogotPwd:
                mState = 2;
                changeUi(mState);
                break;
            case R.id.fastRegist:
                mState = 1;
                changeUi(1);
                break;
            case R.id.btn_login:
                loginData();
                doLogin();
                break;
            //}
            //注册相关
            case R.id.delete:
                rigistEtLoginPwd.setText("");
                delete.setVisibility(View.INVISIBLE);
                break;
            case R.id.delete_safty:
                etSaftyPwd.setText("");
                deleteSafty.setVisibility(View.INVISIBLE);
                break;
            case R.id.et_adress:
                final BottomDialog dialog = new BottomDialog(this);
                dialog.setOnAddressSelectedListener(new OnAddressSelectedListener() {
                    @Override
                    public void onAddressSelected(Province province, City city, County county, Street street) {

                        String s =
                                (province == null ? "" : province.name) +
                                        (city == null ? "" : "" + city.name) +
                                        (county == null ? "" : "" + county.name) +
                                        (street == null ? "" : "" + street.name);
                        etAdress.setText(s.toString());
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            case R.id.btn_rg:
                registData();
                doRegist();
                break;
            case R.id.regist_btn_login:
                mState = 0;
                changeUi(0);
                break;
            case R.id.btn_code:
                phone = registEtPhone.getText().toString().trim();
                if (!RegexUtils.isMobileSimple(phone)) {
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(registEtPhone);
                    ToastUtils.showShortToast("请输入正确的手机号");
                    return;
                } else {
                    doSendCode("register",phone);
                }


                countDownTimer = new CountDownTimer(Constant.MILLISINFUTURE, Constant.COUNTDOWNINTERVAL) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        btnCode.setClickable(false);
                        btnCode.setText(millisUntilFinished / Constant.COUNTDOWNINTERVAL + "S");
                        btnCode.setTextColor(getResources().getColor(R.color.six));
                        btnCode.setBackgroundResource(R.drawable.yzm_beijing_wx);
                    }

                    @Override
                    public void onFinish() {
                        btnCode.setText("重新获取");
                        btnCode.setClickable(true);
                        btnCode.setTextColor(getResources().getColor(R.color.white));
                        btnCode.setBackgroundResource(R.drawable.yzm_beijing);
                    }
                };
                countDownTimer.start();
                break;
            //忘记密码
            case R.id.btn_findPwd:
                findPhone = tvPhone.getText().toString().trim();
                findCode=forgotetCode.getText().toString().trim();
                findPwd = etPwd.getText().toString().trim();
                findSurePwd=etPwdConfirm.getText().toString().trim();
                if (!RegexUtils.isMobileSimple(findPhone)) {
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(tvPhone);
                    ToastUtils.showShortToast("请输入正确的手机号");
                    checkUpResult = false;
                    return;
                } else {
                    checkUpResult=true;
                }

                if (findCode.equals("")) {
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(forgotetCode);
                    ToastUtils.showShortToast("验证码不能为空");
                    checkUpResult = false;
                    return;
                } else {
                    checkUpResult = true;
                }


                if (!findPwd.equals("")&&!findSurePwd.equals("")&&findPwd.equals(findSurePwd)) {
                    checkUpResult=true;
                } else {
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(etPwdConfirm);
                    ToastUtils.showShortToast("两次输入的密码必须一致");
                    checkUpResult = false;
                    return;
                }
                doForgetPwd();
                break;
            case R.id.forget_btn_login:
                mState = 0;
                changeUi(0);
                break;
            case R.id.btn_getCode:
                findPhone = tvPhone.getText().toString().trim();
                if (!RegexUtils.isMobileSimple(findPhone)) {
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(tvPhone);
                    ToastUtils.showShortToast("请输入正确的手机号");
                    return;
                } else {
                    doSendCode("find-password",findPhone);
                }
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
                break;
        }
    }

    private void doForgetPwd() {
        new DialogUtil(LoginActivity.this).show();
        apiServices.forgetPwd(findPhone,"1234",findPwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ForgetPwd>() {
                    @Override
                    public void onCompleted() {
                        new DialogUtil(LoginActivity.this).dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        new DialogUtil(LoginActivity.this).dismiss();
                    }

                    @Override
                    public void onNext(ForgetPwd forgetPwd) {
                        handleForgetPwd(forgetPwd);
                    }
                });
    }

    private void handleForgetPwd(ForgetPwd forgetPwd) {
        if(forgetPwd.getCode()==0){
            ToastUtils.showShortToast("密码修改成功");
        }else {
            ToastUtils.showShortToast(forgetPwd.getMessage());
        }
    }

    private void doSendCode(String module,String mphone) {
        new DialogUtil(LoginActivity.this).show();
        apiServices.sendCode(module,mphone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseCode>() {
                    @Override
                    public void onCompleted() {
                        new DialogUtil(LoginActivity.this).dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        new DialogUtil(LoginActivity.this).dismiss();
                    }

                    @Override
                    public void onNext(ResponseCode sendCode) {
                        handleSendResult(sendCode);
                    }
                });
    }

    private void handleSendResult(ResponseCode sendCode) {
        if (sendCode.getCode()==0) {
            ToastUtils.showShortToast("验证码发送成功");
        } else {
            ToastUtils.showShortToast(sendCode.getMessage());
        }
    }

    private void changeUi(int state) {
        switch (state) {
            case 0:
                loginView.setVisibility(View.VISIBLE);
                registView.setVisibility(View.GONE);
                forgetView.setVisibility(View.GONE);
                if (sp.getString("shopnum", "").equals("") || sp.getString("shopnum", "") == null) {
                    etShopId.setHint("门店编号");
                } else {
                    etShopId.setText(sp.getString("shopnum"));
                }
                break;
            case 1:
                loginView.setVisibility(View.GONE);
                registView.setVisibility(View.VISIBLE);
                forgetView.setVisibility(View.GONE);
                break;
            case 2:
                loginView.setVisibility(View.GONE);
                registView.setVisibility(View.GONE);
                forgetView.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void loginData() {
        shopId = etShopId.getText().toString().trim();
        operatorId = etOperator.getText().toString().trim();
        password = etLoginPwd.getText().toString().trim();
        if (shopId.equals("")) {
            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .playOn(etShopId);
            ToastUtils.showShortToast("门店编号不能为空");
            checkUpResult = false;
            return;
        } else {
            checkUpResult = true;
        }
        if (operatorId.equals("")) {
            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .playOn(etOperator);
            ToastUtils.showShortToast("操作员编号不能为空");
            checkUpResult = false;
            return;
        } else {
            checkUpResult = true;
        }
        if (password.equals("")) {
            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .playOn(etLoginPwd);
            ToastUtils.showShortToast("密码不能为空");
            checkUpResult = false;
            return;
        } else {
            checkUpResult = true;
        }
    }

    private void registData() {
        name = etName.getText().toString().trim();
        idcard = etIdcard.getText().toString().trim();
        phone = registEtPhone.getText().toString().trim();
        code = etCode.getText().toString().trim();
        address = etAdress.getText().toString().trim();
        pwd = rigistEtLoginPwd.getText().toString().trim();
        saftyPwd = etSaftyPwd.getText().toString().trim();
        if (name.equals("")) {
            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .playOn(etName);
            ToastUtils.showShortToast("店主姓名不能为空");
            checkUpResult = false;
            return;
        } else {
            checkUpResult = true;
        }
        //
        if (!RegexUtils.isIDCard18(idcard)) {
            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .playOn(etIdcard);

            ToastUtils.showShortToast("请输入正确的身份证号");
            checkUpResult = false;
            return;
        } else {
            checkUpResult = true;
        }
        if (!RegexUtils.isMobileSimple(phone)) {
            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .playOn(registEtPhone);
            ToastUtils.showShortToast("请输入正确的手机号");
            checkUpResult = false;
            return;
        } else {
            checkUpResult = true;
        }
        if (code.equals("")) {
            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .playOn(etCode);
            ToastUtils.showShortToast("验证码不能为空");
            checkUpResult = false;
            return;
        } else {
            checkUpResult = true;
        }
        if (address.equals("")) {
            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .playOn(etAdress);
            ToastUtils.showShortToast("地址不能为空");
            checkUpResult = false;
            return;
        } else {
            checkUpResult = true;
        }
        if (pwd.equals("")) {
            YoYo.with((Techniques.Tada))
                    .duration(700)
                    .playOn(rigistEtLoginPwd);
            ToastUtils.showShortToast("登录密码不能为空");
            checkUpResult = false;
            return;
        } else {
            checkUpResult = true;
        }
        if (saftyPwd.equals("")) {
            YoYo.with((Techniques.Tada))
                    .duration(700)
                    .playOn(etSaftyPwd);
            ToastUtils.showShortToast("财务安全码不能为空");
            checkUpResult = false;
            return;
        } else {
            checkUpResult = true;
        }


    }


    private void doLogin() {
        if (checkUpResult) {
            strShopId = etShopId.getText().toString().trim();
            strOperator = etOperator.getText().toString().trim();
            strPwd = etLoginPwd.getText().toString().trim();
            new DialogUtil(LoginActivity.this).show();
            apiServices.login(strShopId, strOperator, strPwd)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Login>() {
                        @Override
                        public void onCompleted() {
                            new DialogUtil(LoginActivity.this).dismiss();
                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtils.showShortToast(Constant.SERVER_ERROR);
                            new DialogUtil(LoginActivity.this).dismiss();
                        }

                        @Override
                        public void onNext(Login login) {

                            handleLoginResult(login);
                        }
                    });
        }

    }

    private void handleLoginResult(Login login) {
        if (login.getCode() == 0) {

            ToastUtils.showLongToast("登录成功");
            String token = login.getData().getToken();
            String shopid = login.getData().getShop_id();
            String staffid = login.getData().getStaff_id();

           // Log.i("shopnum", token==""?"token is empty":token);
            //存储门店编号
            sp.putString("shopid", shopid);
            sp.putString("token", token);
            sp.putString("staffid", staffid);

            //成功
            intent = new Intent(LoginActivity.this, ExchangeHomeActivity.class);
            startActivity(intent);
            finish();

        } else {
            //输出错误信息
            ToastUtils.showShortToast(login.getMessage());
        }
    }


    private void doRegist() {
        if (checkUpResult) {
            new DialogUtil(this).show();
            apiServices.register(name,idcard,phone,code,address,pwd,saftyPwd)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Regist>() {
                        @Override
                        public void onCompleted() {

                            new DialogUtil(LoginActivity.this).dismiss();
                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtils.showShortToast(Constant.SERVER_ERROR);

                        }

                        @Override
                        public void onNext(Regist regist) {
                            handleRegistResult(regist);
                        }
                    });


        }
    }

    private void handleRegistResult(Regist regist) {
        if (regist.getCode()==0) {
            mState = 0;
            changeUi(0);
            ToastUtils.showLongToast("注册成功，请登录");
            //成功

            String shopnum = regist.getData().getCode();
            Log.i("shopnum", shopnum);
            //存储门店编号
            sp.putString("shopnum", shopnum);

        } else {
            //输出错误信息
            ToastUtils.showShortToast(regist.getMessage());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        rootView.addOnLayoutChangeListener(this);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//        }
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

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        //old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
        //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
            mLogoHeight = ivLogin.getHeight();
            performAnimation(ivLogin, mLogoHeight, 0);
            performAnimation(img, mLogoHeight, 0);
        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
            performAnimation(ivLogin, 0, mLogoHeight);
            performAnimation(img, 0, mLogoHeight);
        }
    }

}
