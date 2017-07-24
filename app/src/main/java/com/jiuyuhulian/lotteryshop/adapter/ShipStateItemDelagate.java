package com.jiuyuhulian.lotteryshop.adapter;

import android.content.Context;
import android.widget.LinearLayout;

import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.model.MineOrder;
import com.jiuyuhulian.lotteryshop.view.LotteryOrderView;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by khb on 2017/4/15.
 */

public class ShipStateItemDelagate implements ItemViewDelegate<MineOrder.DataBean> {

    private Context mcontext;
    private LinearLayout llContainer;
    private List<MineOrder.DataBean.OrderLotteryBean> orderLotteryBeans;
    public ShipStateItemDelagate(Context context) {
        this.mcontext=context;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.ship_order_adapter_item;
    }

    @Override
    public boolean isForViewType( MineOrder.DataBean item, int position) {
        return item.getStatus()==2||item.getStatus()==6||item.getStatus()==8;
    }

    @Override
    public void convert(ViewHolder holder, final MineOrder.DataBean orderEntity, final int position) {
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
        holder.setText(R.id.order_id,orderEntity.getOrder_number());
        holder.setText(R.id.order_time,orderEntity.getCreate_time());
        holder.setText(R.id.order_paytime,orderEntity.getPay_time());

        switch (orderEntity.getStatus()){

            case 2:
                holder.setText(R.id.tv_status,"等待卖家发货");
                break;
            case 6:
                holder.setText(R.id.tv_status,"订单已签收");
                break;
            case 8:
                holder.setText(R.id.tv_status,"订单已关闭");
                break;
        }
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
}
