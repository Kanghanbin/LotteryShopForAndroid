package com.jiuyuhulian.lotteryshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.utils.ToastUtils;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.api.ApiServices;
import com.jiuyuhulian.lotteryshop.config.Constant;
import com.jiuyuhulian.lotteryshop.function.ConfirmPayActivity;
import com.jiuyuhulian.lotteryshop.model.MineOrder;
import com.jiuyuhulian.lotteryshop.model.ResponseCode;
import com.jiuyuhulian.lotteryshop.view.LotteryOrderView;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by khb on 2017/4/15.
 */

public class PayStateItemDelagate implements ItemViewDelegate<MineOrder.DataBean> {
    private Context mcontext;
    private List<MineOrder.DataBean>list;
    private OrderAllMultiItemTypeAdapter adapter;
    private LinearLayout llContainer;
    private List<MineOrder.DataBean.OrderLotteryBean> orderLotteryBeans;
    private ApiServices mApiServices;

    public PayStateItemDelagate(Context mcontext, OrderAllMultiItemTypeAdapter adapter, List<MineOrder.DataBean> list,ApiServices apiServices) {
        this.mcontext = mcontext;
        this.adapter = adapter;
        this.list = list;
        this.mApiServices=apiServices;
    }




    @Override
    public int getItemViewLayoutId() {
        return R.layout.pay_order_adapter_item;
    }

    @Override
    public boolean isForViewType(MineOrder.DataBean item, int position) {
        return item.getStatus()==1;
    }

    @Override
    public void convert(ViewHolder holder, final MineOrder.DataBean orderEntity, final int position) {;
        llContainer = holder.getView(R.id.lottery_container);
        if (llContainer.getChildCount() == 0) {

        } else {
            llContainer.removeAllViews();
        }
        orderLotteryBeans = orderEntity.getOrder_lottery();
        for (int i = 0; i < orderLotteryBeans.size(); i++) {
            LotteryOrderView childView;
            if (i == orderLotteryBeans.size() - 1) {
                childView = new LotteryOrderView(mcontext, orderLotteryBeans.get(i), false);
            } else {
                childView = new LotteryOrderView(mcontext, orderLotteryBeans.get(i), true);
            }
            llContainer.addView(childView);
        }
        holder.setText(R.id.order_time,orderEntity.getCreate_time());
        holder.setText(R.id.order_id,orderEntity.getOrder_number());
        holder.setText(R.id.num_desp, "共" + orderLotteryBeans.size() + "件商品   合计：");
        holder.setText(R.id.tv_freight, "（含运费￥：" + orderEntity.getFreight() + ")");
        holder.setText(R.id.tv_total, "￥" + orderEntity.getSum_money());
        holder.getView(R.id.btn_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(mcontext, SweetAlertDialog.WARNING_TYPE).setTitleText("确定要取消该订单吗？")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(final SweetAlertDialog sweetAlertDialog) {

                                mApiServices.cancleOrder(orderEntity.getOrder_id()).subscribeOn(Schedulers.io())
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
                                                if(responseCode.getCode()==0){
                                                    list.remove(position);
                                                    adapter.notifyDataSetChanged();
                                                    sweetAlertDialog.dismissWithAnimation();
                                                }else {
                                                    ToastUtils.showShortToast(responseCode.getMessage());
                                                }
                                            }
                                        });





                            }
                        }).show();
            }
        });
        holder.getView(R.id.btn_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, ConfirmPayActivity.class);
                intent.putExtra("from", 2);
                intent.putExtra("orderId",orderEntity.getOrder_id());
                intent.putExtra("total", Double.parseDouble(orderEntity.getSum_money()));
                mcontext.startActivity(intent);
            }
        });

    }
}
