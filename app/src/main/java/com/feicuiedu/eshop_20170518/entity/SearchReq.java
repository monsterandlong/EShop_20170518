package com.feicuiedu.eshop_20170518.entity;

import com.feicuiedu.eshop_20170518.manger.base.RequestParam;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 张志龙 on 2017/5/26.
 */

public class SearchReq extends RequestParam{
    @SerializedName("filter") private Filter mFilter;

    @SerializedName("pagination") private Pagination mPagination;

    public void setFilter(Filter filter) {
        mFilter = filter;
    }

    public void setPagination(Pagination pagination) {
        mPagination = pagination;
    }
}
