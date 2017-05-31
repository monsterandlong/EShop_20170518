package com.feicuiedu.eshop_20170518.entity;

import com.feicuiedu.eshop_20170518.manger.base.ResponseEntity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张志龙 on 2017/5/26.
 */

public class SearchRsp  extends ResponseEntity{

    @SerializedName("data") private List<SimpleGoods> mData;

    @SerializedName("paginated") private Paginated mPaginated;

    public List<SimpleGoods> getData() {
        return mData;
    }

    public Paginated getPaginated() {
        return mPaginated;
    }


}
