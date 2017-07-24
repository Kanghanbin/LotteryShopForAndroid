package com.jiuyuhulian.lotteryshop.function;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.utils.SPUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jiuyuhulian.lotteryshop.BaseFragment;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.adapter.OrderAllMultiItemTypeAdapter;
import com.jiuyuhulian.lotteryshop.api.ApiServices;
import com.jiuyuhulian.lotteryshop.config.Constant;
import com.jiuyuhulian.lotteryshop.model.MineOrder;
import com.jiuyuhulian.lotteryshop.rest.RetrofitHttp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.jiuyuhulian.lotteryshop.config.Constant.LOAD_MORE;
import static com.jiuyuhulian.lotteryshop.config.Constant.PULL_TO_REFRESH;

/**
 * Created by Admin on 2017/3/8.
 */

public class OrderStateFragment extends BaseFragment implements XRecyclerView.LoadingListener {
    @BindView(R.id.rv_order)
    XRecyclerView rvOrder;
    private View view;
    private List<MineOrder.DataBean> list;

    private SPUtils spUtils;
    private ApiServices apiServices;
    private String shopId;
    private Context mContext;
    private OrderAllMultiItemTypeAdapter adpter;

    /**
     * 请求页面
     */
    private int currentPage = 2;
    private int defaultPage=1;


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
        getData(defaultPage,Constant.NORMAL_GET_DATA);
        rvOrder.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvOrder.setLoadingListener(this);

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void getData(int page, final String type) {
        apiServices.mineOrder(shopId, 0, page).subscribeOn(Schedulers.io())
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
                        if (mineOrder.getCode() == 0) {

                            if(type.equals(LOAD_MORE)){
                                currentPage++;
                                list.addAll(mineOrder.getData());
                            }else if(type.equals(PULL_TO_REFRESH)){
                                if(list!=null){
                                    list.clear();
                                }
                                list.addAll(0,mineOrder.getData());
                                adpter.notifyDataSetChanged();
                            }else {
                                list = mineOrder.getData();
                                adpter = new OrderAllMultiItemTypeAdapter(mContext, list, apiServices);
                                rvOrder.setAdapter(adpter);
                            }


                        }else {
                            ToastUtils.showShortToast(mineOrder.getMessage());
                        }
                    }
                });
    }




}
