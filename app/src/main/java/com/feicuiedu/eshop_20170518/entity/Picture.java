package com.feicuiedu.eshop_20170518.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 张志龙 on 2017/5/25.
 */

public class Picture {
    @SerializedName("small") private String mSmall; // 小图

    @SerializedName("thumb") private String mMiddle; // 中图

    @SerializedName("url") private String mLarge; // 大图

    public String getSmall() {
        return mSmall;
    }

    public String getMiddle() {
        return mMiddle;
    }

    public String getLarge() {
        return mLarge;
    }
}
