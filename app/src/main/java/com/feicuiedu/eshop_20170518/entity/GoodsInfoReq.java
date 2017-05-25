package com.feicuiedu.eshop_20170518.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 张志龙 on 2017/5/22.
 */

public class GoodsInfoReq {
    @SerializedName("good_id")
    private int mGoodsId;

    public int getmGoodsId() {
        return mGoodsId;
    }

    public void setmGoodsId(int mGoodsId) {
        this.mGoodsId = mGoodsId;
    }
}
