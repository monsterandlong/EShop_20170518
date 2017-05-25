package com.feicuiedu.eshop_20170518.base.utils;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.feicuiedu.eshop_20170518.R;

/**
 * Created by 张志龙 on 2017/5/23.
 */

public class TramActivity extends AppCompatActivity {


    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        setTransitionAnimator(true);

    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        setTransitionAnimator(true);
    }

    @Override
    public void finish() {
        super.finish();
        setTransitionAnimator(false);
    }
    public void finishWithDefault(){
        super.finish();
    }

    public void setTransitionAnimator(boolean isNewActivity) {
       if (isNewActivity){
           overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
       }else {
           overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
       }
    }
}
