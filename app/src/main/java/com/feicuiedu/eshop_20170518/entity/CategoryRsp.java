package com.feicuiedu.eshop_20170518.entity;

import com.feicuiedu.eshop_20170518.manger.base.ResponseEntity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张志龙 on 2017/5/23.
 */

public class CategoryRsp extends ResponseEntity{
        @SerializedName("data")
        private List<CategoryPrimary> mData;

        public List<CategoryPrimary> getData() {
            return mData;
        }
}
