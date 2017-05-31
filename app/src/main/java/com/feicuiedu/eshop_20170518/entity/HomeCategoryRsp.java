package com.feicuiedu.eshop_20170518.entity;

import com.feicuiedu.eshop_20170518.manger.base.ResponseEntity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张志龙 on 2017/5/25.
 */

public class HomeCategoryRsp extends ResponseEntity {


    @SerializedName("data")
    private List<CategoryHome> mData;

    public List<CategoryHome> getData() {
        return mData;
    }

}