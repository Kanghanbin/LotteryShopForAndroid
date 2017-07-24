package com.jiuyuhulian.lotteryshop.engine.exchange.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.api.ApiServices;
import com.jiuyuhulian.lotteryshop.config.Constant;
import com.jiuyuhulian.lotteryshop.model.CashSubtotal;
import com.jiuyuhulian.lotteryshop.model.ResponseCode;
import com.jiuyuhulian.lotteryshop.rest.RetrofitHttp;
import com.jiuyuhulian.lotteryshop.view.TopBar;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.jiuyuhulian.lotteryshop.config.Constant.LOAD_MORE;
import static com.jiuyuhulian.lotteryshop.config.Constant.NORMAL_GET_DATA;
import static com.jiuyuhulian.lotteryshop.config.Constant.PULL_TO_REFRESH;

public class SubTotalActivity extends AppCompatActivity implements XRecyclerView.LoadingListener {

    @BindView(R.id.topbar)
    TopBar topbar;

    @BindView(R.id.recycleView)
    XRecyclerView recycleView;

    private View header;
    private ApiServices apiServices;
    private CommonAdapter<CashSubtotal.DataBean.LotteryBean> adpater;
    private List<CashSubtotal.DataBean.LotteryBean> list;
    private Context mContext;
    private SweetAlertDialog dialog;

    /**
     * 请求页面
     */
    private int currentPage = 2;
    private int defaultPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_total);
        ButterKnife.bind(this);
        apiServices = RetrofitHttp.getApiServiceWithToken();
        mContext = this;
        initViews();
        recycleView.setLoadingListener(this);
        initListeners();
    }

    private void initListeners() {
    }

    private void initViews() {
        initData(defaultPage, NORMAL_GET_DATA);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        topbar.setOnTopBarClickListener(new TopBar.OnTopBarClickListener() {
            @Override
            public void onTopBarRightClick(View v) {
                dialog = new SweetAlertDialog(SubTotalActivity.this, SweetAlertDialog.WARNING_TYPE).setTitleText("确定要清除全部记录吗？")
                        .setConfirmText("确定")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                apiServices.clearSubtotal().subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Subscriber<ResponseCode>() {
                                            @Override
                                            public void onCompleted() {

                                            }

                                            @Override
                                            public void onError(Throwable e) {

                                            }

                                            @Override
                                            public void onNext(ResponseCode responseCode) {

                                                if (responseCode.getCode() == 0) {
                                                    recycleView.removeView(header);
                                                    dialog.dismissWithAnimation();
                                                    recycleView.setVisibility(View.GONE);

                                                } else {
                                                    ToastUtils.showShortToast(responseCode.getMessage());
                                                }
                                            }
                                        });

                            }
                        });
                dialog.show();
            }

            @Override
            public void onTopBarLeftClick(View v) {

                finish();
            }
        });

    }

    private void initData(int page, final String type) {
        apiServices.cashSubtotal(page).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CashSubtotal>() {
                    @Override
                    public void onCompleted() {
                        if (type.equals(LOAD_MORE)) {
                            recycleView.loadMoreComplete();
                        } else if (type.equals(PULL_TO_REFRESH)) {
                            recycleView.refreshComplete();
                        } else {

                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CashSubtotal cashSubtotal) {
                        handleCashSubtotal(cashSubtotal, type);
                    }
                });
    }

    private void handleCashSubtotal(CashSubtotal cashSubtotal, String type) {


        if (cashSubtotal.getCode() == 0) {

            if (type.equals(LOAD_MORE)) {
                currentPage++;
                list.addAll(cashSubtotal.getData().getLottery());
            } else if (type.equals(PULL_TO_REFRESH)) {
                if (list != null) {
                    list.clear();
                }
                list.addAll(0, cashSubtotal.getData().getLottery());
                adpater.notifyDataSetChanged();
            } else {
                list = cashSubtotal.getData().getLottery();

                adpater = new CommonAdapter<CashSubtotal.DataBean.LotteryBean>(this, R.layout.subtotal_adapter_item, list) {

                    @Override
                    protected void convert(ViewHolder holder, CashSubtotal.DataBean.LotteryBean subTotalEntity, int position) {
                        Glide.with(mContext).load(Constant.IMG_URL + subTotalEntity.getImg()).placeholder(R.drawable.zwt_subtotal)
                                .centerCrop().into((ImageView) holder.getView(R.id.img));
                        holder.setText(R.id.tv_name, subTotalEntity.getLottery());
                        holder.setText(R.id.single_price, subTotalEntity.getPrice() + "元");
                        holder.setText(R.id.tv_win_amount, subTotalEntity.getWin_money() + "元");
                        holder.setText(R.id.tv_time, subTotalEntity.getEncash_time());
                    }
                };

                recycleView.setAdapter(adpater);
            }
            if(header!=null){
                recycleView.removeView(header);
            }else {
                header = LayoutInflater.from(this).inflate(R.layout.subtotal_header_layout, (ViewGroup) findViewById(android.R.id.content), false);
                recycleView.addHeaderView(header);
            }
            ((TextView) header.findViewById(R.id.tv_total_detail)).setText("总计：共" + cashSubtotal.getData().getSum() + "张/" + cashSubtotal.getData().getMoney() + "元");

        } else {
            ToastUtils.showShortToast(cashSubtotal.getMessage());
        }
    }

    @Override
    public void onRefresh() {
        currentPage = 2;
        initData(defaultPage, PULL_TO_REFRESH);
    }

    @Override
    public void onLoadMore() {
        initData(currentPage, LOAD_MORE);
    }
}
