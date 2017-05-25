package com.feicuiedu.eshop_20170518.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 张志龙 on 2017/5/23.
 */

public class CategoryBase {
    @SerializedName("id") private int mId;

    @SerializedName("name") private String mName;

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }
}
