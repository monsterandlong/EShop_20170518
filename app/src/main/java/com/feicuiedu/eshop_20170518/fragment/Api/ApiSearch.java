package com.feicuiedu.eshop_20170518.fragment.Api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.feicuiedu.eshop_20170518.entity.Filter;
import com.feicuiedu.eshop_20170518.entity.Pagination;
import com.feicuiedu.eshop_20170518.entity.SearchReq;
import com.feicuiedu.eshop_20170518.entity.SearchRsp;
import com.feicuiedu.eshop_20170518.manger.base.ApiInterface;
import com.feicuiedu.eshop_20170518.manger.base.ApiPath;
import com.feicuiedu.eshop_20170518.manger.base.RequestParam;
import com.feicuiedu.eshop_20170518.manger.base.ResponseEntity;

/**
 * Created by 张志龙 on 2017/5/31.
 */

public class ApiSearch implements ApiInterface {
    private SearchReq mSearchReq;
    public ApiSearch(Filter filter, Pagination pagination) {
        mSearchReq=new SearchReq();
        mSearchReq.setFilter(filter);
        mSearchReq.setPagination(pagination);
    }

    @NonNull
    @Override
    public String getPath() {
        return ApiPath.SEARCH;
    }

    @Nullable
    @Override
    public RequestParam getRequestParam() {
        return mSearchReq;
    }

    @NonNull
    @Override
    public Class<? extends ResponseEntity> getResponseEntity() {
        return SearchRsp.class;
    }
}
