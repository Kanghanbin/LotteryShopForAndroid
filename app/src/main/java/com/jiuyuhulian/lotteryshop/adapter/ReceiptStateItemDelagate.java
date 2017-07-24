package com.jiuyuhulian.lotteryshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.utils.ToastUtils;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.api.ApiServices;
import com.jiuyuhulian.lotteryshop.config.Constant;
import com.jiuyuhulian.lotteryshop.function.QueryInterflowActivity;
import com.jiuyuhulian.lotteryshop.model.MineOrder;
import com.jiuyuhulian.lotteryshop.model.ResponseCode;
import com.jiuyuhulian.lotteryshop.view.LotteryOrderView;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by khb on 2017/4/15.
 */

public class ReceiptStateItemDelagate implements ItemViewDelegate<MineOrder.DataBean> {
    private Context mContext;
    private LinearLayout llContainer;
    private ApiServices mApiservice;
    private List<MineOrder.DataBean> mList;
    private OrderAllMultiItemTypeAdapter mAdapter;
    private List<MineOrder.DataBean.OrderLotteryBean> orderLotteryBeans;

    public ReceiptStateItemDelagate(Context mcontext,OrderAllMultiItemTypeAdapter adapter,List<MineOrder.DataBean> list, ApiServices apiServices) {
        this.mContext = mcontext;
        this.mApiservice = apiServices;
        this.mAdapter=adapter;
        this.mList=list;
    }


    @Override
    public int getItemViewLayoutId() {
        return R.layout.receipt_order_adapter_item;
    }

    @Override
    public boolean isForViewType(MineOrder.DataBean item, int position) {
        return item.getStatus() == 5;
    }

    @Override
    public void convert(ViewHolder holder, final MineOrder.DataBean orderEntity, final int position) {


        llContainer = holder.getView(R.id.lottery_container);
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
        holder.setText(R.id.order_id, orderEntity.getOrder_number());
        holder.setText(R.id.order_time, orderEntity.getCreate_time());
        holder.setText(R.id.order_paytime, orderEntity.getPay_time());
        switch (orderEntity.getPay_type()) {
            case 1:
                holder.setText(R.id.order_pay, "支付宝");
                break;
            case 2:
                holder.setText(R.id.order_pay, "微信");
                break;
            case 3:
                holder.setText(R.id.order_pay, "银行卡");
                break;
            case 4:
                holder.setText(R.id.order_pay, "余额");
                break;
        }
        holder.setText(R.id.num_desp, "共" + orderLotteryBeans.size() + "件商品   合计：");
        holder.setText(R.id.tv_freight, "（含运费￥：" + orderEntity.getFreight() + ")");
        holder.setText(R.id.tv_total, "￥" + orderEntity.getSum_money());
        holder.getView(R.id.btn_interflow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, QueryInterflowActivity.class));
            }
        });
        holder.getView(R.id.btn_confirm_receipt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                receiptOrder(orderEntity);
            }
        });

    }

    private void receiptOrder(final MineOrder.DataBean orderEntity) {
        mApiservice.confirmReceipt(orderEntity.getOrder_id()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseCode>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        ToastUtils.showShortToast(Constant.SERVER_ERROR);
                    }

                    @Override
                    public void onNext(ResponseCode responseCode) {
                        if (responseCode.getCode() == 0) {
                            ToastUtils.showShortToast("订单已确认收货");
                            mList.remove(orderEntity);
                            mAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtils.showShortToast(responseCode.getMessage());
                        }
                    }
                });
    }

}
