package com.feicuiedu.eshop_20170518.base.utils.toast;

import android.app.Application;

/**
 * Created by 张志龙 on 2017/5/25.
 */

public class EshopToast extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ToastWarp.init(this);
    }
}
