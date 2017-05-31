package com.feicuiedu.eshop_20170518.fragment.Api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.feicuiedu.eshop_20170518.entity.GoodsInfoReq;
import com.feicuiedu.eshop_20170518.entity.GoodsInfoRsp;
import com.feicuiedu.eshop_20170518.manger.base.ApiInterface;
import com.feicuiedu.eshop_20170518.manger.base.ApiPath;
import com.feicuiedu.eshop_20170518.manger.base.RequestParam;
import com.feicuiedu.eshop_20170518.manger.base.ResponseEntity;

/**
 * Created by 张志龙 on 2017/5/31.
 */

public class ApiGoodsInfo implements ApiInterface {
    private GoodsInfoReq mGoodsInfoReq;

    // 请求体数据的初始化和请求体数据填充
    public ApiGoodsInfo(int goodsId) {
        mGoodsInfoReq = new GoodsInfoReq();
        mGoodsInfoReq.setGoodsId(goodsId);
    }
    @NonNull
    @Override
    public String getPath() {
        return ApiPath.GOODS_INFO;
    }

    @Nullable
    @Override
    public RequestParam getRequestParam() {
        return mGoodsInfoReq;
    }

    @NonNull
    @Override
    public Class<? extends ResponseEntity> getResponseEntity() {
        return GoodsInfoRsp.class;
    }
}
