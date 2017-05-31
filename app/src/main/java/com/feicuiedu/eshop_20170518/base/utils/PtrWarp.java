package com.feicuiedu.eshop_20170518.base.utils;

import com.feicuiedu.eshop_20170518.R;

import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicDefaultFooter;
import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by 张志龙 on 2017/5/25.
 */

public abstract class PtrWarp {

    private PtrFrameLayout mRefreshLyout;

    public PtrWarp(BaseActivity activity,boolean isNeedLoad) {
        mRefreshLyout = ButterKnife.findById(activity, R.id.standard_refresh_layout);
        initPtr(isNeedLoad);

    }

    public PtrWarp(BaseFragment fragment,boolean isNeedLoad) {
        mRefreshLyout = ButterKnife.findById(fragment.getView(), R.id.standard_refresh_layout);
        initPtr(isNeedLoad);
    }
    private void initPtr(boolean isNeedLoad) {
        if (mRefreshLyout==null){
            //水平移动
           mRefreshLyout.disableWhenHorizontalMove(true);
        }
        initPtrHeandler();
        if (isNeedLoad){
            initPtrFooter();
        }
        mRefreshLyout.setPtrHandler(mHandler);
    }
    private PtrDefaultHandler2 mHandler=new PtrDefaultHandler2() {
        @Override
        public void onLoadMoreBegin(PtrFrameLayout frame) {
           onLoadMore();
        }

        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
           onRefresh();
        }
    };

    //尾部
    private void initPtrFooter() {
        PtrClassicDefaultFooter footer=new PtrClassicDefaultFooter(mRefreshLyout.getContext());
        mRefreshLyout.setFooterView(footer);
        mRefreshLyout.addPtrUIHandler(footer);
    }

    private void initPtrHeandler() {
        PtrClassicDefaultHeader header=new PtrClassicDefaultHeader(mRefreshLyout.getContext());
        mRefreshLyout.setHeaderView(header);
        mRefreshLyout.addPtrUIHandler(header);
    }
    //自动刷新
    public void autoRefresh(){
        mRefreshLyout.autoRefresh();
    }
    //延时刷新
    public void postRefreshDelayed(long delay){
        mRefreshLyout.postDelayed(new Runnable() {
            @Override
            public void run() {
               mRefreshLyout.autoRefresh();
            }
        }, delay);

    }
    //判断是否在刷新
    public boolean isRefresh(){
        return mRefreshLyout.isRefreshing();
    }
    //停止刷新
    public void stopRefresh(){
        if (isRefresh()){
            mRefreshLyout.refreshComplete();
        }
    }


    public abstract void onRefresh();
    public abstract void onLoadMore();

}
