package com.jiuyuhulian.lotteryshop.function;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.entity.InterflowEntity;
import com.jiuyuhulian.lotteryshop.view.ActionSheet;
import com.jiuyuhulian.lotteryshop.view.LogisticsData;
import com.jiuyuhulian.lotteryshop.view.LogisticsInformationView;
import com.jiuyuhulian.lotteryshop.view.TopBar;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class QueryInterflowActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    TopBar topbar;

    @BindView(R.id.activity_query_interflow)
    AutoLinearLayout activityQueryInterflow;
    @BindView(R.id.img_interflow)
    ImageView imgInterflow;
    @BindView(R.id.interflow_status)
    TextView interflowStatus;
    @BindView(R.id.interflow_from)
    TextView interflowFrom;
    @BindView(R.id.interflow_id)
    TextView interflowId;
    @BindView(R.id.interflow_tel)
    TextView interflowTel;
    @BindView(R.id.img_company)
    ImageView imgCompany;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.list_order_logistics)
    ListView listView;
    @BindView(R.id.logistics_InformationView)
    LogisticsInformationView logisticsInformationView;

    private List<InterflowEntity> list;
    List<LogisticsData> logisticsDataList;
    private Context context;
    private ActionSheet actionSheet;


    final int REQUEST_CODE = 0x1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_interflow);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        getData();
        context = this;
        topbar.setOnTopBarClickListener(new TopBar.OnTopBarClickListener() {
            @Override
            public void onTopBarRightClick(View v) {

            }

            @Override
            public void onTopBarLeftClick(View v) {
                finish();
            }
        });
        logisticsInformationView.setLogisticsDataList(logisticsDataList);

//        listView.setAdapter(new CommonAdapter<InterflowEntity>(this,R.layout.query_interflow_adapter_item,list) {
//            @Override
//            protected void convert(ViewHolder viewHolder, InterflowEntity item, int position) {
//                viewHolder.setText(R.id.tv_desp,item.getDesp());
//                viewHolder.setText(R.id.date,item.getDate());
//                viewHolder.setText(R.id.time,item.getTime());
//
//                if(position==0){
//                   viewHolder.setImageResource(R.id.img_interflow,R.drawable.ckwl_green);
//
//                }else {
//                    viewHolder.setImageResource(R.id.img_interflow,R.drawable.ckwl_huise);
//                }
//
//            }
//        });

    }

    public void getData() {
        logisticsDataList = new ArrayList<>();
        logisticsDataList.add(new LogisticsData().setTime("2017-1-20 07:23:06").setContext("[杭州市]浙江杭州江干公司派件员：吕敬桥  15555555551  正在为您派件正在为您派件正在为您派件正在为您派件正在为您派件正在为您派件正在为您派件"));
        logisticsDataList.add(new LogisticsData().setTime("2017-1-19 23:11:37").setContext("[杭州市]浙江杭州江干区新杭派公司派件员：吕敬桥  15555555552  正在为您派件"));
        logisticsDataList.add(new LogisticsData().setTime("2017-1-19 23:08:06").setContext("[杭州市]浙江派件员：吕敬桥  15555555553  正在为您派件"));
        logisticsDataList.add(new LogisticsData().setTime("2017-1-19 23:08:06").setContext("[杭州市]员：吕敬桥  15555555554  正在为您派件"));
        logisticsDataList.add(new LogisticsData().setTime("2017-1-20 11:23:07").setContext("[杭州市]浙江杭州江干区新杭派公司进行签收扫描，快件已被  已签收  签收，感谢使用韵达快递，期待再次为您服务"));
        logisticsDataList.add(new LogisticsData().setTime("2017-1-19 15:52:43").setContext("[泰州市]韵达快递  江苏靖江市公司收件员  已揽件"));
        logisticsDataList.add(new LogisticsData().setTime("2017-1-19 12:39:15").setContext("包裹正等待揽件"));
        logisticsDataList.add(new LogisticsData().setTime("2016-6-28 15:13:02").setContext("快件在【相城中转仓】装车,正发往【无锡分拨中心】已签收,签收人是【王漾】,签收网点是【忻州原平】"));
        logisticsDataList.add(new LogisticsData().setTime("2017-1-20 11:23:07").setContext("[杭州市]浙江杭州江干区新杭派公司进行签收扫描，快件已被  已签收  签收，感谢使用韵达快递，期待再次为您服务"));
        logisticsDataList.add(new LogisticsData().setTime("2017-1-20 06:48:37").setContext("到达目的地网点浙江杭州江干区新杭派，快件很快进行派送"));
        logisticsDataList.add(new LogisticsData().setTime("2017-1-19 23:11:37").setContext("[苏州市]江苏苏州分拨中心  已发出"));
        logisticsDataList.add(new LogisticsData().setTime("2017-1-19 23:08:06").setContext("[苏州市]快件已到达  江苏苏州分拨中心"));
        logisticsDataList.add(new LogisticsData().setTime("2017-1-19 15:52:43").setContext("[泰州市]韵达快递  江苏靖江市公司收件员  已揽件"));
        logisticsDataList.add(new LogisticsData().setTime("2017-1-19 12:39:15").setContext("菜鸟驿站代收，请及时取件，如有疑问请联系 程先生:18061208980"));
        initPermissionChecker();
        logisticsInformationView.setOnPhoneClickListener(new LogisticsInformationView.OnPhoneClickListener() {
            @Override
            public void onPhoneClick(final String phoneNumber) {
                if (actionSheet == null) {
                    actionSheet = new ActionSheet(QueryInterflowActivity.this);

                }
                actionSheet.clearAllMenu().addMenuItem(phoneNumber).show();

                actionSheet.setMenuListener(new ActionSheet.MenuListener() {
                    @Override
                    public void onItemSelected(int position, String item) {
                        //实现拨打电话的操作
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
                                + phoneNumber));
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        QueryInterflowActivity.this.startActivity(intent);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
        });
    }

    /**
     * 请求权限的回调方法
     * @param requestCode    请求码
     * @param permissions    请求的权限
     * @param grantResults   权限的结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE && PermissionChecker.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            //开启成功
        }else{
            ToastUtils.showLongToast("拨打电话授权失败，请在设置中手动开启");
        }
    }

    /**
     * 当系统为6.0以上都要手动配置权限
     * 如果想避免手动配置权限，在gradle中配置targetSdkVersion 23以下即可，不包括23
     * */
    private void initPermissionChecker() {
        if (Build.VERSION.SDK_INT >= 23) {

            //判断有没有拨打电话权限
            if (PermissionChecker.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                //请求拨打电话权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE);

            }

        }
    }
}
