package com.jiuyuhulian.lotteryshop.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuyuhulian.lotteryshop.OnRecycleViewItemClickListener;
import com.jiuyuhulian.lotteryshop.R;

/**
 * Created by Admin on 2017/3/8.
 */

public class FunctionAdapter extends RecyclerView.Adapter<FunctionAdapter.FunctionViewHolder> {
    public String[] datas;
    public int[]imgs;
    public FunctionAdapter(String[] datas,int[]imgs) {
        this.datas = datas;
        this.imgs=imgs;
    }

    private OnRecycleViewItemClickListener mOnItemClickListener = null;

    @Override
    public FunctionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.functions_adapter_item, parent, false);
        FunctionViewHolder vh = new FunctionViewHolder(view,mOnItemClickListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(FunctionViewHolder holder, int position) {
        holder.function_name.setText(datas[position]);
        holder.function_img.setImageResource(imgs[position]);
    }

    @Override
    public int getItemCount() {
        return datas.length;
    }

    public void setOnItemClickListener(OnRecycleViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class FunctionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView function_name;
        public ImageView function_img;
        private OnRecycleViewItemClickListener mOnItemClickListener = null;

        public FunctionViewHolder(View view,OnRecycleViewItemClickListener listener) {
            super(view);
            this.function_name = (TextView) view.findViewById(R.id.function_name);
            this.function_img= (ImageView) view.findViewById(R.id.function_img);
            this.mOnItemClickListener=listener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                //注意这里使用getTag方法获取数据
                mOnItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }

    }



}
