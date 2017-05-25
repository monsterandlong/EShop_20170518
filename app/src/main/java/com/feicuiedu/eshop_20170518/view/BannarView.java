package com.feicuiedu.eshop_20170518.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.feicuiedu.eshop_20170518.R;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by 张志龙 on 2017/5/24.
 */

public class BannarView extends RelativeLayout {

    @BindView(R.id.pager_banner)
    ViewPager mPagerBanner;
    @BindView(R.id.indicator)
    CircleIndicator mIndicator;
    private Timer mTimer;
    private TimerTask mTask;
    private CyclHandler mCyclHandler;
    private long mResumeTiem;
    private static final long duration = 4000;

    public BannarView(Context context) {
        super(context);
        init(context);
    }

    public BannarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BannarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.widget_banner_layout, this, true);
        ButterKnife.bind(this);
        mCyclHandler=new CyclHandler(this);

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mTimer = new Timer();
        mTask = new TimerTask() {
            @Override
            public void run() {
                mCyclHandler.sendEmptyMessage(0);
            }
        };
        mTimer.schedule(mTask, 4000, 4000);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mTimer.cancel();
        mTask.cancel();
        mTimer=null;
        mTask=null;

    }
    //触摸时间
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mResumeTiem = System.currentTimeMillis()+duration ;
        return super.dispatchTouchEvent(ev);
    }

    //切换下一张图
    public void moveToImg() {
        //看是否有适配器
        if (mPagerBanner.getAdapter()==null){
            throw new IllegalStateException("you need set a banner adapter");
        }
        //适配器是否有东西
        int count=mPagerBanner.getAdapter().getCount();
        if (count==0)return;
        //看是否是最后一条
        if (mPagerBanner.getCurrentItem()==count-1){
            mPagerBanner.setCurrentItem(0,false);
        }else {
            mPagerBanner.setCurrentItem(mPagerBanner.getCurrentItem()+1,true);
        }
    }
    public void setAdapter(BannarAdapter adapter){
        Log.e("id","id="+mPagerBanner.getId());
        mPagerBanner.setAdapter(adapter);
        mIndicator.setViewPager(mPagerBanner);
        adapter.registerDataSetObserver(mIndicator.getDataSetObserver());
    }
    //轮播
    private static class CyclHandler extends Handler {
        //弱引用
        private final WeakReference<BannarView> weakReference;

        public CyclHandler(BannarView bannarView) {
            weakReference = new WeakReference<BannarView>(bannarView);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (weakReference == null) return;
            BannarView bannarView = weakReference.get();
            if (bannarView == null) return;
            if (System.currentTimeMillis() < bannarView.mResumeTiem){
                return;
            }
            bannarView.moveToImg();
        }


    }

}
