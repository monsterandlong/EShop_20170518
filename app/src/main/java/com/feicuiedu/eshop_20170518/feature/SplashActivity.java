package com.feicuiedu.eshop_20170518.feature;

import android.animation.Animator;
import android.content.Intent;
import android.widget.ImageView;

import com.feicuiedu.eshop_20170518.R;
import com.feicuiedu.eshop_20170518.base.utils.BaseActivity;

import butterknife.BindView;

// Splash 页面：图片播放2秒的渐变动画(透明度变化)之后，跳转到主页面
public class SplashActivity extends BaseActivity implements Animator.AnimatorListener {

    @BindView(R.id.image_splash)
    ImageView mImageSplash;

    // 初始化工作
    protected void initView() {
        /**
         * 属性动画来完成：ViewPropertyAnimator：针对于View进行操作的动画类
         * 1. 针对View进行操作：通过View来调用
         * 2. 简洁的链式调用,调用多个动画效果，动画同时进行
         * 3. 多个动画属性同时进行，UI只刷新一次，性能上更加优化
         * 4. 使用：View调用amimate()方法拿到引用
         */
        // 首先设置一个透明度
        mImageSplash.setAlpha(0.3f);

        // 从开始的0.3的透明度再去变化
        mImageSplash.animate()
                .alpha(1.0f) // 设置透明度的动画
                .setDuration(2000)// 设置动画持续的时间
                .setListener(this) // 设置动画的监听
                .start();// 开始动画
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    //---------------------设置动画监听重写的方法----------------------
    // 动画开始的时候会触发
    @Override
    public void onAnimationStart(Animator animation) {

    }

    // 动画结束的时候触发
    @Override
    public void onAnimationEnd(Animator animation) {
        // 完成跳转页面：转场的效果，从右边进行和退出
        Intent intent = new Intent(this,EShopMainActivity.class);
        startActivity(intent);
        finishWithDefault();
    }

    // 动画取消的时候
    @Override
    public void onAnimationCancel(Animator animation) {

    }

    // 动画重复播放的时候
    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
