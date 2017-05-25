package com.feicuiedu.eshop_20170518.fragment;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.feicuiedu.eshop_20170518.R;
import com.feicuiedu.eshop_20170518.base.utils.BaseFragment;
import com.feicuiedu.eshop_20170518.entity.CategoryPrimary;
import com.feicuiedu.eshop_20170518.entity.CategoryRsp;
import com.feicuiedu.eshop_20170518.fragment.adapter.CategoryAdapter;
import com.feicuiedu.eshop_20170518.fragment.adapter.ChildrenAdapter;
import com.feicuiedu.eshop_20170518.manger.InIntent;
import com.feicuiedu.eshop_20170518.manger.UICallback;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnItemClick;
import okhttp3.Call;
import okhttp3.Response;

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
            Call call= InIntent.getInstance().getCategory();
            call.enqueue(new UICallback() {
                @Override
                public void onFailureInUI(Call call, IOException e) {
                    Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponseInUI(Call call, Response response) throws IOException {
                 if (response.isSuccessful()){
                     //Gson解析字符串，字符串为response.body().string()
                     CategoryRsp categoryRsp = new Gson().fromJson(response.body().string(), CategoryRsp.class);
                     if (categoryRsp.getStatus().isSuccees()){
                         mData=categoryRsp.getData();
                         unDataCategory();
                     }
                 }
                }
            });
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
                Toast.makeText(getContext(), mChildrenAdapter.getItem(position).getName(), Toast.LENGTH_SHORT).show();
                break;
        }

    }

    private void initToolbar() {
        //展示Toolbar
        setHasOptionsMenu(true);
        //将Toolbar当作actionbar
        AppCompatActivity activity= (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        //取消默认的标题
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        //设置是否显示箭头
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTvTitle.setText(R.string.category_title);
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
            Toast.makeText(getContext(), "OK", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
