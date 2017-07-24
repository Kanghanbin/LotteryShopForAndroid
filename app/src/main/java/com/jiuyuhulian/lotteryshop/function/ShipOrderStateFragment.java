package com.jiuyuhulian.lotteryshop.function;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.blankj.utilcode.utils.SPUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jiuyuhulian.lotteryshop.BaseFragment;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.api.ApiServices;
import com.jiuyuhulian.lotteryshop.config.Constant;
import com.jiuyuhulian.lotteryshop.model.MineOrder;
import com.jiuyuhulian.lotteryshop.rest.RetrofitHttp;
import com.jiuyuhulian.lotteryshop.view.LotteryOrderView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.jiuyuhulian.lotteryshop.config.Constant.LOAD_MORE;
import static com.jiuyuhulian.lotteryshop.config.Constant.NORMAL_GET_DATA;
import static com.jiuyuhulian.lotteryshop.config.Constant.PULL_TO_REFRESH;

/**
 * Created by Admin on 2017/3/8.
 */

public class ShipOrderStateFragment extends BaseFragment implements XRecyclerView.LoadingListener{
    @BindView(R.id.rv_order)
    XRecyclerView rvOrder;
    private View view;

    private List<MineOrder.DataBean> dataBeans;
    private List<MineOrder.DataBean.OrderLotteryBean> orderLotteryBeans;
    private SPUtils spUtils;
    private ApiServices apiServices;
    private String shopId;
    private Context mContext;
    private LinearLayout llContainer;


    /**
     * 请求页面
     */
    private int currentPage = 2;
    private int defaultPage=1;

    private CommonAdapter adpter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order_state, container, false);
        ButterKnife.bind(this, view);
        mContext = getActivity();
        apiServices = RetrofitHttp.getApiServiceWithToken();
        spUtils = new SPUtils("regist");
        shopId = spUtils.getString("shopid");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {
        getData(defaultPage,NORMAL_GET_DATA);
        rvOrder.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    public void getData(int page, final String type) {
        apiServices.mineOrder(shopId,2,page).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MineOrder>() {
                    @Override
                    public void onCompleted() {
                        if(type.equals(LOAD_MORE)){
                            rvOrder.loadMoreComplete();
                        }else if(type.equals(PULL_TO_REFRESH)){
                            rvOrder.refreshComplete();
                        }else {

                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if(type.equals(LOAD_MORE)){
                            rvOrder.loadMoreComplete();
                        }else if(type.equals(PULL_TO_REFRESH)){
                            rvOrder.refreshComplete();
                        }else {

                        }
                        ToastUtils.showShortToast(Constant.SERVER_ERROR);
                    }

                    @Override
                    public void onNext(MineOrder mineOrder) {
                        handShipStateResult(mineOrder,type);
                    }
                });
    }
    private void handShipStateResult(MineOrder mineOrder,String type) {
        if(mineOrder.getCode()==0){

            if(type.equals(LOAD_MORE)){
                currentPage++;
                dataBeans.addAll(mineOrder.getData());
            }else if(type.equals(PULL_TO_REFRESH)){
                if(dataBeans!=null){
                    dataBeans.clear();
                }
                dataBeans.addAll(0,mineOrder.getData());
                adpter.notifyDataSetChanged();
            }else {
                dataBeans = mineOrder.getData();
                adpter =new CommonAdapter<MineOrder.DataBean>(getActivity(), R.layout.ship_order_adapter_item, dataBeans) {
                    @Override
                    protected void convert(final ViewHolder holder, final MineOrder.DataBean orderEntity, final int position) {

                        llContainer = holder.getView(R.id.lottery_container);
                        if (llContainer.getChildCount() == 0) {

                        } else {
                            llContainer.removeAllViews();
                        }


                        orderLotteryBeans = orderEntity.getOrder_lottery();
                        for (int i = 0; i < orderLotteryBeans.size(); i++) {
                            LotteryOrderView childView;
                            if (i == orderLotteryBeans.size() - 1) {
                                childView = new LotteryOrderView(mContext, orderLotteryBeans.get(i), false);
                            } else {
                                childView = new LotteryOrderView(mContext, orderLotteryBeans.get(i), true);
                            }
                            llContainer.addView(childView);
                        }
                        holder.setText(R.id.order_id,orderEntity.getOrder_number());
                        holder.setText(R.id.order_time,orderEntity.getCreate_time());
                        holder.setText(R.id.order_paytime,orderEntity.getPay_time());
                        switch (orderEntity.getPay_type()){
                            case 1:
                                holder.setText(R.id.order_pay,"支付宝");
                                break;
                            case 2:
                                holder.setText(R.id.order_pay,"微信");
                                break;
                            case 3:
                                holder.setText(R.id.order_pay,"银行卡");
                                break;
                            case 4:
                                holder.setText(R.id.order_pay,"余额");
                                break;
                        }
                        holder.setText(R.id.num_desp, "共" + orderLotteryBeans.size() + "件商品   合计：");
                        holder.setText(R.id.tv_freight, "（含运费￥：" + orderEntity.getFreight() + ")");
                        holder.setText(R.id.tv_total, "￥" + orderEntity.getSum_money());

                    }
                };
                rvOrder.setAdapter(adpter);
            }



        }else {
            ToastUtils.showShortToast(mineOrder.getMessage());
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
