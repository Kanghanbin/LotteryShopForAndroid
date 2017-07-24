package com.jiuyuhulian.lotteryshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.utils.SPUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.api.ApiServices;
import com.jiuyuhulian.lotteryshop.config.Constant;
import com.jiuyuhulian.lotteryshop.model.ResponseCode;
import com.jiuyuhulian.lotteryshop.rest.RetrofitHttp;
import com.jiuyuhulian.lotteryshop.utils.DialogUtil;
import com.jiuyuhulian.lotteryshop.view.TopBar;
import com.zhy.autolayout.AutoLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UpdatePwdActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    TopBar topbar;
    @BindView(R.id.btn_update)
    Button btnUpdate;
    @BindView(R.id.activity_update_pwd)
    AutoLinearLayout activityUpdatePwd;
    @BindView(R.id.et_old_pwd)
    EditText etOldPwd;
    @BindView(R.id.et_new_pwd)
    EditText etNewPwd;
    @BindView(R.id.et_new_sure_pwd)
    EditText etNewSurePwd;



    private SPUtils sp;
    private ApiServices apiServices;
    private boolean checkResult;
    private String oldPwd;
    private String newPwd;
    private String newSurePwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pwd);
        ButterKnife.bind(this);
        sp= new SPUtils("regist");
        initViews();

    }

    private void initViews() {
        topbar.setCenterText("修改密码");
        topbar.setBackGroundColor(R.color.updatPwd_bg);
        topbar.setLeftSrc(R.drawable.xgmm_return);
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
    @OnClick(R.id.btn_update)
    public void updatePwd(){
        updateData();
        String starffId=sp.getString("staffid");
        if(checkResult){
            new DialogUtil(this).show();
            apiServices= RetrofitHttp.getApiServiceWithToken();
            apiServices.changePwd(starffId,oldPwd,newPwd)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ResponseCode>() {
                        @Override
                        public void onCompleted() {
                            new DialogUtil(UpdatePwdActivity.this).dismiss();
                        }

                        @Override
                        public void onError(Throwable e) {
                            new DialogUtil(UpdatePwdActivity.this).dismiss();
                            ToastUtils.showShortToast(Constant.SERVER_ERROR);
                        }

                        @Override
                        public void onNext(ResponseCode updatePwd) {
                            handResultUpdate(updatePwd);
                        }


                    });
        }
    }

    private void handResultUpdate(ResponseCode updatePwd) {
        if (updatePwd.getCode()==0){
            sp.clear();
            ToastUtils.showShortToast("密码修改成功,请重新登陆");
            Intent intent = new Intent(this, LoginActivity.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }else {
           ToastUtils.showShortToast( updatePwd.getMessage());
        }
    }

    private void updateData() {
        oldPwd=etOldPwd.getText().toString().trim();
        newPwd=etNewPwd.getText().toString().trim();
        newSurePwd=etNewSurePwd.getText().toString().trim();

        if(oldPwd.equals("")){
            ToastUtils.showShortToast("请输入正确的原密码");
            checkResult=false;
            return;
        }else {
            checkResult=true;
        }
        if(!newPwd.equals("")&&!newSurePwd.equals("")&&newSurePwd.equals(newPwd)){
            checkResult=true;
        }else {
            ToastUtils.showShortToast("两次输入的密码必须一致且不为空");
            checkResult=false;
            return;
        }

    }
}
