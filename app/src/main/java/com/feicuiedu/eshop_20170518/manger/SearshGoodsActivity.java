package com.feicuiedu.eshop_20170518.manger;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.feicuiedu.eshop_20170518.R;
import com.feicuiedu.eshop_20170518.base.utils.BaseActivity;
import com.feicuiedu.eshop_20170518.base.utils.LogUtils;
import com.feicuiedu.eshop_20170518.base.utils.PtrWarp;
import com.feicuiedu.eshop_20170518.base.utils.ToolbarWarp;
import com.feicuiedu.eshop_20170518.base.utils.toast.ToastWarp;
import com.feicuiedu.eshop_20170518.entity.Filter;
import com.feicuiedu.eshop_20170518.entity.Paginated;
import com.feicuiedu.eshop_20170518.entity.Pagination;
import com.feicuiedu.eshop_20170518.entity.SearchReq;
import com.feicuiedu.eshop_20170518.entity.SearchRsp;
import com.feicuiedu.eshop_20170518.entity.SimpleGoods;
import com.feicuiedu.eshop_20170518.fragment.Api.ApiSearch;
import com.feicuiedu.eshop_20170518.manger.base.ResponseEntity;
import com.feicuiedu.eshop_20170518.manger.base.UICallback;
import com.feicuiedu.eshop_20170518.view.SimpleSearchView;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.OnItemClick;
import okhttp3.Call;

/**
 * Created by 张志龙 on 2017/5/26.
 */

public class SearshGoodsActivity extends BaseActivity {
    private static final String EXTRA_SEARCH_FILTER = "extra_search_filter";
    private static Context context;
    private PtrWarp ptrWarp;
    private Call mSearchCall;
    private Paginated mPaginated;
    private Pagination mPagination = new Pagination();// 分页参数
    private long mLastRefreshTime;
    @BindView(R.id.list_goods)
    ListView mListGood;
    @BindViews({R.id.text_is_hot,R.id.text_most_expensive,R.id.text_cheapest})
    List<TextView> mTVList;
    @BindView(R.id.search_view)
    SimpleSearchView mSearchView;
    private SearchGoodsAdapter goodsAdapter;
    private static Filter mFilter;

    @Override
    protected void initView() {
        new ToolbarWarp(this);
        //默认一进去选什么
        mTVList.get(0).setActivated(true);
        //传递数据
        String fileStr=getIntent().getStringExtra(EXTRA_SEARCH_FILTER);
        mFilter = new Gson().fromJson(fileStr,Filter.class);
        // 刷新和加载的处理
        // 进行网络数据的获取
        // 进行网络数据的获取
        ptrWarp= new PtrWarp(this,true) {
            @Override
            public void onRefresh() {
                searchGoods(true);
            }

            @Override
            public void onLoadMore() {
                // 进行网络数据的加载，判断上次请求的结果里面是不是还有更多数据
                if (mPaginated.hasMore()){
                    //加载
                    searchGoods(false);
                }else {
                    //不加载
                    ptrWarp.stopRefresh();
                    ToastWarp.show(R.string.msg_load_more_complete);
                }

            }
        };
        mSearchView.setSearchLister(new SimpleSearchView.OnSearchLister() {
            @Override
            public void search(String query) {
                mFilter.setKeywords(query);
                ptrWarp.autoRefresh();
            }
        });
        goodsAdapter = new SearchGoodsAdapter();
        mListGood.setAdapter(goodsAdapter);
        ptrWarp.postRefreshDelayed(50);

    }
    @OnItemClick(R.id.list_goods)
    public void goodsItemClick(int position){
        int id=goodsAdapter.getItem(position).getId();
        Intent intent=GoodsActivity.getStarIntent(this,id);
        startActivity(intent);
    }
    // 搜索商品的方法中进行网络获取
    private void searchGoods(boolean isSearch) {
        if (mSearchCall!=null){
            mSearchCall.cancel();
        }
        if (isSearch){
            mLastRefreshTime=System.currentTimeMillis();
            // 刷新的页数从1开始
            mPagination.reset();
            // 将ListView定位到第一条
            mListGood.setSelection(0);
        }else {
            // 页数要进行+1
            mPagination.next();
            LogUtils.debug("Load More Page = "+mPagination.getPage());
        }
        SearchReq searchReq=new SearchReq();
        searchReq.setFilter(mFilter);
        searchReq.setPagination(mPagination);
        ApiSearch apiSearch = new ApiSearch(mFilter,mPagination);
        mSearchCall = InIntent.getInstance().enqueue(apiSearch, mUICallback,getClass().getSimpleName());
    }
    UICallback mUICallback=new UICallback() {
        @Override
        protected void onBusinessResponse(boolean isSuccess, ResponseEntity responseEntity) {
           ptrWarp.stopRefresh();
            mSearchCall=null;
            if (isSuccess){
                //拿数据
                SearchRsp searchRsp= (SearchRsp) responseEntity;
                // 将当前分页结果取出，便于下次进行加载的时候判断是否还需要进行
                mPaginated=searchRsp.getPaginated();
                // 设置给适配器
                List<SimpleGoods> goodsList = searchRsp.getData();
                if (mPagination.isFirst()){
                    // 刷新得到的数据
                    goodsAdapter.reset(goodsList);
                }else {
                    // 加载出来的数据
                    goodsAdapter.addAll(goodsList);
                }
            }
        }
    };

    // 排序的切换
    @OnClick({R.id.text_is_hot,R.id.text_most_expensive,R.id.text_cheapest})
    public void chooseGoodsOrder(View view) {
        // 如果当前显示是此项，不去触发
        if (view.isActivated())return;
        // 如果正在刷新，不去执行
        if (ptrWarp.isRefresh())return;
        // 遍历将三个都设置为inActivited
        for (TextView textView : mTVList) {
            textView.setActivated(false);
        }
        String sortBy;
        switch (view.getId()) {
            case R.id.text_is_hot:
                sortBy = Filter.SORT_IS_HOT;// 热销
                break;
            case R.id.text_most_expensive:
                sortBy = Filter.SORT_PRICE_DESC;// 价格降序
                break;
            case R.id.text_cheapest:
                sortBy = Filter.SORT_PRICE_ASC;// 价格升序
                break;
            default:
                throw new UnsupportedOperationException();
        }
        mFilter.setSortBy(sortBy);
        // 如果刷新数据，可能会出现切换过快，Tab切换了，但数据没有及时更新
        // 看一下本次刷新和上次刷新时间之间间隔2秒以上，立即刷新，如果没有，延时刷新);
        long time = mLastRefreshTime + 2000 - System.currentTimeMillis();
        time = time<0?0:time;
        ptrWarp.postRefreshDelayed(time);
    }



    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_goods;
    }
    //对外提供一个跳转的方法，并且将Filer数据化
    // 对外提供一个跳转的方法：因为涉及到传递数据，为了规范数据的传递
    // 将Filter转换为字符串传递：不将Filter序列化
    public static Intent getStartIntent(Context context, Filter filter){
        Intent intent=new Intent(context,SearshGoodsActivity.class);
        intent.putExtra(EXTRA_SEARCH_FILTER, new Gson().toJson(filter));
        return intent;
    }
}
