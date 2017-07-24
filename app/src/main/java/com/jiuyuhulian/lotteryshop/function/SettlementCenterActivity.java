package com.jiuyuhulian.lotteryshop.function;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.SPUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.jiuyuhulian.lotteryshop.LotteryShopApplication;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.api.ApiServices;
import com.jiuyuhulian.lotteryshop.config.Constant;
import com.jiuyuhulian.lotteryshop.local.LocalShoppingCarInfo;
import com.jiuyuhulian.lotteryshop.model.AddressList;
import com.jiuyuhulian.lotteryshop.model.CreateOrderRequest;
import com.jiuyuhulian.lotteryshop.model.CreateOrderResponse;
import com.jiuyuhulian.lotteryshop.rest.RetrofitHttp;
import com.jiuyuhulian.lotteryshop.utils.DialogUtil;
import com.jiuyuhulian.lotteryshop.view.TopBar;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SettlementCenterActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.topbar)
    TopBar topbar;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.arl_add_adress)
    AutoRelativeLayout arlAddAdress;
    @BindView(R.id.freight_price)
    TextView freightPrice;
    @BindView(R.id.arl_freight)
    AutoRelativeLayout arlFreight;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.freight)
    TextView freight;
    @BindView(R.id.tv_show_price)
    TextView tvShowPrice;
    @BindView(R.id.rl_bottom)
    AutoRelativeLayout rlBottom;
    @BindView(R.id.activity_shopping_car)
    AutoLinearLayout activityShoppingCar;
    @BindView(R.id.tv_shr)
    TextView tvShr;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.img_adress)
    ImageView imgAdress;
    @BindView(R.id.address)
    TextView tvaddress;
    @BindView(R.id.arl_detail_adress)
    AutoRelativeLayout arlDetailAdress;
    private List<LocalShoppingCarInfo> list;
    private double total;

    private AddressList.DataBean defaultAddress;
    private Intent intent;
    private SPUtils spUtils;
    private ApiServices apiServices;
    private String shopId,starffId,addressId;
    private Context mContext;
    private List<AddressList.DataBean> listAddress;
    private  List<CreateOrderRequest.LotteryBean>  lotteryBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settlement_center);
        ButterKnife.bind(this);
        initViews();
        initListeners();
    }

    private void initListeners() {
        arlDetailAdress.setOnClickListener(this);
        arlAddAdress.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
    }

    private void initViews() {
        mContext = this;
        apiServices = RetrofitHttp.getApiServiceWithToken();
        spUtils = new SPUtils("regist");
        shopId = spUtils.getString("shopid");
        starffId=spUtils.getString("staffid");
        getAddressData();
        topbar.setOnTopBarClickListener(new TopBar.OnTopBarClickListener() {
            @Override
            public void onTopBarRightClick(View v) {

            }

            @Override
            public void onTopBarLeftClick(View v) {
                finish();
            }
        });
        final Intent shoppingCar = getIntent();
        list = (List<LocalShoppingCarInfo>) shoppingCar.getSerializableExtra("listobj");
        setDataBeans(list);
        total = shoppingCar.getDoubleExtra("total",0);
        freight.setText("含运费  "+new DecimalFormat("0.00").format(10)+"元");
        tvShowPrice.setText("￥  "+ new DecimalFormat("0.00").format(total+10));
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setAdapter(new CommonAdapter<LocalShoppingCarInfo>(this, R.layout.shopingcar_dapter_item, list) {
            @Override
            protected void convert(final ViewHolder holder, final LocalShoppingCarInfo shoppingCarEntity, int position) {

                holder.setText(R.id.ticket_name, shoppingCarEntity.getName());
                holder.setText(R.id.ticket_price, "￥" +  new DecimalFormat("0.00").format(shoppingCarEntity.getPrice()));
                holder.setText(R.id.ticket_num, "X" + shoppingCarEntity.getNum());
                // holder.setImageResource(R.id.img_ticket, shoppingCarEntity.getImg());
                Glide.with(mContext)
                        .load(Constant.IMG_URL + shoppingCarEntity.getImg())
                        .centerCrop()
                        // .placeholder(R.drawable.loading_spinner)
                        // .crossFade()
                        .into((ImageView) holder.getView(R.id.img_ticket));
                holder.getView(R.id.img_select).setVisibility(View.GONE);

            }
        });


    }

    private void setDataBeans(List<LocalShoppingCarInfo> list) {
        lotteryBeans=new ArrayList<>();
        for (int i=0;i<list.size();i++){
            lotteryBeans.add(new CreateOrderRequest.LotteryBean(
                    list.get(i).getLottery_id()+"",list.get(i).getPrice()+"",list.get(i).getNum()+""));
        }
    }

    /**
     *获取默认地址
     */
    private void getAddressData() {

        apiServices.addressList(shopId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AddressList>() {
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
                    public void onNext(AddressList addressList) {
                        handAddressListResult(addressList);
                    }
                });


    }

    private void handAddressListResult(AddressList addressList) {
        if (addressList.getCode() == 0) {
            listAddress = addressList.getData();
            //取出默认地址
            getDefaultAddress();
            name.setText(defaultAddress.getUsername());
            phone.setText(defaultAddress.getMobile());
            tvaddress.setText(defaultAddress.getCity()+defaultAddress.getAddress());
            arlAddAdress.setVisibility(View.GONE);
            arlDetailAdress.setVisibility(View.VISIBLE);

        } else if (addressList.getCode() == 30) {
            arlAddAdress.setVisibility(View.VISIBLE);
            arlDetailAdress.setVisibility(View.GONE);
        } else {
            ToastUtils.showShortToast(addressList.getMessage());
        }
    }

    private void getDefaultAddress() {
        for (AddressList.DataBean address : listAddress) {
            if (address.getIs_default().equals("1")) {
                defaultAddress = address;
                addressId=address.getId();
                break;
            } else {

            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_submit:

                CreateOrderRequest request=new CreateOrderRequest(shopId,lotteryBeans,total+10+"","10",addressId,starffId);
                new DialogUtil(mContext).show();
                apiServices.createOrder(request).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<CreateOrderResponse>() {
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
                            public void onNext(CreateOrderResponse createOrderResponse) {
                                handCreateResult(createOrderResponse);
                            }
                        });


                break;
            case R.id.arl_add_adress:
                intent = new Intent(this, AddAddressActivity.class);
                intent.putExtra("flag", "add");
                startActivity(intent);
                break;
            case R.id.arl_detail_adress:
                intent = new Intent(this, AddressListActivity.class);
               /* AddressInfoEntity address = new AddressInfoEntity(name.getText().toString().trim(),phone.getText().toString().trim(),
                     tvaddress.getText().toString().trim() ,detailAddress.getText().toString().trim() );
                intent.putExtra("entity",address);*/
                startActivity(intent);
                break;
        }
    }

    private void handCreateResult(CreateOrderResponse createOrderResponse) {
        if(createOrderResponse.getCode()==0){
            ToastUtils.showShortToast("订单创建成功");

            //下单成功删除购物车商品
            for(LocalShoppingCarInfo shopcarInfo:list){
                LotteryShopApplication.liteOrm.delete(shopcarInfo);
            }
            Intent submitIntent = new Intent(this, ConfirmPayActivity.class);
            submitIntent.putExtra("from", 1);
            submitIntent.putExtra("total",total);
            submitIntent.putExtra("createOrder",createOrderResponse.getData());
            startActivity(submitIntent);



        }else {
            ToastUtils.showShortToast(createOrderResponse.getMessage());
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getAddressData();
    }
}
