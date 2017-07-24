package com.jiuyuhulian.lotteryshop.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.config.Constant;
import com.jiuyuhulian.lotteryshop.model.MineOrder;

/**
 * Created by khb on 2017/5/8.
 */

public class LotteryOrderView extends RelativeLayout {

    ImageView ticketSrc;
    TextView ticketName;
    TextView tvPrice;
    TextView tvNum;
    View subline;

    private MineOrder.DataBean.OrderLotteryBean orderLoterBean;
    private boolean show;

    public LotteryOrderView(Context context, MineOrder.DataBean.OrderLotteryBean bean,boolean showSubline) {
        super(context);
        this.orderLoterBean=bean;
        this.show=showSubline;
        initViews(context);
    }

    private void initViews(Context context) {
        View view=LayoutInflater.from(context).inflate(R.layout.layout_item_lotterys, this);
        ticketSrc= (ImageView) view.findViewById(R.id.ticket_src);
        ticketName= (TextView) view.findViewById(R.id.ticket_name);
        tvPrice= (TextView) view.findViewById(R.id.tv_price);
        tvNum= (TextView) view.findViewById(R.id.tv_num);
        subline= view.findViewById(R.id.subline);
        Glide.with(context).load(Constant.IMG_URL+orderLoterBean.getImg())
                .placeholder(R.drawable.zhanwei_order).centerCrop().into(ticketSrc);
        tvPrice.setText("￥"+orderLoterBean.getMoney());
        tvNum.setText("X"+orderLoterBean.getQuantity());
        ticketName.setText(orderLoterBean.getLottery());
        if(show){
            subline.setVisibility(VISIBLE);
        }else {
            subline.setVisibility(INVISIBLE);
        }
    }


}
