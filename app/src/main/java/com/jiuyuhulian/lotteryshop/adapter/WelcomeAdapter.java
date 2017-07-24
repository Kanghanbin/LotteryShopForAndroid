package com.jiuyuhulian.lotteryshop.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Admin on 2017/3/18.
 */


public class WelcomeAdapter extends PagerAdapter {
    private List<ImageView>list;

    public WelcomeAdapter(List<ImageView> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));
        return list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
    }
}

