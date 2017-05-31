package com.feicuiedu.eshop_20170518.base.utils;

import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.feicuiedu.eshop_20170518.R;

import butterknife.ButterKnife;

/**
 * Created by 张志龙 on 2017/5/25.
 */

public class ToolbarWarp {

    private BaseActivity mBaseActivity;
    private Toolbar mToolbar;
    private TextView mTvTitle;

    public ToolbarWarp(BaseActivity activity) {
        mBaseActivity=activity;
        mToolbar = ButterKnife.findById(activity, R.id.standard_toolbar);
        init(mToolbar);
        setShowBack(true);
        setShowTitle(false);
    }
    public ToolbarWarp(BaseFragment fragment) {
        mBaseActivity= (BaseActivity) fragment.getActivity();
       mToolbar= ButterKnife.findById(fragment.getView(), R.id.standard_toolbar);
        init(mToolbar);
        fragment.setHasOptionsMenu(true);
        setShowTitle(false);
        setShowBack(false);
    }

    private void init(Toolbar mToolbar) {
        mTvTitle = ButterKnife.findById(mToolbar, R.id.standard_toolbar_title);
        mBaseActivity.setSupportActionBar(mToolbar);
    }
    public ToolbarWarp setShowTitle(boolean isShowTitle){
        mBaseActivity.getSupportActionBar().setDisplayShowTitleEnabled(isShowTitle);
        return this;
    }
    public ToolbarWarp setShowBack(boolean isShowBack){
        mBaseActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(isShowBack);
        return this;
    }

    public ToolbarWarp setTitle(@StringRes int resId){
        if (mTvTitle==null){
            throw new UnsupportedOperationException("No title TextView in toolbar");
        }
        mTvTitle.setText(resId);
        return this;
    }
}
