package com.jiuyuhulian.lotteryshop.function;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.SizeUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.jiuyuhulian.lotteryshop.LotteryShopApplication;
import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.api.ApiServices;
import com.jiuyuhulian.lotteryshop.config.Constant;
import com.jiuyuhulian.lotteryshop.entity.PrizeEntity;
import com.jiuyuhulian.lotteryshop.local.LocalShoppingCarInfo;
import com.jiuyuhulian.lotteryshop.model.LotteryDetail;
import com.jiuyuhulian.lotteryshop.rest.RetrofitHttp;
import com.jiuyuhulian.lotteryshop.utils.DialogUtil;
import com.jiuyuhulian.lotteryshop.view.BizierEvaluator2;
import com.jiuyuhulian.lotteryshop.view.TopBar;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.AutoFrameLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LotteryDetailsActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    TopBar topbar;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.label)
    ImageView label;
    @BindView(R.id.frame)
    AutoFrameLayout frame;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.tv_zhang)
    TextView tvZhang;
    @BindView(R.id.tv_ben)
    TextView tvBen;
    @BindView(R.id.tv_addcar)
    TextView tvAddcar;
    @BindView(R.id.rv_prizes)
    RecyclerView rvPrizes;
    @BindView(R.id.tvdesc)
    TextView tvdesc;


    private int cars;
    private List<LocalShoppingCarInfo> activityShopCar = new ArrayList<>();
    private int lotteryId;
    private List<PrizeEntity> prizes = new ArrayList<>();
    private int startPos[];
    private int endPos[];
    private ApiServices apiServices;
    private LotteryDetail mLotterDetail;
    private LocalShoppingCarInfo local;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery_details);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        mContext = this;
        startPos = new int[2];
        endPos = new int[2];
        lotteryId = getIntent().getIntExtra("lottery_id", 0);
        RequestLotteryDetail();
        //img.setImageResource(shopcar.getImg());

        topbar.setOnTopBarClickListener(new TopBar.OnTopBarClickListener() {
            @Override
            public void onTopBarRightClick(View v) {
                Intent intent = new Intent(LotteryDetailsActivity.this, ShoppingCarActivity.class);
                startActivity(intent);
            }

            @Override
            public void onTopBarLeftClick(View v) {
                finish();
            }
        });

    }

    private void RequestLotteryDetail() {
        new DialogUtil(this).show();
        apiServices = RetrofitHttp.getApiServiceWithToken();
        if (lotteryId != 0) {
            apiServices.lotteryDetail(lotteryId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<LotteryDetail>() {
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
                        public void onNext(LotteryDetail lotteryDetail) {
                            handDetailResult(lotteryDetail);
                        }
                    });
        }
    }

    private void handDetailResult(LotteryDetail lotteryDetail) {
        this.mLotterDetail = lotteryDetail;
        if (lotteryDetail.getCode() == 0) {
            Glide.with(this)
                    .load(Constant.IMG_URL + lotteryDetail.getData().getImg())
                    .centerCrop()
                    .placeholder(R.drawable.zhanwei_detail)
                    .crossFade()
                    .into((img));
            if (mLotterDetail.getData().getSell_status().equals("爆")) {
                label.setVisibility(View.VISIBLE);
                label.setImageResource(R.drawable.jcp_huobao);
            } else if (mLotterDetail.getData().getSell_status().equals("新")) {
                label.setVisibility(View.VISIBLE);
                label.setImageResource(R.drawable.jcp_zuixin);
            } else {
                label.setVisibility(View.GONE);
            }
            tvBen.setText("￥" + new DecimalFormat("0.00").format(lotteryDetail.getData().getMoney()));
            tvZhang.setText("￥" + new DecimalFormat("0.00").format(lotteryDetail.getData().getPrice()));
            name.setText(lotteryDetail.getData().getLottery());
            tvdesc.setText(lotteryDetail.getData().getDesc());

            setPrizeUi();

        } else {
            ToastUtils.showShortToast(lotteryDetail.getMessage());
        }


    }

    private void setPrizeUi() {
        rvPrizes.setLayoutManager(new LinearLayoutManager(this));
        rvPrizes.setAdapter(new CommonAdapter<String>(this, R.layout.layout_item_prizes, mLotterDetail.getData().getRank()) {
            @Override
            protected void convert(ViewHolder holder, String prizeEntity, int position) {
                if (position % 2 == 0) {
                    holder.setBackgroundRes(R.id.prize, R.drawable.jxbj);
                } else {
                    holder.setBackgroundRes(R.id.prize, R.drawable.xhsbj);
                }
                holder.setText(R.id.prize_id, position + 1 + "");
                holder.setText(R.id.prize_num, prizeEntity + "元");
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        getShopCarDB();
    }

    @OnClick(R.id.tv_addcar)
    public void addShopCar(View v) {

        if (mLotterDetail.getData().getNum() != 0) {
            if (exitShopCarEntity(v)) {

            } else {
                local = new LocalShoppingCarInfo(lotteryId, mLotterDetail.getData().getMoney(), mLotterDetail.getData().getLottery(),
                        1, mLotterDetail.getData().getImg(), "爆", true);
                //activityShopCar.add(local);
                //添加至本地数据库
                LotteryShopApplication.liteOrm.save(local);
                ToastUtils.showShortToast(mLotterDetail.getData().getLottery() + "已加入购物车");
                v.getLocationInWindow(startPos);
                addAnimation();
            }

        } else {
            ToastUtils.showShortToast("当前库存数为0，暂时无法购买");
        }


    }

    /**
     * 添加购物车动画,动画结束后更改购物车数量
     */
    private void addAnimation() {
        final ImageView mImg = new ImageView(this);
        mImg.setImageResource(R.drawable.xin);
        mImg.setScaleType(ImageView.ScaleType.MATRIX);
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        rootView.addView(mImg);

        topbar.getmRightIv().getLocationInWindow(endPos);
        /*动画开始位置，也就是按钮的位置;动画结束的位置，也就是购物车的位置 */
        Point startPosition = new Point(startPos[0], startPos[1]);
        Point endPosition = new Point(endPos[0] + topbar.getmRightIv().getWidth() / 2, endPos[1] + topbar.getmRightIv().getHeight() / 2);
        int pointX = (startPosition.x + endPosition.x) / 2 - SizeUtils.dp2px(100);
        int pointY = startPosition.y - SizeUtils.dp2px(100);
        Point controllPoint = new Point(pointX, pointY);
 /*
        * 属性动画，依靠TypeEvaluator来实现动画效果，其中位移，缩放，渐变，旋转都是可以直接使用
        * 这里是自定义了TypeEvaluator， 我们通过point记录运动的轨迹，然后，物品随着轨迹运动，
        * 一旦轨迹发生变化，就会调用addUpdateListener这个方法，我们不断的获取新的位置，是物品移动
        */
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new BizierEvaluator2(controllPoint), startPosition, endPosition);
        valueAnimator.setDuration(500);
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Point point = (Point) valueAnimator.getAnimatedValue();
                mImg.setX(point.x);
                mImg.setY(point.y);
            }
        });

        /**
         * 动画结束，移除掉小圆圈
         */
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ViewGroup rootView = (ViewGroup) LotteryDetailsActivity.this.getWindow().getDecorView();
                rootView.removeView(mImg);
            }
        });
        //更改购物车数量
        getShopCarDB();


    }


    /**
     * @return 判断购物车中是否含有该商品种类
     */
    private boolean exitShopCarEntity(View view) {
        //防止购物车界面改动后数据库数据未更新
        getShopCarDB();
        if (activityShopCar.size() != 0) {
            for (LocalShoppingCarInfo entity : activityShopCar) {
                if (entity.getLottery_id() == lotteryId) {

                    if (entity.getNum() >= mLotterDetail.getData().getNum()) {
                        ToastUtils.showShortToast("购物车所含" + mLotterDetail.getData().getLottery() + "彩票数不能超过库存数" + mLotterDetail.getData().getNum());
                    } else {
                        entity.setNum(entity.getNum() + 1);
                        LotteryShopApplication.liteOrm.update(entity);
                        ToastUtils.showShortToast(mLotterDetail.getData().getLottery() + "已加入购物车");
                        view.getLocationInWindow(startPos);
                        addAnimation();
                    }

                    return true;
                }

            }
        }
        return false;
    }

    private void getShopCarDB() {
        activityShopCar = LotteryShopApplication.liteOrm.query(LocalShoppingCarInfo.class);
        int count = 0;
        for (LocalShoppingCarInfo local : activityShopCar) {
            count = count + local.getNum();
        }
        cars = count;
        if (cars == 0) {
            topbar.setShowMessage(false);
        } else {
            topbar.setShowMessage(true);
            topbar.setMessageText(cars + "");
        }
    }
}
