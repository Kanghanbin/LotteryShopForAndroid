package com.jiuyuhulian.lotteryshop.function;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.utils.SPUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.api.ApiServices;
import com.jiuyuhulian.lotteryshop.config.Constant;
import com.jiuyuhulian.lotteryshop.model.AddressList;
import com.jiuyuhulian.lotteryshop.model.ResponseCode;
import com.jiuyuhulian.lotteryshop.rest.RetrofitHttp;
import com.jiuyuhulian.lotteryshop.utils.DialogUtil;
import com.jiuyuhulian.lotteryshop.view.TopBar;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

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

public class AddAddressActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int ADDRESULTCODE =0;
    private static final int EDITRESULTCODE =1 ;
    @BindView(R.id.topbar)
    TopBar topbar;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.tv_adress)
    TextView tvAdress;
    @BindView(R.id.tv_detail_adress)
    TextView tvDetailAdress;
    @BindView(R.id.arl_select_address)
    AutoRelativeLayout arlSelectAddress;
    @BindView(R.id.address)
    EditText etaddress;
    @BindView(R.id.activity_add_address)
    AutoLinearLayout activityAddAddress;
    private String from;
    private AddressList.DataBean entity;

    private boolean defaultaddress;


    private SPUtils spUtils;
    private ApiServices apiServices;
    private String shopId;
    private Context mContext;
    private String username,mobile,city,address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
        initViews();
        initListeners();
    }

    private void initViews() {
        mContext=this;
        apiServices= RetrofitHttp.getApiServiceWithToken();
        spUtils = new SPUtils("regist");
        shopId = spUtils.getString("shopid");
        from=getIntent().getStringExtra("flag");
        if(from.equals("edit")){
            topbar.setTitle("编辑地址");
            entity= (AddressList.DataBean) getIntent().getSerializableExtra("entity");
            name.setText(entity.getUsername());
            phone.setText(entity.getMobile());
            tvDetailAdress.setText(entity.getCity());
            etaddress.setText(entity.getAddress());
            defaultaddress=entity.getIs_default().equals("1")?true:false;
        }else{
            topbar.setTitle("添加地址");
        }
        topbar.setRightText("保存");
        topbar.setOnTopBarClickListener(new TopBar.OnTopBarClickListener() {

            @Override
            public void onTopBarRightClick(View v) {

                getTextData();

                if(from.equals("edit")){
                    new DialogUtil(mContext).show();
                    apiServices.updateAddress(entity.getId(),shopId,username,mobile,city,address)
                            .subscribeOn(Schedulers.io())
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
                                public void onNext(ResponseCode responseCode) {
                                    handleEditAddress(responseCode);
                                }
                            });

                }else {
                    addAddresss();
                }

            }

            @Override
            public void onTopBarLeftClick(View v) {
                finish();
            }
        });
    }

    private void addAddresss() {
        new DialogUtil(mContext).show();
        apiServices.addAddress(shopId,username,mobile,city,address)
                .subscribeOn(Schedulers.io())
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
                    public void onNext(ResponseCode responseCode) {
                        handleAddAddress(responseCode);
                    }
                });
    }

    private void handleAddAddress(ResponseCode responseCode) {
        if(responseCode.getCode()==0){
            ToastUtils.showShortToast("添加成功");
            setResult(ADDRESULTCODE);
            finish();
        }else {
            ToastUtils.showShortToast(responseCode.getMessage());
        }
    }

    private void getTextData() {
        username=name.getText().toString().trim();
        mobile=phone.getText().toString().trim();
        city=tvDetailAdress.getText().toString().trim();
        address=etaddress.getText().toString().trim();
    }

    private void handleEditAddress(ResponseCode responseCode) {
        if(responseCode.getCode()==0){
            ToastUtils.showShortToast("修改成功");
            setResult(EDITRESULTCODE);
            finish();
        }else {
            ToastUtils.showShortToast(responseCode.getMessage());
        }

    }

    private void initListeners() {
        arlSelectAddress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.arl_select_address){
            final BottomDialog dialog = new BottomDialog(this);
            dialog.setOnAddressSelectedListener(new OnAddressSelectedListener() {
                @Override
                public void onAddressSelected(Province province, City city, County county, Street street) {

                    String s =
                            (province == null ? "" : province.name) +
                                    (city == null ? "" : "" + city.name) +
                                    (county == null ? "" : "" + county.name) +
                                    (street == null ? "" : "" + street.name);
                    tvDetailAdress.setText(s.toString());
                    dialog.dismiss();
                }
            });
            dialog.show();

        }
    }
}
