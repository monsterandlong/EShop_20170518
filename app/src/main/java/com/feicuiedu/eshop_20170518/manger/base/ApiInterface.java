package com.feicuiedu.eshop_20170518.manger.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by 张志龙 on 2017/5/31.
 */

public interface ApiInterface {
    @NonNull String getPath();
    @Nullable RequestParam getRequestParam();
    @NonNull Class<? extends ResponseEntity> getResponseEntity();
}
