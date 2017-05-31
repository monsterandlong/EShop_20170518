package com.feicuiedu.eshop_20170518.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张志龙 on 2017/5/25.
 */

public class CategoryHome extends CategoryBase{

    @SerializedName("goods")
    private List<SimpleGoods> mHotGoodsList; // 首页分类的推荐商品.

    public List<SimpleGoods> getHotGoodsList() {
        return mHotGoodsList;
    }
}
