package com.feicuiedu.eshop_20170518.entity;

import com.feicuiedu.eshop_20170518.manger.base.ResponseEntity;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 张志龙 on 2017/5/31.
 */

public class GoodsInfoRsp extends ResponseEntity{
    @SerializedName("data")
    private GoodsInfo mData;

    public GoodsInfo getData() {
        return mData;
    }
}
