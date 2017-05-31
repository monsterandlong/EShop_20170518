package com.feicuiedu.eshop_20170518.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.feicuiedu.eshop_20170518.R;

/**
 * Created by 张志龙 on 2017/5/31.
 */

public class TransitionActivity extends AppCompatActivity {
    private boolean transitionAnimator;

    // 处理返回箭头的事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        // 设置跳转效果
        setTransitionAnimator(true);
    }

    @Override
    public void finish() {
        super.finish();
        // 设置跳转效果
        setTransitionAnimator(false);
    }
    // 不需要的时候，设置没有动画效果的finishWithDefault
    public void finishWithDefault(){
        super.finish();
    }

    // 设置跳转的动画
    public void setTransitionAnimator(boolean transitionAnimator) {
        if (transitionAnimator){
            // 新的页面从右边进入
            overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
        }else {
            overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
        }
    }
}
