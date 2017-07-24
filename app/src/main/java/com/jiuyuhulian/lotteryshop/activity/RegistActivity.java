package com.jiuyuhulian.lotteryshop.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.RegexUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.config.Constant;
import com.jiuyuhulian.lotteryshop.entity.UserDataEntity;
import com.zhy.autolayout.AutoLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import chihane.jdaddressselector.BottomDialog;
import chihane.jdaddressselector.OnAddressSelectedListener;
import chihane.jdaddressselector.model.City;
import chihane.jdaddressselector.model.County;
import chihane.jdaddressselector.model.Province;
import chihane.jdaddressselector.model.Street;

public class RegistActivity extends AppCompatActivity implements View.OnClickListener {

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
    @BindView(R.id.activity_login)
    AutoLinearLayout activityLogin;
    @BindView(R.id.delete)
    ImageView delete;
    @BindView(R.id.delete_safty)
    ImageView deleteSafty;

    private ProgressDialog mDialog;
    private boolean checkUpResult = true;


    private CountDownTimer countDownTimer;

    private String name;
    private String idcard;
    private String phone;
    private String code;
    private String address;
    private String pwd;
    private String saftyPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);
        initViews();
        initListeners();
    }

    private void initListeners() {
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

    private void initViews() {
        mDialog = new ProgressDialog(this);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setMessage("请稍等");
        mDialog.setIndeterminate(false);
        // 设置ProgressDialog 是否可以按退回按键取消
        mDialog.setCancelable(false);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
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
                getData();
                break;
            case R.id.regist_btn_login:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_code:
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
        }

    }

    private void getData() {
        name = etName.getText().toString().trim();
        idcard = etIdcard.getText().toString().trim();
        phone = registEtPhone.getText().toString().trim();
        code = etCode.getText().toString().trim();
        address = etAdress.getText().toString().trim();
        pwd = rigistEtLoginPwd.getText().toString().trim();
        saftyPwd = etSaftyPwd.getText().toString().trim();
        if (name.equals("")) {
            ToastUtils.showShortToast("店主姓名不能为空");
            checkUpResult = false;
        }
        //
        if (!RegexUtils.isIDCard18(idcard)) {
            ToastUtils.showShortToast("请输入正确的身份证号");
            checkUpResult = false;
        }
        if (!RegexUtils.isMobileSimple(phone)) {
            ToastUtils.showShortToast("请输入正确的手机号");
            checkUpResult = false;
        }
        if (code.equals("")) {
            ToastUtils.showShortToast("验证码不能为空");
            checkUpResult = false;
        }
        if (pwd.equals("")) {
            ToastUtils.showShortToast("登录密码不能为空");
            checkUpResult = false;
        }
        if (saftyPwd.equals("")) {
            ToastUtils.showShortToast("财务安全码不能为空");
            checkUpResult = false;
        }
        doRegist();

    }

    private void doRegist() {
        if (checkUpResult) {
            UserDataEntity userDataEntity = new UserDataEntity();
            userDataEntity.setName(name);
            userDataEntity.setAddress(address);
            userDataEntity.setPhone(phone);
            userDataEntity.setIdcard(idcard);
            userDataEntity.setPwd(pwd);
            userDataEntity.setSaftyPwd(saftyPwd);
            mDialog.show();
        }
    }


}
