package com.jiuyuhulian.lotteryshop.function;

import android.content.Context;
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
import com.jiuyuhulian.lotteryshop.model.AddStaff;
import com.jiuyuhulian.lotteryshop.model.ResponseCode;
import com.jiuyuhulian.lotteryshop.model.StaffList;
import com.jiuyuhulian.lotteryshop.rest.RetrofitHttp;
import com.jiuyuhulian.lotteryshop.utils.DialogUtil;
import com.jiuyuhulian.lotteryshop.view.TopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddOperatorActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    TopBar topbar;
    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.finish)
    Button finish;
    @BindView(R.id.et_phone)
    EditText etPhone;
    private String flag, staffId;
    private StaffList.DataBean entity;
    private SPUtils spUtils;
    private ApiServices apiServices;
    private String shopId;
    private Context mContext;


    private static int ADD = 0;
    private static int EDIT = 1;
    private static int DELETE = 1;

    private String staff, password, mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_operator);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        mContext = this;
        apiServices = RetrofitHttp.getApiServiceWithToken();
        spUtils = new SPUtils("regist");
        shopId = spUtils.getString("shopid");


        flag = getIntent().getStringExtra("flag");
        entity = (StaffList.DataBean) getIntent().getSerializableExtra("operatorEntity");
        if (flag.equals("edit")) {
            etNumber.setText(entity.getStaff());
            etPwd.setHint("请重新输入密码");
            etPhone.setText(entity.getMobile());
            staffId = entity.getStaff_id();
            topbar.setCenterText("操作员信息");
            topbar.setRightText("删除");
            finish.setText("完成");
        } else {
            topbar.setRightText("");
            topbar.setCenterText("添加操作员");
            finish.setText("确定");
        }
        topbar.setOnTopBarClickListener(new TopBar.OnTopBarClickListener() {
            @Override
            public void onTopBarRightClick(View v) {
                //删除操作员
                deleteStaff();
            }

            @Override
            public void onTopBarLeftClick(View v) {
                finish();
            }
        });
    }




    @OnClick(R.id.finish)
    public void AddOrEdit() {

        staff = etNumber.getText().toString().trim();
        password = etPwd.getText().toString().trim();
        mobile = etPhone.getText().toString().trim();


        if (flag.equals("edit")) {
            edit();
        } else {
            add();
        }
    }

    private void add() {
        new DialogUtil(mContext).show();
        apiServices.addStaff(shopId, staff, password, mobile).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AddStaff>() {
                    @Override
                    public void onCompleted() {
                        new DialogUtil(mContext).dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        new DialogUtil(mContext).dismiss();
                        ToastUtils.showShortToast(Constant.SERVER_ERROR);
                    }

                    @Override
                    public void onNext(AddStaff addStaff) {
                        handleAddStaff(addStaff);
                    }
                });
    }

    private void edit() {
        new DialogUtil(mContext).show();
        apiServices.editStaff(staffId, staff, password, mobile).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseCode>() {
                    @Override
                    public void onCompleted() {
                        new DialogUtil(mContext).dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        new DialogUtil(mContext).dismiss();
                        ToastUtils.showShortToast(Constant.SERVER_ERROR);
                    }

                    @Override
                    public void onNext(ResponseCode editStaff) {
                        handleEditStaff(editStaff);
                    }
                });
    }

    private void deleteStaff() {
        new DialogUtil(mContext).show();
        apiServices.deleteStaff(staffId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseCode>() {
                    @Override
                    public void onCompleted() {
                        new DialogUtil(mContext).dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        new DialogUtil(mContext).dismiss();
                        ToastUtils.showShortToast(Constant.SERVER_ERROR);
                    }

                    @Override
                    public void onNext(ResponseCode deleteStaff) {
                        handleDeleteStaff(deleteStaff);
                    }
                });
    }

    private void handleDeleteStaff(ResponseCode deleteStaff) {
        if (deleteStaff.getCode() == 0) {
            ToastUtils.showShortToast("删除成功");
            setResult(DELETE);
            finish();
        } else {
            ToastUtils.showShortToast(deleteStaff.getMessage());
        }
    }

    private void handleEditStaff(ResponseCode editStaff) {
        if (editStaff.getCode() == 0) {
            ToastUtils.showShortToast("修改操作员成功");
            setResult(EDIT);
            finish();
        } else {
            ToastUtils.showShortToast(editStaff.getMessage());
        }
    }

    private void handleAddStaff(AddStaff addStaff) {
        if (addStaff.getCode() == 0) {
            ToastUtils.showShortToast("添加操作员成功");
            setResult(ADD);
            finish();
        } else {
            ToastUtils.showShortToast(addStaff.getMessage());
        }
    }


}
