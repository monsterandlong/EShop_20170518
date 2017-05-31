package com.feicuiedu.eshop_20170518.entity;


import com.google.gson.annotations.SerializedName;

/**
 * Created by 张志龙 on 2017/5/25.
 */

public class Banner {
    @SerializedName("photo") private Picture mPicture; // 轮播图图片

    @SerializedName("description") private String mDesc; // 描述

    @SerializedName("url") private String mUrl; // 外链URL

    public Picture getPicture() {
        return mPicture;
    }

    public String getDesc() {
        return mDesc;
    }

    public String getUrl() {
        return mUrl;
    }
}
