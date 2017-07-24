package com.jiuyuhulian.lotteryshop.function;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.entity.ChargeRecordsEntity;
import com.jiuyuhulian.lotteryshop.view.TopBar;

public class ChargeRecordsActivity extends AppCompatActivity {

    private List<ChargeRecordsEntity> list;

    @BindView(R.id.topbar)
    TopBar topbar;
    @BindView(R.id.rv_recodes)
    XRecyclerView rvRecodes;
    @BindView(R.id.activity_charge_jilu)
    LinearLayout activityChargeJilu;
    CommonAdapter<ChargeRecordsEntity> commonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge_recodes);
        ButterKnife.bind(this);
        setViews();
    }

    private void setViews() {
        list = new ArrayList<>();
        Intent from = getIntent();
        String fromAct = from.getStringExtra("from");

        if (fromAct.equals("transerin")) {
            topbar.setCenterText("充值记录");
            getChargeData();

            commonAdapter = new CommonAdapter<ChargeRecordsEntity>(this, R.layout.charge_record_adapter_item, list) {
                @Override
                protected void convert(ViewHolder holder, ChargeRecordsEntity chargeRecordsEntity, int position) {
                    holder.setImageResource(R.id.img, chargeRecordsEntity.getImg());
                    holder.setText(R.id.tv_bankname, chargeRecordsEntity.getBankName());
                    holder.setText(R.id.tv_time, chargeRecordsEntity.getTime());
                    holder.setText(R.id.tv_num, chargeRecordsEntity.getBankNum() + "");
                    if(chargeRecordsEntity.isReview()){
                        holder.setText(R.id.tv_review,"支付成功");
                    }else {
                        holder.setText(R.id.tv_review,"支付失败");
                    }

                }
            };
        } else {
            topbar.setCenterText("提现记录");
            getTixianData();

            commonAdapter = new CommonAdapter<ChargeRecordsEntity>(this, R.layout.charge_record_adapter_item, list) {
                @Override
                protected void convert(ViewHolder holder, ChargeRecordsEntity chargeRecordsEntity, int position) {
                    holder.setImageResource(R.id.img, chargeRecordsEntity.getImg());
                    holder.setText(R.id.tv_bankname, chargeRecordsEntity.getBankName());
                    holder.setText(R.id.tv_time, chargeRecordsEntity.getTime());
                    holder.setText(R.id.tv_num, chargeRecordsEntity.getBankNum() + "");
                    if(chargeRecordsEntity.isReview()){
                        holder.setText(R.id.tv_review,"已审核");
                    }else {
                        holder.setText(R.id.tv_review,"未审核");
                    }
                }
            };
        }

        topbar.setOnTopBarClickListener(new TopBar.OnTopBarClickListener() {
            @Override
            public void onTopBarRightClick(View v) {

            }

            @Override
            public void onTopBarLeftClick(View v) {
                finish();
            }
        });

        LoadMoreWrapper<ChargeRecordsEntity> adapter = new LoadMoreWrapper<>(commonAdapter);
      /*  adapter.setLoadMoreView(R.applicationactivity_layout.charge_record_head);
        adapter.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                ToastUtils.showShortToast("点击加载更多");
            }
        });*/


        rvRecodes.setLayoutManager(new LinearLayoutManager(this));
        rvRecodes.setAdapter(adapter);
        rvRecodes.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //refresh data here
            }

            @Override
            public void onLoadMore() {
                // load more data here
            }
        });
    }

    public void getTixianData() {

        list.add(new ChargeRecordsEntity(R.drawable.txjl_gsyh, true, "2016-02-03", 200.00, "工商银行"));
        list.add(new ChargeRecordsEntity(R.drawable.txjl_nyyh, true, "2016-08-09", 890.00, "农业银行"));
        list.add(new ChargeRecordsEntity(R.drawable.txjl_zsyh, false, "2017-02-03", 60.00, "招商银行"));
        list.add(new ChargeRecordsEntity(R.drawable.txjl_jsyh, false, "2017-03-01", 160.08, "建设银行"));
        list.add(new ChargeRecordsEntity(R.drawable.txjl_gsyh, true, "2016-02-03", 200.00, "工商银行"));
        list.add(new ChargeRecordsEntity(R.drawable.txjl_nyyh, true, "2016-08-09", 890.00, "农业银行"));
        list.add(new ChargeRecordsEntity(R.drawable.txjl_zsyh, false, "2017-02-03", 60.00, "招商银行"));
        list.add(new ChargeRecordsEntity(R.drawable.txjl_jsyh, false, "2017-03-01", 160.08, "建设银行"));

        list.add(new ChargeRecordsEntity(R.drawable.txjl_gsyh, true, "2016-02-03", 200.00, "工商银行"));
        list.add(new ChargeRecordsEntity(R.drawable.txjl_nyyh, true, "2016-08-09", 890.00, "农业银行"));
        list.add(new ChargeRecordsEntity(R.drawable.txjl_zsyh, false, "2017-02-03", 60.00, "招商银行"));
        list.add(new ChargeRecordsEntity(R.drawable.txjl_jsyh, false, "2017-03-01", 160.08, "建设银行"));
        list.add(new ChargeRecordsEntity(R.drawable.txjl_gsyh, true, "2016-02-03", 200.00, "工商银行"));
        list.add(new ChargeRecordsEntity(R.drawable.txjl_nyyh, true, "2016-08-09", 890.00, "农业银行"));
        list.add(new ChargeRecordsEntity(R.drawable.txjl_zsyh, false, "2017-02-03", 60.00, "招商银行"));
        list.add(new ChargeRecordsEntity(R.drawable.txjl_jsyh, false, "2017-03-01", 160.08, "建设银行"));
        list.add(new ChargeRecordsEntity(R.drawable.txjl_gsyh, true, "2016-02-03", 200.00, "工商银行"));
        list.add(new ChargeRecordsEntity(R.drawable.txjl_nyyh, true, "2016-08-09", 890.00, "农业银行"));
        list.add(new ChargeRecordsEntity(R.drawable.txjl_zsyh, false, "2017-02-03", 60.00, "招商银行"));
        list.add(new ChargeRecordsEntity(R.drawable.txjl_jsyh, false, "2017-03-01", 160.08, "建设银行"));


    }

    public void getChargeData() {
        list.add(new ChargeRecordsEntity(R.drawable.czjl_wechat, true, "2016-02-03", 200.00, "微信充值"));
        list.add(new ChargeRecordsEntity(R.drawable.czjl_yhk, true, "2016-02-03", 200.00, "银联充值"));
        list.add(new ChargeRecordsEntity(R.drawable.czjl_wechat, true, "2016-02-03", 200.00, "微信充值"));
        list.add(new ChargeRecordsEntity(R.drawable.czjl_zfb, true, "2016-02-08", 200.00, "支付宝充值"));
        list.add(new ChargeRecordsEntity(R.drawable.czjl_wechat, true, "2016-02-03", 200.00, "微信充值"));
        list.add(new ChargeRecordsEntity(R.drawable.czjl_yhk, true, "2016-02-03", 200.00, "银联充值"));
    }
}
