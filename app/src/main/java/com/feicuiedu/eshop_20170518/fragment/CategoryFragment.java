package com.feicuiedu.eshop_20170518.fragment;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.feicuiedu.eshop_20170518.R;
import com.feicuiedu.eshop_20170518.base.utils.BaseFragment;
import com.feicuiedu.eshop_20170518.base.utils.ToolbarWarp;
import com.feicuiedu.eshop_20170518.entity.CategoryPrimary;
import com.feicuiedu.eshop_20170518.entity.CategoryRsp;
import com.feicuiedu.eshop_20170518.entity.Filter;
import com.feicuiedu.eshop_20170518.fragment.Api.ApiCategory;
import com.feicuiedu.eshop_20170518.fragment.adapter.CategoryAdapter;
import com.feicuiedu.eshop_20170518.fragment.adapter.ChildrenAdapter;
import com.feicuiedu.eshop_20170518.manger.InIntent;
import com.feicuiedu.eshop_20170518.manger.SearshGoodsActivity;
import com.feicuiedu.eshop_20170518.manger.base.ResponseEntity;
import com.feicuiedu.eshop_20170518.manger.base.UICallback;

import java.util.List;

import butterknife.BindView;
import butterknife.OnItemClick;

/**
 * Created by 张志龙 on 2017/5/23.
 */

public class CategoryFragment extends BaseFragment {
    @BindView(R.id.list_category)
    ListView mListCate;
    @BindView(R.id.list_children)
    ListView mListChil;
    @BindView(R.id.standard_toolbar_title)
    TextView mTvTitle;
    @BindView(R.id.standard_toolbar)
    Toolbar mToolbar;
    private List<CategoryPrimary> mData;
    private CategoryAdapter mCategoryAdapter;
    private ChildrenAdapter mChildrenAdapter;

    //单列
    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    protected int getlayoutid() {
        return R.layout.fragment_category;
    }

    protected void initView() {
        initToolbar();
        mCategoryAdapter = new CategoryAdapter();
        mListCate.setAdapter(mCategoryAdapter);
        mChildrenAdapter = new ChildrenAdapter();
        mListChil.setAdapter(mChildrenAdapter);
        if (mData!=null){
            unDataCategory();
        }else {
            //call是想网络发送请求
            UICallback uiCallback = new UICallback() {
                @Override
                protected void onBusinessResponse(boolean isSuccess, ResponseEntity responseEntity) {
                    if (isSuccess){
                        // 拿到具体的数据
                        CategoryRsp categoryRsp = (CategoryRsp) responseEntity;
                        mData = categoryRsp.getData();
                        // 数据有了之后更新UI：拿到的Data是一级分类的信息，一级分类里面又包括二级分类
                        // 数据先给一级分类，默认选择一级分类的第一条，二级分类数据才能展示。
                        unDataCategory();
                    }
                }
            };
           InIntent.getInstance().enqueue(new ApiCategory(),uiCallback,getClass().getSimpleName());
        }

    }
    //一级分类
    public void unDataCategory(){
        //更新数据
       mCategoryAdapter.reset(mData);
        chooseCategory(0);
    }
    public void chooseCategory(int position){
        mListChil.setItemChecked(position,true);
        mChildrenAdapter.reset(mCategoryAdapter.getItem(position).getChildren());

    }
    @OnItemClick({R.id.list_category,R.id.list_children})
    public void onItemClick(AdapterView<?> parent,int position){
        switch (parent.getId()){
            case R.id.list_category:
                chooseCategory(position);
                break;
            case R.id.list_children:
                int id = mChildrenAdapter.getItem(position).getId();
                navigateToSearch(id);
                break;
        }

    }

    private void initToolbar() {
        new ToolbarWarp(this).setShowBack(false).setShowTitle(true);
    }
    //设置搜索图标


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_category,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId== android.R.id.home){
            getActivity().onBackPressed();
            return true;
        }
        if (itemId==R.id.menu_search){
            int postion=mListCate.getCheckedItemPosition();
            int catetoryId=mCategoryAdapter.getItem(postion).getId();
            navigateToSearch(catetoryId);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void navigateToSearch(int categoryId){
        Filter filter=new Filter();
        filter.setCategoryId(categoryId);
        Intent intent= SearshGoodsActivity.getStartIntent(getContext(),filter);
        getActivity().startActivity(intent);
    }
}
