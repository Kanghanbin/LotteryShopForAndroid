package com.jiuyuhulian.lotteryshop.function;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.jiuyuhulian.lotteryshop.BaseFragment;
import com.jiuyuhulian.lotteryshop.EnterTicketEvent;
import com.jiuyuhulian.lotteryshop.LotteryShopApplication;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.api.ApiServices;
import com.jiuyuhulian.lotteryshop.config.Constant;
import com.jiuyuhulian.lotteryshop.local.LocalShoppingCarInfo;
import com.jiuyuhulian.lotteryshop.model.LotteryList;
import com.jiuyuhulian.lotteryshop.rest.RetrofitHttp;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Admin on 2017/3/28.
 * Fragment与Activity之间的交互可以通过Fragment.setArguments(Bundle args)以及Fragment.getArguments()来实现。
 */

public class EnterTicketFragment extends BaseFragment {
    @BindView(R.id.rv_tickets)
    RecyclerView rvTickets;
    Unbinder unbinder;
    @BindView(R.id.all_empty)
    AutoLinearLayout allEmpty;
    private View view;
    private ApiServices apiServices;
    private ArrayList<LotteryList.DataBean> listData;

    private List<LocalShoppingCarInfo> activityShopCar = new ArrayList<>();

    private Intent lotterDetail;
    private Context mContext;

    int startPos[];

    public static EnterTicketFragment newInstance(String price) {
        EnterTicketFragment frag = new EnterTicketFragment();
        Bundle args = new Bundle();
        args.putString("price", price);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_enter_ticket, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apiServices = RetrofitHttp.getApiServiceWithToken();
        startPos = new int[2];
    }

    @Override
    public void onResume() {
        super.onResume();
        initViews();
    }

    private void initViews() {
        getData();
        getShopCarDB();


    }

    private void updateUi() {
        rvTickets.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rvTickets.setAdapter(new CommonAdapter<LotteryList.DataBean>(mContext, R.layout.entrytickets_adapter_item, listData) {

            @Override
            protected void convert(ViewHolder holder, final LotteryList.DataBean enterTicketEntity, int position) {
                holder.setText(R.id.price, "￥" + new DecimalFormat("0.00").format(enterTicketEntity.getMoney()));
                holder.setText(R.id.ticket_name, enterTicketEntity.getLottery());
                if (enterTicketEntity.getSell_status().equals("爆")) {
                    holder.setVisible(R.id.enter_label, true);
                    holder.setImageResource(R.id.enter_label, R.drawable.jcp_huobao);
                } else if (enterTicketEntity.getSell_status().equals("新")) {
                    holder.setVisible(R.id.enter_label, true);
                    holder.setImageResource(R.id.enter_label, R.drawable.jcp_zuixin);
                } else {
                    holder.setVisible(R.id.enter_label, false);
                }

                holder.setOnClickListener(R.id.lottery_detail, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lotterDetail = new Intent(getActivity(), LotteryDetailsActivity.class);
                        lotterDetail.putExtra("lottery_id", enterTicketEntity.getId());
                        startActivity(lotterDetail);
                    }
                });
                Glide.with(mContext)
                        .load(Constant.IMG_URL + enterTicketEntity.getImg())
                        .centerCrop()
                        .placeholder(R.drawable.zhanwei)
                        .crossFade()
                        .into((ImageView) holder.getView(R.id.img));
                holder.getView(R.id.add_car).getLocationInWindow(startPos);
                holder.getView(R.id.add_car).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        {
                            if(enterTicketEntity.getNum()!=0){
                                if (exitShopCarEntity(v)) {

                                } else {
                                    //添加至本地数据库
                                    LocalShoppingCarInfo localshoCar = new LocalShoppingCarInfo(enterTicketEntity.getId(), enterTicketEntity.getMoney(),
                                            enterTicketEntity.getLottery(), 1, enterTicketEntity.getImg(), enterTicketEntity.getSell_status(), true);
                                    LotteryShopApplication.liteOrm.save(localshoCar);
                                    ToastUtils.showShortToast(enterTicketEntity.getLottery() + "已加入购物车");
                                    v.getLocationInWindow(startPos);
                                    EventBus.getDefault().post(new EnterTicketEvent(1, startPos));
                                }
                            }else {
                                ToastUtils.showShortToast("当前库存数为0，暂时无法购买");
                            }



                        }
                    }

                    /**
                     * @return
                     * 判断购物车中是否含有该商品种类
                     */
                    private boolean exitShopCarEntity(View view) {
                        //防止购物车界面改动后数据库数据未更新
                        getShopCarDB();
                        if (activityShopCar.size() != 0) {
                            for (LocalShoppingCarInfo entity : activityShopCar) {
                                if (entity.getLottery_id() == enterTicketEntity.getId()) {
                                    if (enterTicketEntity.getNum() <= entity.getNum() ) {
                                        ToastUtils.showShortToast("购物车所含"+enterTicketEntity.getLottery()+"彩票数不能超过库存数" + enterTicketEntity.getNum());

                                    }else {
                                        entity.setNum(entity.getNum() + 1);
                                        LotteryShopApplication.liteOrm.update(entity);
                                        ToastUtils.showShortToast(enterTicketEntity.getLottery() + "已加入购物车");
                                        view.getLocationInWindow(startPos);
                                        EventBus.getDefault().post(new EnterTicketEvent(1, startPos));
                                    }
                                    return true;
                                }


                            }
                        }
                        return false;
                    }
                });
            }
        });
    }

    private void getShopCarDB() {
        activityShopCar = LotteryShopApplication.liteOrm.query(LocalShoppingCarInfo.class);
    }


    private void getData() {
        apiServices.lotteryShopList(1, getArguments().getString("price"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LotteryList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShortToast(Constant.SERVER_ERROR);
                    }

                    @Override
                    public void onNext(LotteryList lotteryList) {
                        handleLotteryListResult(lotteryList);
                    }
                });

    }

    private void handleLotteryListResult(LotteryList lotteryList) {
        if (lotteryList.getCode() == 0) {
            allEmpty.setVisibility(View.GONE);
            listData = new ArrayList<>();
            listData = (ArrayList) lotteryList.getData();
            updateUi();
        } else if (lotteryList.getCode() == 30) {
            allEmpty.setVisibility(View.VISIBLE);
        } else {
            ToastUtils.showShortToast(lotteryList.getMessage());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
