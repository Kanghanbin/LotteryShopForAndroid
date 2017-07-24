package com.jiuyuhulian.lotteryshop.function;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.jiuyuhulian.lotteryshop.LotteryShopApplication;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.adapter.ShoppingCarAdapter;
import com.jiuyuhulian.lotteryshop.local.LocalShoppingCarInfo;
import com.jiuyuhulian.lotteryshop.view.TopBar;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jiuyuhulian.lotteryshop.R.id.ck_all;

public class ShoppingCarActivity extends AppCompatActivity implements View.OnClickListener, ShoppingCarAdapter.CheckInterface, ShoppingCarAdapter.ModifyCountInterface {

    @BindView(R.id.topbar)
    TopBar topbar;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    @BindView(R.id.freight)
    TextView freight;
    @BindView(R.id.rl_bottom)
    AutoRelativeLayout rlBottom;
    @BindView(R.id.activity_shopping_car)
    AutoLinearLayout activityShoppingCar;
    @BindView(R.id.ck_all)
    CheckBox ckAll;
    @BindView(R.id.tv_settlement)
    TextView tvSettlement;
    @BindView(R.id.tv_show_price)
    TextView tvShowPrice;
    private ShoppingCarAdapter adapter;
    private List<LocalShoppingCarInfo> list;
    private boolean selected = false;
    private int count = 0;
    private boolean mSelect;
    private double totalPrice = 0.00;// 购买的商品总价
    private int totalCount = 0;// 购买的商品总数量
    private boolean flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_car);
        ButterKnife.bind(this);
        initViews();
        initListeners();

    }

    private void initListeners() {
        ckAll.setOnClickListener(this);
        tvSettlement.setOnClickListener(this);
        adapter.setCheckInterface(this);
        adapter.setModifyCountInterface(this);
    }
    private void initViews() {
        getData();
        adapter = new ShoppingCarAdapter(list, this);
       /* adapter.setOnItemClickListener(new OnRecycleViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                list.get(position).setChooosed(((CheckBox) view).isChecked());
                checkGroup(position, ((CheckBox) view).isChecked());//向外暴露接口
            }
        });*/
        topbar.setRightText("编辑");
        topbar.setCenterText("购物车(" + list.size() + ")");
        statistics();
        topbar.setOnTopBarClickListener(new TopBar.OnTopBarClickListener() {
            @Override
            public void onTopBarRightClick(View v) {
                flag = !flag;
                if (flag) {
                    topbar.setRightText("完成");
                    adapter.isShow(false);
                } else {
                    topbar.setRightText("编辑");
                    adapter.isShow(true);
                }
            }

            @Override
            public void onTopBarLeftClick(View v) {
                finish();
            }
        });

        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setAdapter(adapter);

    }

    public void getData() {
        list=LotteryShopApplication.liteOrm.query(LocalShoppingCarInfo.class);
    }

    /**
     * 遍历list集合
     *
     * @return
     */
    private boolean isAllCheck() {

        for (LocalShoppingCarInfo group : list) {
            if (!group.isChooosed())
                return false;
        }
        return true;
    }

    /**
     * 统计操作
     * 1.先清空全局计数器<br>
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作
     * 3.给底部的textView进行数据填充
     */
    public void statistics() {
        totalCount = 0;
        totalPrice = 0.00;
        for (int i = 0; i < list.size(); i++) {
            LocalShoppingCarInfo shoppingCartBean = list.get(i);
            if (shoppingCartBean.isChooosed()) {
                totalCount++;
                totalPrice += shoppingCartBean.getPrice() * shoppingCartBean.getNum();
            }
        }
        tvShowPrice.setText("￥  " +new DecimalFormat("0.00").format(totalPrice) );
        tvSettlement.setText("结算(" + totalCount + ")");
    }

    /**
     * 单选
     *
     * @param position  组元素位置
     * @param isChecked 组元素选中与否
     */
    @Override
    public void checkGroup(int position, boolean isChecked) {
        list.get(position).setChooosed(isChecked);
        if (isAllCheck())
            ckAll.setChecked(true);
        else
            ckAll.setChecked(false);

        adapter.notifyDataSetChanged();
        statistics();
    }

    @Override
    public void doIncrease(int position, View showCountView, boolean isChecked,String text) {


    }

    @Override
    public void doDecrease(int position, View showCountView, boolean isChecked,String text) {

        list.get(position).setNum(Integer.parseInt(text));
        LotteryShopApplication.liteOrm.update(list.get(position));
        adapter.notifyDataSetChanged();
        statistics();
    }

    @Override
    public void childDelete(int position) {

        LotteryShopApplication.liteOrm.delete(list.get(position));
        list.remove(list.get(position));
        adapter.notifyDataSetChanged();
        topbar.setCenterText("购物车(" + list.size() + ")");
        statistics();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //全选按钮
            case ck_all:
                if (list.size() != 0) {
                    if (ckAll.isChecked()) {
                        for (int i = 0; i < list.size(); i++) {
                            list.get(i).setChooosed(true);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        for (int i = 0; i < list.size(); i++) {
                            list.get(i).setChooosed(false);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
                statistics();
                break;
            case R.id.tv_settlement:
                Intent settleIntent=new Intent(this, SettlementCenterActivity.class);
                List<LocalShoppingCarInfo>intentList=new ArrayList<>();
                for (LocalShoppingCarInfo entity:list) {
                    if(entity.isChooosed()){
                        intentList.add(entity);
                    }
                }
                settleIntent.putExtra("total",totalPrice);
                settleIntent.putExtra("listobj", (Serializable) intentList);
                if(totalPrice==0){
                    ToastUtils.showShortToast("请至少选择一种商品");
                }else {
                    startActivity(settleIntent);
                }
                break;
        }
    }
}
