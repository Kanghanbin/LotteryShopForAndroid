package com.jiuyuhulian.lotteryshop;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by Admin on 2017/3/7.
 */

public  abstract class BaseActivity extends AppCompatActivity {
    public abstract int getContentViewId();
    private String titles[]={"兑奖","功能","我的"};

    protected abstract void initAllMembersView(Bundle savedInstanceState);
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        initAllMembersView(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
