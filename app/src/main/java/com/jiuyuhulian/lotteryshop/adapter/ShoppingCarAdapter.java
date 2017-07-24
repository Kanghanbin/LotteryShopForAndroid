package com.jiuyuhulian.lotteryshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.jiuyuhulian.lotteryshop.OnRecycleViewItemClickListener;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.config.Constant;
import com.jiuyuhulian.lotteryshop.local.LocalShoppingCarInfo;
import com.jiuyuhulian.lotteryshop.view.NumberButton;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Admin on 2017/3/8.
 */

public class ShoppingCarAdapter extends RecyclerView.Adapter<ShoppingCarAdapter.ShoppingCarViewHolder> {

    private CheckInterface checkInterface;
    private ModifyCountInterface modifyCountInterface;
    private List<LocalShoppingCarInfo> list;
    private Context context;

    private boolean isShow = true;//是否显示编辑/完成


    public ShoppingCarAdapter(List<LocalShoppingCarInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ShoppingCarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopingcar_dapter_item, parent, false);
        ShoppingCarViewHolder vh = new ShoppingCarViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ShoppingCarViewHolder holder, final int position) {

        Glide.with(context)
                .load(Constant.IMG_URL+list.get(position).getImg())
                .centerCrop()
                // .placeholder(R.drawable.loading_spinner)
                // .crossFade()
                .into( holder.imgTicket);
       // holder.imgTicket.setImageResource(list.get(position).getImg());
        holder.ticketPrice.setText("￥" + new DecimalFormat("0.00").format(list.get(position).getPrice()));
        holder.ticketNum.setText("X" + list.get(position).getNum());
        holder.ticketName.setText(list.get(position).getName());
        holder.imgSelect.setChecked(list.get(position).isChooosed());


        //点击区域放大
        holder.setOnItemClickListener(new OnRecycleViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                holder.imgSelect.setChecked(!holder.imgSelect.isChecked());
                list.get(position).setChooosed((holder.imgSelect).isChecked());
                checkInterface.checkGroup(position,(holder.imgSelect).isChecked());//向外暴露接口
            }
        });
        //单选框按钮
        holder.imgSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(position).setChooosed(((CheckBox) v).isChecked());
                checkInterface.checkGroup(position, ((CheckBox) v).isChecked());//向外暴露接口
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SweetAlertDialog deleteDialog =
                        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("您确定吗?")
                                .setContentText("您确定要将这些商品从购物车中移除吗？")
                                .setConfirmText("确定")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        modifyCountInterface.childDelete(position);
                                        sweetAlertDialog.dismissWithAnimation();
                                    }
                                });
                deleteDialog.setCanceledOnTouchOutside(true);
                deleteDialog.show();
            }
        });

        holder.numberButton.setOnChangeListener(new NumberButton.OnChangeListener() {
            @Override
            public void onChanged(String num) {
                holder.ticketNum.setText(num);
                modifyCountInterface.doDecrease(position, holder.ticketNum, holder.imgSelect.isChecked(), num);


            }
        });
        holder.numberButton.setBuyMax(15)
                .setInventory(20)
                .setCurrentNumber(list.get(position).getNum())
                .setOnWarnListener(new NumberButton.OnWarnListener() {
                    @Override
                    public void onWarningForInventory(int inventory) {
                        ToastUtils.showShortToast("当前库存:" + inventory);
                    }

                    @Override
                    public void onWarningForBuyMax(int buyMax) {
                        ToastUtils.showShortToast("超过最大购买数:" + buyMax);
                    }
                });

        //判断是否在编辑状态下
        if (isShow) {
            holder.ticketName.setVisibility(View.VISIBLE);
            holder.numberButton.setVisibility(View.GONE);
            holder.imgDelete.setVisibility(View.GONE);
        } else {
            holder.ticketName.setVisibility(View.INVISIBLE);
            holder.numberButton.setVisibility(View.VISIBLE);
            holder.imgDelete.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    /**
     * 单选接口
     *
     * @param checkInterface
     */
    public void setCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }

    /**
     * 改变商品数量接口
     *
     * @param modifyCountInterface
     */
    public void setModifyCountInterface(ModifyCountInterface modifyCountInterface) {
        this.modifyCountInterface = modifyCountInterface;
    }

    /**
     * 是否显示可编辑
     *
     * @param flag
     */
    public void isShow(boolean flag) {
        isShow = flag;
        notifyDataSetChanged();
    }


    /**
     * 复选框接口
     */
    public interface CheckInterface {
        /**
         * 组选框状态改变触发的事件
         *
         * @param position  元素位置
         * @param isChecked 元素选中与否
         */
        void checkGroup(int position, boolean isChecked);
    }

    /**
     * 改变数量的接口
     */
    public interface ModifyCountInterface {
        /**
         * 增加操作
         *
         * @param position      组元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doIncrease(int position, View showCountView, boolean isChecked, String text);

        /**
         * 删减操作
         *
         * @param position      组元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doDecrease(int position, View showCountView, boolean isChecked, String text);

        /**
         * 删除子item
         *
         * @param position
         */
        void childDelete(int position);
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ShoppingCarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.img_select)
        CheckBox imgSelect;
        @BindView(R.id.img_ticket)
        ImageView imgTicket;
        @BindView(R.id.ticket_name)
        TextView ticketName;
        @BindView(R.id.number_button)
        NumberButton numberButton;
        @BindView(R.id.img_delete)
        ImageView imgDelete;
        @BindView(R.id.ticket_price)
        TextView ticketPrice;
        @BindView(R.id.ticket_num)
        TextView ticketNum;
        @BindView(R.id.view)
        View view;

        OnRecycleViewItemClickListener listener;

        public ShoppingCarViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            ButterKnife.bind(this, view);

        }

        public void setOnItemClickListener(OnRecycleViewItemClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClick(view, getAdapterPosition());
            }
        }
    }


}
