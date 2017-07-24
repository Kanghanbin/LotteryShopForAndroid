package com.jiuyuhulian.lotteryshop.adapter;

import android.content.Context;

import com.jiuyuhulian.lotteryshop.api.ApiServices;
import com.jiuyuhulian.lotteryshop.model.MineOrder;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by khb on 2017/4/15.
 */

public class OrderAllMultiItemTypeAdapter extends MultiItemTypeAdapter<MineOrder.DataBean>{



    public OrderAllMultiItemTypeAdapter(Context context, List<MineOrder.DataBean> datas, ApiServices apiServices) {
        super(context, datas);
        addItemViewDelegate(new PayStateItemDelagate(context,this,datas,apiServices));
        addItemViewDelegate(new ShipStateItemDelagate(context));
        addItemViewDelegate(new ReceiptStateItemDelagate(context,this,datas,apiServices));

    }
}
