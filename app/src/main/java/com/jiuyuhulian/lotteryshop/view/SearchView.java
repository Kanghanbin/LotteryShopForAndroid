package com.jiuyuhulian.lotteryshop.view;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jiuyuhulian.lotteryshop.R;
import com.jiuyuhulian.lotteryshop.config.Constant;
import com.jiuyuhulian.lotteryshop.utils.PreferenceUtil;
import com.zhy.autolayout.AutoLinearLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * Created by Admin on 2017/3/8.
 */

public class SearchView extends LinearLayout implements View.OnClickListener {

    private SimpleDateFormat mFormat;

    /**
     * 历史记录数据
     */
    private ArrayList<String>mResults;

    /**
     * 最多显示几条历史记录
     */
    private static final int HISTORY_MAX = 5;

    /**
     * 输入框
     */
    private EditText etInput;

    /**
     * 删除键
     */
    private ImageView ivDelete;

    /**
     * 返回按钮
     */
    private Button btnBack;

    /**
     * 上下文对象
     */
    private Context mContext;

    /**
     * 弹出列表
     */
    private ListView lvTips;

    /**
     * 清空历史记录
     */
    private TextView tvClear;


    /**
     * 历史记录布局
     */
    private AutoLinearLayout allsearHistory;

    /**
     * 提示adapter （推荐adapter）
     */
    private ArrayAdapter<String> mHintAdapter;

    /**
     * 自动补全adapter 只显示名字
     */
    private ArrayAdapter<String> mAutoCompleteAdapter;

    /**
     * 搜索回调接口
     */
    private SearchViewListener mListener;

    /**
     * 设置搜索回调接口
     *
     * @param listener 监听者
     */
    public void setSearchViewListener(SearchViewListener listener) {
        mListener = listener;
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.search_layout, this);
        initViews();
    }

    public void setHint(String hint){
        etInput.setHint(hint);
    }
    private void initViews() {
        mFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        etInput = (EditText) findViewById(R.id.search_et_input);
        ivDelete = (ImageView) findViewById(R.id.search_iv_delete);
        btnBack = (Button) findViewById(R.id.search_btn_back);
        lvTips = (ListView) findViewById(R.id.search_lv_tips);
        tvClear= (TextView) findViewById(R.id.clear_history);
        allsearHistory= (AutoLinearLayout) findViewById(R.id.ll_search_history);


        mResults=new ArrayList<>();
        showSearchHistory();
        lvTips.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //set edit text
                String text = lvTips.getAdapter().getItem(i).toString();
                etInput.setText(text);
                etInput.setSelection(text.length());
                //hint list view gone and result list view show
                allsearHistory.setVisibility(View.GONE);
                notifyStartSearching(text);
            }
        });

        ivDelete.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        tvClear.setOnClickListener(this);
        etInput.addTextChangedListener(new EditChangedListener());
        etInput.setOnClickListener(this);
        etInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    allsearHistory.setVisibility(GONE);
                    notifyStartSearching(etInput.getText().toString());
                }
                return true;
            }
        });
    }

    /**
     * 通知监听者 进行搜索操作
     *
     * @param text
     */
    private void notifyStartSearching(String text) {
        if (mListener != null) {
            mListener.onSearch(etInput.getText().toString());
        }
        saveSearchHistory(text);
        //隐藏软键盘
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 设置热搜版提示 adapter
     */
    public void setTipsHintAdapter(ArrayAdapter<String> adapter) {
        this.mHintAdapter = adapter;
        if (lvTips.getAdapter() == null) {
            lvTips.setAdapter(mHintAdapter);
        }
    }

    /**
     * 设置自动补全adapter
     */
    public void setAutoCompleteAdapter(ArrayAdapter<String> adapter) {
        this.mAutoCompleteAdapter = adapter;
    }


    /**
     * 将历史记录保存至sp中，key=当前时间(20160302133455，便于排序) ，value=关键字
     *
     * @param keyWords
     */
    private void saveSearchHistory(String keyWords) {
        //保存之前要先查询sp中是否有该value的记录，有则删除.这样保证搜索历史记录不会有重复条目
        Map<String, String> historys = (Map<String, String>) PreferenceUtil.getAll(mContext, Constant.SEARCHSPNAME);
        for (Map.Entry<String, String> entry : historys.entrySet()) {
            if (keyWords.equals(entry.getValue())) {
                PreferenceUtil.remove(mContext, Constant.SEARCHSPNAME, entry.getKey());
            }
        }
        PreferenceUtil.write(mContext, Constant.SEARCHSPNAME, mFormat.format(new Date()), keyWords);
    }

    /**
     * 从SP查询搜索历史，并按时间显示
     */
    private void showSearchHistory() {
        Map<String, String> hisAll = (Map<String, String>) PreferenceUtil.getAll(mContext, Constant.SEARCHSPNAME);
        //将key排序升序
        Object[] keys = hisAll.keySet().toArray();
        Arrays.sort(keys);
        int keyLeng = keys.length;
        //这里计算 如果历史记录条数是大于 可以显示的最大条数，则用最大条数做循环条件，防止历史记录条数-最大条数为负值，数组越界
        int hisLeng = keyLeng > HISTORY_MAX ? HISTORY_MAX : keyLeng;
        for (int i = 1; i <= hisLeng; i++) {
            mResults.add(hisAll.get(keys[keyLeng - i]));
        }
        mHintAdapter=new ArrayAdapter<String>(mContext,
                android.R.layout.simple_list_item_1,mResults);
        lvTips.setAdapter(mHintAdapter);
        //如果size不为0 显示footerview
        allsearHistory.setVisibility(0 != mResults.size() ? View.VISIBLE : View.GONE);
    }
        private class EditChangedListener implements TextWatcher {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }


        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (!"".equals(charSequence.toString())) {
                ivDelete.setVisibility(VISIBLE);
                allsearHistory.setVisibility(VISIBLE);
                if (mAutoCompleteAdapter != null && lvTips.getAdapter() != mAutoCompleteAdapter) {
                    lvTips.setAdapter(mAutoCompleteAdapter);
                }
                //更新autoComplete数据
                if (mListener != null) {
                    mListener.onRefreshAutoComplete(charSequence + "");
                }
            } else {
                ivDelete.setVisibility(GONE);
                if (mHintAdapter != null) {
                    lvTips.setAdapter(mHintAdapter);
                }
                allsearHistory.setVisibility(GONE);
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_et_input:
                allsearHistory.setVisibility(VISIBLE);
                break;
            case R.id.search_iv_delete:
                etInput.setText("");
                ivDelete.setVisibility(GONE);
                break;
            case R.id.search_btn_back:
                ((Activity) mContext).finish();
                break;
            case R.id.clear_history:
                PreferenceUtil.clear(mContext, Constant.SEARCHSPNAME);
                tvClear.setVisibility(GONE);
                mResults.clear();
                mHintAdapter.notifyDataSetChanged();
                break;
        }
    }

    /**
     * search view回调方法
     */
    public interface SearchViewListener {

        /**
         * 更新自动补全内容
         *
         * @param text 传入补全后的文本
         */
        void onRefreshAutoComplete(String text);

        /**
         * 开始搜索
         *
         * @param text 传入输入框的文本
         */
        void onSearch(String text);

//        /**
//         * 提示列表项点击时回调方法 (提示/自动补全)
//         */
//        void onTipsItemClick(String text);
    }

}
