package com.feicuiedu.eshop_20170518.fragment.Api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.feicuiedu.eshop_20170518.entity.HomeCategoryRsp;
import com.feicuiedu.eshop_20170518.manger.base.ApiInterface;
import com.feicuiedu.eshop_20170518.manger.base.ApiPath;
import com.feicuiedu.eshop_20170518.manger.base.RequestParam;
import com.feicuiedu.eshop_20170518.manger.base.ResponseEntity;

/**
 * Created by 张志龙 on 2017/5/31.
 */

public class ApiHomeCategory implements ApiInterface {
    @NonNull
    @Override
    public String getPath() {
        return ApiPath.HOME_CATEGORY;
    }

    @Nullable
    @Override
    public RequestParam getRequestParam() {
        return null;
    }

    @NonNull
    @Override
    public Class<? extends ResponseEntity> getResponseEntity() {
        return HomeCategoryRsp.class;
    }
}
