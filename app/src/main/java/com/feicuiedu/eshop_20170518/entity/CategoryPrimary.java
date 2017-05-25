package com.feicuiedu.eshop_20170518.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

/**
 * Created by 张志龙 on 2017/5/23.
 */

public class CategoryPrimary extends CategoryBase{
    @SerializedName("children")
    private List<CategoryBase> mChildren = Collections.emptyList();

    public List<CategoryBase> getChildren() {
        return mChildren;
    }
}
