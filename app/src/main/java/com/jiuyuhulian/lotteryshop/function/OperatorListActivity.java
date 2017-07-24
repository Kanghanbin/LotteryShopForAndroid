package com.jiuyuhulian.lotteryshop.function;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.utils.SPUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.api.ApiServices;
import com.jiuyuhulian.lotteryshop.config.Constant;
import com.jiuyuhulian.lotteryshop.entity.OperatorEntity;
import com.jiuyuhulian.lotteryshop.model.StaffList;
import com.jiuyuhulian.lotteryshop.rest.RetrofitHttp;
import com.jiuyuhulian.lotteryshop.utils.DialogUtil;
import com.jiuyuhulian.lotteryshop.view.TopBar;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OperatorListActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    TopBar topbar;
    @BindView(R.id.rv_Operator)
    RecyclerView rvOperator;
    private List<OperatorEntity>list;
    private Intent intent;

    private List<StaffList.DataBean> mStaffList;
    private SPUtils spUtils;
    private ApiServices apiServices;
    private String shopId;
    private Context mContext;

    private static int ADD=0;
    private static int EDIT=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_list);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {

        mContext = this;
        apiServices = RetrofitHttp.getApiServiceWithToken();
        spUtils = new SPUtils("regist");
        shopId = spUtils.getString("shopid");
        getData();
        topbar.setOnTopBarClickListener(new TopBar.OnTopBarClickListener() {
            @Override
            public void onTopBarRightClick(View v) {
                intent=new Intent(OperatorListActivity.this,AddOperatorActivity.class);
                intent.putExtra("flag","add");
                startActivityForResult(intent,ADD);
            }

            @Override
            public void onTopBarLeftClick(View v) {
                finish();
            }
        });

    }

    private void getData() {
        new DialogUtil(mContext).show();
        apiServices.staffList(shopId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<StaffList>() {
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
                    public void onNext(StaffList staffList) {

                        handStaffListResult(staffList);
                    }
                });

    }

    private void handStaffListResult(StaffList staffList) {

        if(staffList.getCode()==0){
            mStaffList=staffList.getData();
            rvOperator.setLayoutManager(new LinearLayoutManager(this));
            rvOperator.setAdapter(new CommonAdapter<StaffList.DataBean>(this,R.layout.layout_item_operator_list,mStaffList){

                @Override
                protected void convert(ViewHolder holder, final StaffList.DataBean operatorEntity, int position) {
                    holder.setText(R.id.tv_name,"操作员"+operatorEntity.getStaff());
                    holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intent=new Intent(OperatorListActivity.this,AddOperatorActivity.class);
                            intent.putExtra("flag","edit");
                            intent.putExtra("operatorEntity",operatorEntity);
                            startActivityForResult(intent,EDIT);
                        }
                    });
                }
            });
        }else {
            ToastUtils.showShortToast(staffList.getMessage());
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getData();
    }
}
