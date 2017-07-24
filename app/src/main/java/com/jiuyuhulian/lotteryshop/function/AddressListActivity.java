package com.jiuyuhulian.lotteryshop.function;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddressListActivity extends AppCompatActivity {

    private static final int EDITREQUESTCODE = 1;
    ;
    private static final int ADDREQUESTCODE = 0;
    ;
    @BindView(R.id.topbar)
    TopBar topbar;
    @BindView(R.id.rv_address)
    RecyclerView rvAddress;
    @BindView(R.id.all_empty)
    AutoLinearLayout allEmpty;


    private SweetAlertDialog deleteDialog;
    private CommonAdapter<AddressList.DataBean> adapter;

    private int mSelectedPos = -1;//实现最优方案单选 变量保存当前选中的position

    private SPUtils spUtils;
    private ApiServices apiServices;
    private String shopId;
    private List<AddressList.DataBean> list ;

    private String addressId;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        ButterKnife.bind(this);
        apiServices = RetrofitHttp.getApiServiceWithToken();
        spUtils = new SPUtils("regist");
        shopId = spUtils.getString("shopid");
        mContext = this;
        list = new ArrayList<>();
        topbar.setOnTopBarClickListener(new TopBar.OnTopBarClickListener() {
            @Override
            public void onTopBarRightClick(View v) {
                Intent intent = new Intent(AddressListActivity.this, AddAddressActivity.class);
                intent.putExtra("flag", "add");
                startActivityForResult(intent, ADDREQUESTCODE);
            }

            @Override
            public void onTopBarLeftClick(View v) {
                finish();
            }
        });

    }

    private void initViews() {
        getDatas();
    }

    private CommonAdapter getAdapter() {


        adapter = new CommonAdapter<AddressList.DataBean>(this, R.layout.address_adapter_item, list) {
            @Override
            protected void convert(final ViewHolder holder, final AddressList.DataBean addressInfoEntity, final int position) {

                holder.setText(R.id.name, addressInfoEntity.getUsername());
                holder.setText(R.id.phone, addressInfoEntity.getMobile());
                holder.setText(R.id.address, addressInfoEntity.getCity() + addressInfoEntity.getAddress());
                if (addressInfoEntity.getIs_default().equals("1")) {
                    Drawable drawable = getResources().getDrawable(R.drawable.glshdz_xuanzhong);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    ((TextView) holder.getView(R.id.default_address)).setCompoundDrawables(drawable, null, null, null);
                    holder.getView(R.id.default_address).setClickable(false);
                } else {
                    Drawable drawable = getResources().getDrawable(R.drawable.glshdz_wxz);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    ((TextView) holder.getView(R.id.default_address)).setCompoundDrawables(drawable, null, null, null);
                    holder.getView(R.id.default_address).setClickable(true);

                    holder.getView(R.id.default_address).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deleteDialog = new SweetAlertDialog(AddressListActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("确认设置为默认收货地址吗？")
                                    .setConfirmText("确认")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                                            addressId = addressInfoEntity.getId();
                                            updatedefaultAddress(position);
                                            sweetAlertDialog.dismissWithAnimation();

                                        }
                                    });
                            deleteDialog.show();


                        }

                    });
                }


                holder.getView(R.id.delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteDialog = new SweetAlertDialog(AddressListActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("确认删除该收货地址吗？")
                                .setConfirmText("确认")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        deleteAddress(addressInfoEntity);
                                        sweetAlertDialog.dismissWithAnimation();
                                    }
                                });
                        deleteDialog.setCanceledOnTouchOutside(true);
                        deleteDialog.show();
                    }
                });
                holder.getView(R.id.edit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AddressList.DataBean addressInfo = list.get(position);
                        Intent intent = new Intent(AddressListActivity.this, AddAddressActivity.class);
                        intent.putExtra("flag", "edit");
                        intent.putExtra("entity", addressInfo);
                        startActivityForResult(intent, EDITREQUESTCODE);

                    }
                });

                //仿ios侧滑删除
                holder.getView(R.id.btnDelete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteDialog = new SweetAlertDialog(AddressListActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("确认删除该收货地址吗？")
                                .setConfirmText("确认")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        deleteAddress(addressInfoEntity);
                                        sweetAlertDialog.dismissWithAnimation();
                                    }
                                });
                        deleteDialog.setCanceledOnTouchOutside(true);
                        deleteDialog.show();
                    }
                });

            }

            private void updatedefaultAddress(final int positon) {
                new DialogUtil(mContext).show();
                apiServices.defaultAddressStatus(addressId, "1")
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

                                handDefaultAddress(responseCode, positon);
                            }
                        });
            }
        };
        return adapter;


    }

    private void handDefaultAddress(ResponseCode responseCode, int position) {
        if (responseCode.getCode() == 0) {
            ToastUtils.showShortToast("默认地址设置成功");
            list.get(position).setIs_default("1");
            list.get(mSelectedPos).setIs_default("0");
            adapter.notifyItemChanged(position);
            adapter.notifyItemChanged(mSelectedPos);
            mSelectedPos = position;
        } else {
            ToastUtils.showShortToast(responseCode.getMessage());
        }
    }

    private void deleteAddress(final AddressList.DataBean addressInfoEntity) {
        addressId = addressInfoEntity.getId();
        new DialogUtil(this).show();
        apiServices.deleteAddress(addressId)
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

                        handDeleteAddress(responseCode, addressInfoEntity);
                    }
                });
    }

    private void handDeleteAddress(ResponseCode responseCode, AddressList.DataBean addressinfo) {

        if (responseCode.getCode() == 0) {
            ToastUtils.showShortToast("删除成功");
            getDatas();
        } else
            ToastUtils.showShortToast(responseCode.getMessage());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADDREQUESTCODE && resultCode == ADDREQUESTCODE) {

        } else if (requestCode == EDITREQUESTCODE && resultCode == EDITREQUESTCODE) {

        }
    }

    private void getDatas() {
        new DialogUtil(this).show();
        getAddressList();
    }

    private void getAddressList() {
        apiServices.addressList(shopId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AddressList>() {
                    @Override
                    public void onCompleted() {
                        new DialogUtil(AddressListActivity.this).dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        new DialogUtil(AddressListActivity.this).dismiss();
                        ToastUtils.showShortToast(Constant.SERVER_ERROR);
                    }

                    @Override
                    public void onNext(AddressList addressList) {
                        handAddressListResult(addressList);
                    }
                });
    }


    private void handAddressListResult(AddressList addressList) {
        if (addressList.getCode() == 0) {
            list.clear();
            list = addressList.getData();

            if (list.size() != 0) {

                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getIs_default().equals("1")) {
                        mSelectedPos = i;
                        break;
                    }
                }
            } else {


            }

            rvAddress.setVisibility(View.VISIBLE);
            allEmpty.setVisibility(View.GONE);
            rvAddress.setLayoutManager(new LinearLayoutManager(this));
            getAdapter();
            adapter.notifyDataSetChanged();
            rvAddress.setAdapter(adapter);
        } else if (addressList.getCode() == 30) {
            list.clear();
            rvAddress.setVisibility(View.GONE);
            allEmpty.setVisibility(View.VISIBLE);
            ToastUtils.showShortToast(addressList.getMessage());
        } else {
            ToastUtils.showShortToast(addressList.getMessage());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViews();
    }
}
