package com.jiuyuhulian.lotteryshop.function;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.utils.SPUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.api.ApiServices;
import com.jiuyuhulian.lotteryshop.config.Constant;
import com.jiuyuhulian.lotteryshop.model.CashDetail;
import com.jiuyuhulian.lotteryshop.rest.RetrofitHttp;
import com.jiuyuhulian.lotteryshop.view.TitleItemDecoration;
import com.jiuyuhulian.lotteryshop.view.TopBar;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.jiuyuhulian.lotteryshop.config.Constant.LOAD_MORE;
import static com.jiuyuhulian.lotteryshop.config.Constant.NORMAL_GET_DATA;
import static com.jiuyuhulian.lotteryshop.config.Constant.PULL_TO_REFRESH;


public class ExchangeDetailActivity extends AppCompatActivity implements XRecyclerView.LoadingListener {

    @BindView(R.id.topbar)
    TopBar topbar;
    @BindView(R.id.activity_exchange_detail)
    LinearLayout activityExchangeDetail;
    @BindView(R.id.rv_exchange_detail)
    XRecyclerView rvExchangeDetail;

    private CommonAdapter adpter;

    private List<CashDetail.DataBean>list;
    private SPUtils spUtils;
    private ApiServices apiServices;
    private String shopId;
    private Context mContext;
    private TitleItemDecoration itemDecoration;

    private int currentPage = 2;
    private int defaultPage = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_detail);
        ButterKnife.bind(this);
        initViews();

    }

    private void initViews() {
        mContext = this;
        apiServices = RetrofitHttp.getApiServiceWithToken();
        spUtils = new SPUtils("regist");
        shopId = spUtils.getString("shopid");
        rvExchangeDetail.setLayoutManager(new LinearLayoutManager(mContext));
        rvExchangeDetail.setLoadingListener(this);
        getData(defaultPage,NORMAL_GET_DATA);

        topbar.setCenterText("兑奖明细");
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
    @OnClick(R.id.et_search)
    public void searchCash(){
        startActivity(new Intent(this,CashSearchActivity.class));
    }





    /**
     * 获取数据
     */
    private void getData(int page, final String type) {

        apiServices.cashDetail(shopId,page).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CashDetail>() {
                    @Override
                    public void onCompleted() {
                        if (type.equals(LOAD_MORE)) {
                            rvExchangeDetail.loadMoreComplete();
                        } else if (type.equals(PULL_TO_REFRESH)) {
                            rvExchangeDetail.refreshComplete();
                        } else {

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (type.equals(LOAD_MORE)) {
                            rvExchangeDetail.loadMoreComplete();
                        } else if (type.equals(PULL_TO_REFRESH)) {
                            rvExchangeDetail.refreshComplete();
                        } else {

                        }
                        ToastUtils.showShortToast(Constant.SERVER_ERROR);
                    }

                    @Override
                    public void onNext(CashDetail cashDetail) {
                        handleCashDetailResult(cashDetail,type);

                    }

                });
    }
    private void handleCashDetailResult(CashDetail cashDetail,String type) {
        if(cashDetail.getCode()==0){

            if (type.equals(LOAD_MORE)) {
                currentPage++;
                list.addAll(cashDetail.getData());
               // rvExchangeDetail.removeItemDecoration(itemDecoration);
               // itemDecoration=new TitleItemDecoration(mContext,list);
             //   rvExchangeDetail.addItemDecoration(itemDecoration);
                adpter.notifyDataSetChanged();
            } else if (type.equals(PULL_TO_REFRESH)) {
                if (list != null) {
                    list.clear();
                }
                list.addAll(0, cashDetail.getData());
                rvExchangeDetail.removeItemDecoration(itemDecoration);
                itemDecoration=new TitleItemDecoration(mContext,list);
                rvExchangeDetail.addItemDecoration(itemDecoration);

                adpter.notifyDataSetChanged();
            }else {
                list=cashDetail.getData();
                itemDecoration=new TitleItemDecoration(mContext,list);
                rvExchangeDetail.addItemDecoration(itemDecoration);
                adpter=new CommonAdapter<CashDetail.DataBean>(mContext, R.layout.exchange_detail_adapter_item,list) {
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
                rvExchangeDetail.setAdapter(adpter);
            }

        }else {
            ToastUtils.showShortToast(cashDetail.getMessage());
        }
    }

    @Override
    public void onRefresh() {
        currentPage=2;
        getData(defaultPage,PULL_TO_REFRESH);
    }

    @Override
    public void onLoadMore() {
        getData(currentPage,LOAD_MORE);
    }
}
