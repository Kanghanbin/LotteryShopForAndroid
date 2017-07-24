package com.jiuyuhulian.lotteryshop.engine.exchange;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiuyuhulian.lotteryshop.BaseFragment;
import com.jiuyuhulian.lotteryshop.OnRecycleViewItemClickListener;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.adapter.FunctionAdapter;
import com.jiuyuhulian.lotteryshop.function.EnterTicketActivity;
import com.jiuyuhulian.lotteryshop.function.ExchangeDetailActivity;
import com.jiuyuhulian.lotteryshop.function.FinanceSettingActivity;
import com.jiuyuhulian.lotteryshop.function.OperatorListActivity;
import com.jiuyuhulian.lotteryshop.function.OrderManagerActivity;
import com.jiuyuhulian.lotteryshop.function.StockQueryActivity;
import com.jiuyuhulian.lotteryshop.function.TicketActiveActivity;
import com.jiuyuhulian.lotteryshop.function.TransferInMoneyActivity;
import com.jiuyuhulian.lotteryshop.function.TransferOutMoneyActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 */
public class FunctionFragment extends BaseFragment {
    @BindView(R.id.recycleView)
    RecyclerView mRecycleView;
    private View view;
    private String[]functions={"进彩票","彩票激活","订单管理","转钱入账","转钱出账","库存查询","兑奖明细","财务设置","操作员","APP推广"};
    private int[]imgs={R.drawable.jincaipiao,R.drawable.caipiaojihuo,
            R.drawable.dingdan_guanli,R.drawable.zhuanqian_ruzhang,R.drawable.zhuanqian_chuzhang,
            R.drawable.kucun_chaxun,R.drawable.duijiang,R.drawable.caiwu_shhezhi, R.drawable.czy, R.drawable.tgapp};



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_function, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {

        mRecycleView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        //        int spanCount = 4;//跟布局里面的spanCount属性是一致的
//        int spacing = 2;//每一个矩形的间距
//        boolean includeEdge = false;//如果设置成false那边缘地带就没有间距s
//        //设置每个item间距
//        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        FunctionAdapter adapter=new FunctionAdapter(functions,imgs);
        adapter.setOnItemClickListener(new OnRecycleViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position){
                    case 0:
                        startActivity(new Intent(getActivity(), EnterTicketActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(), TicketActiveActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getActivity(), OrderManagerActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(getActivity(), TransferInMoneyActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(getActivity(), TransferOutMoneyActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(getActivity(), StockQueryActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(getActivity(), ExchangeDetailActivity.class));
                        break;
                    case 7:
                        startActivity(new Intent(getActivity(), FinanceSettingActivity.class));
                        break;
                    case 8:
                        startActivity(new Intent(getActivity(), OperatorListActivity.class));
                        break;
                }
            }
        });
        mRecycleView.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}
