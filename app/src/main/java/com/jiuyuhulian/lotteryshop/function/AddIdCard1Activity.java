package com.jiuyuhulian.lotteryshop.function;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;
import com.blankj.utilcode.utils.KeyboardUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.api.ApiServices;
import com.jiuyuhulian.lotteryshop.config.Constant;
import com.jiuyuhulian.lotteryshop.model.BankCardTypeList;
import com.jiuyuhulian.lotteryshop.rest.RetrofitHttp;
import com.jiuyuhulian.lotteryshop.utils.DialogUtil;
import com.jiuyuhulian.lotteryshop.view.TopBar;
import com.zhy.autolayout.AutoFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddIdCard1Activity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.topbar)
    TopBar topbar;
    @BindView(R.id.person_name)
    EditText personName;
    @BindView(R.id.et_idcard)
    EditText etIdcard;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.et_bank)
    TextView etBank;
    @BindView(R.id.container)
    AutoFrameLayout container;
    @BindView(R.id.et_kind)
    TextView etKind;


    private ApiServices apiServices;
    private Context mContext;
    private List<BankCardTypeList.DataBean> list;
    private List<String>bankNames;
    private String card_holder,card_number,bank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_id_card2);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        mContext = this;
        apiServices = RetrofitHttp.getApiServiceWithToken();
        bankNames=new ArrayList<>();
        topbar.setOnTopBarClickListener(new TopBar.OnTopBarClickListener() {
            @Override
            public void onTopBarRightClick(View v) {

            }

            @Override
            public void onTopBarLeftClick(View v) {
                finish();
            }
        });
        btnNext.setOnClickListener(this);
        etBank.setOnClickListener(this);
        etKind.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_next :
                getNeedIntent();
                Intent intent=new Intent(mContext,AddIdCard2Activity.class);
                intent.putExtra("card_holder",card_holder);
                intent.putExtra("bank",bank);
                intent.putExtra("card_number",card_number);
                startActivity(intent);
                break;

//                final ActionSheet actionSheet=new ActionSheet(this);
//                actionSheet.addMenuItem("储蓄卡").addMenuItem("信用卡").show();
//                actionSheet.setMenuListener(new ActionSheet.MenuListener() {
//                    @Override
//                    public void onItemSelected(int position, String item) {
//                        etKind.setText(item);
//                    }
//
//                    @Override
//                    public void onCancel() {
//                        actionSheet.dismiss();
//                    }
//                });

            case R.id.et_bank:
                //隐藏键盘栏
                KeyboardUtils.hideSoftInput(this);
                getBankName();
                break;

        }

    }

    private void getNeedIntent() {
        card_holder=personName.getText().toString().trim();
        card_number=etIdcard.getText().toString().trim();
        bank=etBank.getText().toString().trim();
    }

    private void ShowWheelPicker() {
        final WheelPicker wheelPicker = new WheelPicker(this);
        final FrameLayout.LayoutParams flParams = new FrameLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        flParams.gravity = Gravity.CENTER;
        container.removeAllViews();
        container.addView(wheelPicker, flParams);
        wheelPicker.setData(bankNames);
        wheelPicker.setIndicator(true);
        wheelPicker.setIndicatorColor(R.color.green);
        wheelPicker.setCurved(true);
        //空气感效果
        wheelPicker.setAtmospheric(true);
        wheelPicker.setCurtainColor(R.color.btn_orange);
        //数据是否循环展示
        wheelPicker.setCyclic(true);
        // 滚轮是否为卷曲效果
        wheelPicker.setCurved(true);
        wheelPicker.setClickable(true);
        wheelPicker.setSelectedItemTextColor(R.color.btn_orange);

        wheelPicker.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                etBank.setText(bankNames.get(position));
                container.removeView(wheelPicker);
            }
        });
    }

    private void getBankName() {
        new DialogUtil(mContext).show();
        apiServices.getBankTypeList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BankCardTypeList>() {
                    @Override
                    public void onCompleted() {
                        new DialogUtil(mContext).dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        new DialogUtil(mContext).dismiss();
                        ToastUtils.showShortToast(Constant.SERVER_ERROR);
                    }

                    @Override
                    public void onNext(BankCardTypeList bankCardTypeList) {
                        handBankCardTypeListResult(bankCardTypeList);
                    }
                });

    }

    private void handBankCardTypeListResult(BankCardTypeList bankCardTypeList) {
        if(bankCardTypeList.getCode()==0){
            list=bankCardTypeList.getData();

            for (int i = 0; i < list.size(); i++) {
                bankNames.add(list.get(i).getBank());
            }
            ShowWheelPicker();
        }else {
            ToastUtils.showShortToast(bankCardTypeList.getMessage());
        }
    }


}
