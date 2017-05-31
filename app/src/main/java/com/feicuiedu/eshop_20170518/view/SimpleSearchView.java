package com.feicuiedu.eshop_20170518.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feicuiedu.eshop_20170518.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 张志龙 on 2017/5/27.
 */

public class SimpleSearchView extends LinearLayout implements TextWatcher, TextView.OnEditorActionListener {
    @BindView(R.id.button_search)
    ImageButton mButtonSearch;
    @BindView(R.id.edit_query)
    EditText mEditQuery;
    @BindView(R.id.button_clear)
    ImageButton mButtonClear;
    private OnSearchLister mOnSearchLister;

    public SimpleSearchView(Context context) {
        super(context);
        init(context);
    }

    public SimpleSearchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SimpleSearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.widget_simple_search_view, this);
        ButterKnife.bind(this);
        mEditQuery.addTextChangedListener(this);
        mEditQuery.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        mEditQuery.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        mEditQuery.setOnEditorActionListener(this);

    }
    // 搜索和清除的点击事件
    @OnClick({R.id.button_search, R.id.edit_query, R.id.button_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_search:
                search();
                break;
            case R.id.edit_query:
                break;
            case R.id.button_clear:
                mEditQuery.setText(null);
                search();
                break;
        }
    }
    /****************************文本变化的监听****************************/
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String query=mEditQuery.getText().toString();
        int visiable= TextUtils.isEmpty(query)?View.INVISIBLE:VISIBLE;
        mButtonClear.setVisibility(INVISIBLE);
    }
    // -----------------软件操作监听重写的方法-------------------
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId==EditorInfo.IME_ACTION_SEARCH){
            search();
            return true;
        }
        return false;
    }
    // 拿到字符串，然后搜索的方法
    public void search(){
        String query=mEditQuery.getText().toString();
        if (mOnSearchLister!=null){
            mOnSearchLister.search(query);
        }
        closeKeyBoard();
    }
    // 关闭软键盘
    private void closeKeyBoard() {
        mEditQuery.clearFocus();
        InputMethodManager manager= (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
         manager.hideSoftInputFromWindow(mEditQuery.getWindowToken(),0);
    }
    public void setSearchLister(OnSearchLister onSearchLister){
        mOnSearchLister = onSearchLister;
    }
    // 利用接口回调，为控件提供一个设置搜索监听的方法，让调用者实现监听，实现里面搜索方法
    public interface OnSearchLister{
         void search(String query);
    }
}
