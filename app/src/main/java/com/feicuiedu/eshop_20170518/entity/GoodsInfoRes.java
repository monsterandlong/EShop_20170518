package com.feicuiedu.eshop_20170518.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 张志龙 on 2017/5/22.
 */

public class GoodsInfoRes {
    @SerializedName("status")
    private Status mStatus;

    @SerializedName("data")
    private GoodsInfo mGoodsInfo;

    public Status getStatus() {
        return mStatus;
    }

    public GoodsInfo getGoodsInfo() {
        return mGoodsInfo;
    }
}
