package com.jiuyuhulian.lotteryshop.view;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;

import com.jiuyuhulian.lotteryshop.R;

/**
 * Created by Admin on 2017/3/22.
 */

public class EditTextWithDeleteButton extends EditText{
    private final static String TAG = "EditTextWithDel";
    private Drawable imgAble;
    private Context mContext;

    public EditTextWithDeleteButton(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public EditTextWithDeleteButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    public EditTextWithDeleteButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        imgAble = mContext.getResources().getDrawable(R.drawable.tx_delete);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {
                setDrawable();
            }
        });
        setDrawable();
    }

    //设置删除图片
    private void setDrawable() {
        if(length() >=1)
            setCompoundDrawablesWithIntrinsicBounds(null, null, imgAble, null);
    }

    // 处理删除事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (imgAble != null && event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();
            Log.e(TAG, "eventX = " + eventX + "; eventY = " + eventY);
            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
            rect.left = rect.right - 50;
            if(rect.contains(eventX, eventY))
                setText("");
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
