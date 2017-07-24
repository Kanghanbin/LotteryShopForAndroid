package com.jiuyuhulian.lotteryshop.function;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;

import com.blankj.utilcode.utils.SPUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.adapter.OrderAllMultiItemTypeAdapter;
import com.jiuyuhulian.lotteryshop.api.ApiServices;
import com.jiuyuhulian.lotteryshop.config.Constant;
import com.jiuyuhulian.lotteryshop.model.MineOrder;
import com.jiuyuhulian.lotteryshop.rest.RetrofitHttp;
import com.jiuyuhulian.lotteryshop.view.SearchView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OrderSearchActivity extends AppCompatActivity implements SearchView.SearchViewListener {

    @BindView(R.id.main_search_layout)
    SearchView searchView;
    @BindView(R.id.main_rv_search_results)
    RecyclerView rv_result;

    private List<MineOrder.DataBean> list;

    private SPUtils spUtils;
    private ApiServices apiServices;
    private String shopId;
    private Context mContext;
    /**
     * 热搜框列表adapter
     */
    private ArrayAdapter<String> hintAdapter;

    /**
     * 自动补全列表adapter
     */
    private ArrayAdapter<String> autoCompleteAdapter;

    /**
     * 搜索结果列表adapter
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_search_order);
        ButterKnife.bind(this);
        mContext =this;
        apiServices = RetrofitHttp.getApiServiceWithToken();
        spUtils = new SPUtils("regist");
        shopId = spUtils.getString("shopid");
        initViews();

    }

    private void initViews() {

        //设置监听
        searchView.setSearchViewListener(this);
        //设置adapter
        //searchView.setTipsHintAdapter(hintAdapter);
        //searchView.setAutoCompleteAdapter(autoCompleteAdapter);

    }

    @Override
    public void onRefreshAutoComplete(String text) {

    }

    @Override
    public void onSearch(String text) {
//        //更新result数据
          getResultData(text);
    }

    private void getResultData(String text) {
        apiServices.searchOrder(shopId,text).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MineOrder>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShortToast(Constant.SERVER_ERROR);
                    }

                    @Override
                    public void onNext(MineOrder mineOrder) {
                        handleSearchResult(mineOrder);
                    }
                });
    }
    private void handleSearchResult(MineOrder mineOrder) {
        if(mineOrder.getCode()==0){
            list=mineOrder.getData();
            rv_result.setVisibility(View.VISIBLE);
            rv_result.setLayoutManager(new LinearLayoutManager(this));
            OrderAllMultiItemTypeAdapter adpter=new OrderAllMultiItemTypeAdapter(this,list,apiServices);
            rv_result.setAdapter(adpter);
            adpter.notifyDataSetChanged();
        }else if(mineOrder.getCode()==Constant.NO_DATA){
            ToastUtils.showShortToast("抱歉，没有找到您要搜索的订单");
            rv_result.setVisibility(View.GONE);
        }else {
            ToastUtils.showShortToast(mineOrder.getMessage());
        }
    }
}
