package com.jiuyuhulian.lotteryshop.function;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zhy.autolayout.AutoLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.view.TopBar;

public class DuijiangLimitActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    TopBar topbar;
    @BindView(R.id.et_ticket_num)
    EditText etTicketNum;
    @BindView(R.id.btn_Active)
    Button btnActive;
    @BindView(R.id.activity_ticke_numt_active)
    AutoLinearLayout activityTickeNumtActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duijiang_limit);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        topbar.setOnTopBarClickListener(new TopBar.OnTopBarClickListener() {
            @Override
            public void onTopBarRightClick(View v) {

            }

            @Override
            public void onTopBarLeftClick(View v) {
                finish();
            }
        });
    }
}
