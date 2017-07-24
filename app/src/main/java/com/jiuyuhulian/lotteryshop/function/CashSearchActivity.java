package com.jiuyuhulian.lotteryshop.function;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.blankj.utilcode.utils.SPUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.api.ApiServices;
import com.jiuyuhulian.lotteryshop.config.Constant;
import com.jiuyuhulian.lotteryshop.model.CashDetail;
import com.jiuyuhulian.lotteryshop.rest.RetrofitHttp;
import com.jiuyuhulian.lotteryshop.view.SearchView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CashSearchActivity extends AppCompatActivity implements SearchView.SearchViewListener {

    @BindView(R.id.main_search_layout)
    SearchView searchView;
    @BindView(R.id.main_rv_search_results)
    RecyclerView rv_result;

    private CommonAdapter adpter;
    private List<CashDetail.DataBean> list;

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
        searchView.setHint("请输入您要搜索的彩票");
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
        apiServices.cashSearch(shopId,text,1).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CashDetail>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShortToast(Constant.SERVER_ERROR);
                    }

                    @Override
                    public void onNext(CashDetail cashDetail) {
                        handleSearchResult(cashDetail);
                    }
                });
    }
    private void handleSearchResult(CashDetail cashDetail) {
        if(cashDetail.getCode()==0){
            list=cashDetail.getData();
            rv_result.setVisibility(View.VISIBLE);
            rv_result.setLayoutManager(new LinearLayoutManager(this));
            adpter=new CommonAdapter<CashDetail.DataBean>(mContext,R.layout.exchange_detail_adapter_item,list) {
                @Override
                protected void convert(ViewHolder holder, CashDetail.DataBean o, int position) {

                    if(o.getWin_money()==0){
                        holder.setText(R.id.tv_status,"未中奖");
                    }else{
                        holder.setText(R.id.tv_status,"已中奖");
                    }
                    Glide.with(mContext).load(Constant.IMG_URL+o.getImg()).placeholder(R.drawable.cashdetail).centerCrop().into((ImageView) holder.getView(R.id.img));
                    holder.setText(R.id.tv_time,o.getEncash_time());
                    holder.setText(R.id.tv_day,o.getDay());
                    holder.setText(R.id.tv_price,o.getPrice()+"元");
                    holder.setText(R.id.tv_name,o.getLottery());
                }

            };
            rv_result.setAdapter(adpter);
            adpter.notifyDataSetChanged();
        }else if(cashDetail.getCode()==Constant.NO_DATA){
            ToastUtils.showShortToast("抱歉，没有找到您要搜索的兑奖明细");
            rv_result.setVisibility(View.GONE);
        }else {
            ToastUtils.showShortToast(cashDetail.getMessage());
        }
    }
}
