package com.jiuyuhulian.lotteryshop.engine.exchange;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.SPUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.jiuyuhulian.lotteryshop.BaseFragment;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.activity.LoginActivity;
import com.jiuyuhulian.lotteryshop.activity.UpdatePwdActivity;
import com.jiuyuhulian.lotteryshop.activity.UpdateSaftyPwdActivity;
import com.jiuyuhulian.lotteryshop.api.ApiServices;
import com.jiuyuhulian.lotteryshop.config.Constant;
import com.jiuyuhulian.lotteryshop.model.MineShopMessage;
import com.jiuyuhulian.lotteryshop.model.ResponseCode;
import com.jiuyuhulian.lotteryshop.rest.RetrofitHttp;
import com.jiuyuhulian.lotteryshop.utils.CleanMessageUtil;
import com.jiuyuhulian.lotteryshop.utils.DialogUtil;
import com.zhy.autolayout.AutoRelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**

 */
public class MineFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.img_num)
    ImageView imgNum;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.img_name)
    ImageView imgName;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.img_idcard)
    ImageView imgIdcard;
    @BindView(R.id.tv_Idcard)
    TextView tvIdcard;
    @BindView(R.id.img_caozuoyuan)
    ImageView imgCaozuoyuan;
    @BindView(R.id.tv_caozuoyuan)
    TextView tvCaozuoyuan;
    @BindView(R.id.img_yue)
    ImageView imgYue;
    @BindView(R.id.tv_yue)
    TextView tvYue;
    @BindView(R.id.img_updatPwd)
    ImageView imgUpdatPwd;
    @BindView(R.id.tv_updatePwd)
    TextView tvUpdatePwd;
    @BindView(R.id.arl_update_pwd)
    AutoRelativeLayout arlUpdatePwd;
    @BindView(R.id.img_updateSaftyPwd)
    ImageView imgUpdateSaftyPwd;
    @BindView(R.id.tv_updateSaftyPwd)
    TextView tvUpdateSaftyPwd;
    @BindView(R.id.arl_update_saftyPwd)
    AutoRelativeLayout arlUpdateSaftyPwd;
    Unbinder unbinder;
    @BindView(R.id.img_clear_memory)
    ImageView imgClearMemory;
    @BindView(R.id.tv_clear_memory)
    TextView tvClearMemory;
    @BindView(R.id.alr_clear_memory)
    AutoRelativeLayout alrClearMemory;
    @BindView(R.id.btn_logout)
    Button btnLogout;
    @BindView(R.id.tv_shop_num)
    TextView tvShopNum;
    @BindView(R.id.tv_shop_name)
    TextView tvShopName;
    @BindView(R.id.tv_shop_idcard)
    TextView tvShopIdcard;
    @BindView(R.id.tv_operator)
    TextView tvOperator;
    @BindView(R.id.tv_shop_money)
    TextView tvShopMoney;
    private View view;
    private Context mcontext;
    private ApiServices apiServices;
    private String staffId;
    private SPUtils sp;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine, null);
        unbinder = ButterKnife.bind(this, view);
        mcontext = getActivity();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTool();

    }

    @Override
    public void onResume() {
        super.onResume();
        initViews();
    }

    private void initTool() {
        apiServices = RetrofitHttp.getApiServiceWithToken();
        sp = new SPUtils("regist");
        staffId = sp.getString("staffid", "");

    }

    private void initViews() {
        apiServices.mineShopMessage(staffId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MineShopMessage>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShortToast(Constant.SERVER_ERROR);
                    }

                    @Override
                    public void onNext(MineShopMessage mineShopMessage) {
                        handleMineShopMessage(mineShopMessage);
                    }
                });


        try {
            tvClearMemory.setText(CleanMessageUtil.getTotalCacheSize(mcontext));
        } catch (Exception e) {
            e.printStackTrace();
        }
        arlUpdatePwd.setOnClickListener(this);
        arlUpdateSaftyPwd.setOnClickListener(this);
        alrClearMemory.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
    }

    private void handleMineShopMessage(MineShopMessage mineShopMessage) {
        if (mineShopMessage.getCode() == 0) {
            tvShopNum.setText(mineShopMessage.getData().getCode());
            tvShopName.setText(mineShopMessage.getData().getUsername());
            tvShopIdcard.setText(mineShopMessage.getData().getId_card());
            tvOperator.setText(mineShopMessage.getData().getStaff());
            tvShopMoney.setText(mineShopMessage.getData().getMoney()+"元");
            sp.putString("leftMoney", mineShopMessage.getData().getMoney());
        }else if(mineShopMessage.getCode()==1002){
            sp.clear();
            ToastUtils.showShortToast("登录过期，请重新登陆");
            Intent intent = new Intent(mcontext, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else if(mineShopMessage.getCode()==1001){
            sp.clear();
            ToastUtils.showShortToast("该账号在另一台设备上登录过，请重新登陆");
            Intent intent = new Intent(mcontext, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else{
            sp.clear();
            ToastUtils.showShortToast("该账号在另一台设备上登录过，请重新登陆");
            Intent intent = new Intent(mcontext, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.arl_update_pwd:
                intent = new Intent(getActivity(), UpdatePwdActivity.class);
                startActivity(intent);
                break;
            case R.id.arl_update_saftyPwd:
                intent = new Intent(getActivity(), UpdateSaftyPwdActivity.class);
                startActivity(intent);
                break;
            case R.id.alr_clear_memory:
                new SweetAlertDialog(mcontext, SweetAlertDialog.WARNING_TYPE).setTitleText("确认清理全部缓存吗？")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                CleanMessageUtil.clearAllCache(mcontext);
                                sweetAlertDialog.dismissWithAnimation();
                                try {
                                    tvClearMemory.setText(CleanMessageUtil.getTotalCacheSize(mcontext));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                }).show();

                break;
            case R.id.btn_logout:
                new DialogUtil(mcontext).show();
                apiServices.loginout()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseCode>() {
                            @Override
                            public void onCompleted() {
                                new DialogUtil(mcontext).dismiss();

                            }

                            @Override
                            public void onError(Throwable e) {

                                ToastUtils.showShortToast(Constant.SERVER_ERROR);
                            }

                            @Override
                            public void onNext(ResponseCode loginOut) {
                                handleLogoutResult(loginOut);
                            }
                        });
                break;
        }
    }

    private void handleLogoutResult(ResponseCode loginOut) {
        if (loginOut.getCode() == 0||loginOut.getCode()==1001) {
            //清除本地数据
            sp.clear();
            Intent intent = new Intent(mcontext, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        } else {
            ToastUtils.showShortToast(loginOut.getMessage());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
