package com.feicuiedu.eshop_20170518.entity;

import com.feicuiedu.eshop_20170518.manger.base.RequestParam;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 张志龙 on 2017/5/22.
 */

public class GoodsInfoReq extends RequestParam {
    @SerializedName("goods_id")
    private int mGoodsId;

    public int getGoodsId() {
        return mGoodsId;
    }

    public void setGoodsId(int goodsId) {
        mGoodsId = goodsId;
    }
}
