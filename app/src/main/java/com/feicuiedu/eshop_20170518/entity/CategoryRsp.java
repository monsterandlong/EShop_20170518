package com.feicuiedu.eshop_20170518.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张志龙 on 2017/5/23.
 */

public class CategoryRsp {
        @SerializedName("data")
        private List<CategoryPrimary> mData;

        @SerializedName("status")
        private Status mStatus;

        public Status getStatus() {
            return mStatus;
        }

        public List<CategoryPrimary> getData() {
            return mData;
        }
}
