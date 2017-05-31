package com.feicuiedu.eshop_20170518.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 张志龙 on 2017/5/26.
 */

public class Pagination {
    @SerializedName("page") private int mPage = 1; // 页码

    @SerializedName("count") private int mCount = 5; // 每页条数

    public Pagination next() {
        mPage++;
        return this;
    }

    public Pagination reset() {
        mPage = 1;
        return this;
    }

    public boolean isFirst() {
        return mPage == 1;
    }

    public int getPage() {
        return mPage;
    }

    public int getCount() {
        return mCount;
    }
}
