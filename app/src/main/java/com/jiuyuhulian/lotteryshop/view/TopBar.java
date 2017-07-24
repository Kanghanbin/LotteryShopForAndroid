package com.jiuyuhulian.lotteryshop.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiuyuhulian.lotteryshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admin on 2017/3/7.
 */

public class TopBar extends RelativeLayout implements View.OnClickListener {

    // 左边的图片
    @BindView(R.id.iv_top_bar_left)
    ImageView mLeftIv;

    // 左边的文字
    @BindView(R.id.tv_left_text)
    TextView mLefTv;

    // 标题
    @BindView(R.id.tv_top_bar_title)
    TextView mTitleTv;

    // 右边的文字
    @BindView(R.id.tv_top_bar_right_text)
    TextView mRirghtTv;

    // 右边的图片
    @BindView(R.id.iv_top_bar_right)
    ImageView mRightIv;

    // 如果有提醒小圆点 , 显示
    @BindView(R.id.iv_top_bar_warn)
    ImageView mWarn;


    @BindView(R.id.tv_top_message)
    TextView tvMessage;

    @BindView(R.id.tv_left_price)
    TextView tvPrice;

    private Context mContext;

    private int leftSrc;
    private String leftText;
    private String centerText;
    private String rightText;
    private int rightSrc;
    private int warnSrc;
    private boolean isShowMessage;
    private boolean isShowWarn;
    private boolean isShowLeft;
    private boolean isShowRight;
    private boolean isShowPrice;
    private boolean isBack;

    private float centerTextSize;
    private int centerTextColor;

    private View mView;

    private OnTopBarClickListener listener;


    public TopBar(Context context) {
        this(context, null);
    }

    public TopBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        this.mContext = context;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TopBar);

        leftSrc = ta.getResourceId(R.styleable.TopBar_leftSrc, R.mipmap.ic_launcher);
        leftText = ta.getString(R.styleable.TopBar_leftTopText);
        centerText = ta.getString(R.styleable.TopBar_centerText);
        rightText = ta.getString(R.styleable.TopBar_rightTopText);
        rightSrc = ta.getResourceId(R.styleable.TopBar_rightSrc, R.mipmap.ic_launcher);
        warnSrc = ta.getResourceId(R.styleable.TopBar_warnSrc, R.mipmap.ic_launcher);
        isShowMessage = ta.getBoolean(R.styleable.TopBar_isShowMessage, false);
        isShowPrice=ta.getBoolean(R.styleable.TopBar_isShowPrice,false);
        isShowWarn = ta.getBoolean(R.styleable.TopBar_isShowWarn, false);
        isShowLeft = ta.getBoolean(R.styleable.TopBar_isShowLeft, false);
        isShowRight = ta.getBoolean(R.styleable.TopBar_isShowRight, false);
        isBack = ta.getBoolean(R.styleable.TopBar_isBack, false);

        centerTextSize = ta.getDimension(R.styleable.TopBar_centerTextSize, 20);
        centerTextColor = ta.getColor(R.styleable.TopBar_centerTextColor, getResources().getColor(android.R.color.white));

        ta.recycle();

        initContentView();
        initView();
        initListener();
    }

    private void initContentView() {
        mView = View.inflate(mContext, R.layout.view_top_bar, this);
        ButterKnife.bind(this);
    }

    // 初始化页面，根据设置项显示
    private void initView() {

        mTitleTv.setTextSize(centerTextSize);
        mTitleTv.setTextColor(centerTextColor);
        mTitleTv.setText(centerText);

        if (isShowLeft) {
            mLeftIv.setVisibility(View.VISIBLE);
            mLeftIv.setImageResource(leftSrc);
        } else {
            mLeftIv.setVisibility(View.INVISIBLE);
        }

        if (isShowRight) {
            mRightIv.setVisibility(View.VISIBLE);
            mRightIv.setImageResource(rightSrc);
        } else {
            mRightIv.setVisibility(View.INVISIBLE);
        }
        // 初始化一个默认的颜色
        setBackGroundColor(-1);
        showWarn();
        showMesasge();
        showPrice();
    }

    //是否显示提醒
    private void showWarn() {
        if (isShowWarn) {
            mWarn.setImageResource(warnSrc);
            mWarn.setVisibility(View.VISIBLE);
        } else {
            mWarn.setVisibility(View.INVISIBLE);
        }
    }
    private void showMesasge(){
        if(isShowMessage){
            tvMessage.setVisibility(VISIBLE);
        }else {
            tvMessage.setVisibility(INVISIBLE);
        }
    }
    private void showPrice(){
        if(isShowPrice){
            tvPrice.setVisibility(VISIBLE);
        }else {
            tvPrice.setVisibility(INVISIBLE);
        }
    }


    private void initListener() {
        mLeftIv.setOnClickListener(this);
        mRightIv.setOnClickListener(this);
        mRirghtTv.setOnClickListener(this);
        mLefTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_top_bar_left:
                if (listener != null) {
                    listener.onTopBarLeftClick(v);
                }
                break;

            case R.id.iv_top_bar_right:
                if (listener != null) {
                    listener.onTopBarRightClick(v);
                }
                break;
            case R.id.tv_left_text:
                if (listener != null) {
                    listener.onTopBarLeftClick(v);
                }
                break;
            case R.id.tv_top_bar_right_text:
                if (listener != null) {
                    listener.onTopBarRightClick(v);
                }
                break;
        }
    }

    public void setOnTopBarClickListener(OnTopBarClickListener listener) {
        this.listener = listener;
    }

    public boolean isShowWarn() {
        return isShowWarn;
    }

    //设置是否显示提醒圆点
    public void setShowWarn(boolean isShowWarn) {
        this.isShowWarn = isShowWarn;
        showWarn();
    }
    //设置是否显示提醒圆点
    public void setShowMessage(boolean isShowMessage) {
        this.isShowMessage = isShowMessage;
        showMesasge();
    }
    //设置是否显示购物车价格
    public void setShowPrice(boolean isShowPrice) {
        this.isShowPrice = isShowPrice;
        showPrice();
    }

    public void setTitle(String title) {
        mTitleTv.setText(title);
    }

    public void setLeftSrc(int src) {
        mLeftIv.setImageResource(src);
    }

    public void setRightSrc(int src) {
        if (src == View.GONE) {
            mRightIv.setVisibility(src);
        } else {
            mRightIv.setImageResource(src);
        }
    }

    public ImageView getmRightIv(){
        return mRightIv;
    }
    public void setBackGroundColor(int resColor) {
        if (resColor == -1) {
            // 默认颜色
            setBackgroundResource(R.color.white);
        } else {
            setBackgroundResource(resColor);
        }
    }
    public void setMessageText(String messageText){
        tvMessage.setText(messageText);
    }
    public void setPriceText(String priceText){
        tvPrice.setText(priceText);
    }
    public void setLeftText(String leftText) {
        mLefTv.setText(leftText);
    }

    public void setCenterText(String centerText) {
        mTitleTv.setText(centerText);
    }

    public void setRightText(String rightText) {
        mRirghtTv.setText(rightText);
    }

    public void setmLeftIv(int mLeftIvSrc) {
        mLeftIv.setImageResource(mLeftIvSrc);
    }

    public void setmRightIv(int mRightIvSrc) {
        mRightIv.setImageResource(mRightIvSrc);
    }

    public interface OnTopBarClickListener {
        void onTopBarRightClick(View v);

        void onTopBarLeftClick(View v);
    }
}


