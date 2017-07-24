package com.jiuyuhulian.lotteryshop.function;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.utils.SPUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.jiuyuhulian.lotteryshop.BaseFragment;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.api.ApiServices;
import com.jiuyuhulian.lotteryshop.config.Constant;
import com.jiuyuhulian.lotteryshop.model.QueryStock;
import com.jiuyuhulian.lotteryshop.rest.RetrofitHttp;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by khb on 2017/4/12.
 */

public class StockQueryActivedFragment extends BaseFragment {
    @BindView(R.id.rv_exchange_detail)
    RecyclerView rvExchangeDetail;
    Unbinder unbinder;
    private View view;


    private List<QueryStock.DataBean>dataBeens;
    private SPUtils spUtils;
    private ApiServices apiServices;
    private String shopId;
    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_stock_state, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();

    }

    private void initViews() {
        apiServices = RetrofitHttp.getApiServiceWithToken();
        spUtils = new SPUtils("regist");
        shopId = spUtils.getString("shopid");
        getData();
    }

    private void updateUi() {
        rvExchangeDetail.setLayoutManager(new LinearLayoutManager(getActivity()));

        //使用base-adapter设置适配器
        rvExchangeDetail.setAdapter(new CommonAdapter<QueryStock.DataBean>(getActivity(), R.layout.stock_adapter_item,dataBeens) {

            @Override
            protected void convert(ViewHolder holder, QueryStock.DataBean stockQureyEntity, int position) {

                holder.setText(R.id.tv_name, stockQureyEntity.getLottery());
                holder.setText(R.id.tv_left, stockQureyEntity.getNum() + "");
                Glide.with(mContext)
                        .load(Constant.IMG_URL+stockQureyEntity.getImg())
                        .centerCrop()
                        .placeholder(R.drawable.stock_zwt)
                        .crossFade()
                        .into((ImageView) holder.getView(R.id.img));
                holder.setText(R.id.tv_status, "已激活");



            }
        });
    }


    public void getData() {
        dataBeens=new ArrayList<>();
        apiServices.stockQuery(shopId,"1").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<QueryStock>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShortToast(Constant.SERVER_ERROR);
                    }

                    @Override
                    public void onNext(QueryStock queryStock) {
                        handStockQueryResult(queryStock);
                    }
                });

    }

    private void handStockQueryResult(QueryStock queryStock) {
        if(queryStock.getCode()==0){
            dataBeens=queryStock.getData();
            updateUi();
        }else {
            ToastUtils.showShortToast(queryStock.getMessage());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
