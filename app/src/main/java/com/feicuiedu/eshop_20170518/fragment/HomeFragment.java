package com.feicuiedu.eshop_20170518.fragment;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.feicuiedu.eshop_20170518.R;
import com.feicuiedu.eshop_20170518.base.utils.BaseFragment;
import com.feicuiedu.eshop_20170518.base.utils.PtrWarp;
import com.feicuiedu.eshop_20170518.base.utils.ToolbarWarp;
import com.feicuiedu.eshop_20170518.entity.Banner;
import com.feicuiedu.eshop_20170518.entity.HomeBannerRsp;
import com.feicuiedu.eshop_20170518.entity.HomeCategoryRsp;
import com.feicuiedu.eshop_20170518.entity.Picture;
import com.feicuiedu.eshop_20170518.entity.SimpleGoods;
import com.feicuiedu.eshop_20170518.fragment.Api.ApiHomeBanner;
import com.feicuiedu.eshop_20170518.fragment.Api.ApiHomeCategory;
import com.feicuiedu.eshop_20170518.fragment.adapter.HomeListAdapter;
import com.feicuiedu.eshop_20170518.manger.GoodsActivity;
import com.feicuiedu.eshop_20170518.manger.InIntent;
import com.feicuiedu.eshop_20170518.manger.base.ResponseEntity;
import com.feicuiedu.eshop_20170518.manger.base.UICallback;
import com.feicuiedu.eshop_20170518.view.BannarAdapter;
import com.feicuiedu.eshop_20170518.view.BannarView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import jp.wasabeef.picasso.transformations.GrayscaleTransformation;

/**
 * Created by 张志龙 on 2017/5/24.
 */

public class HomeFragment extends BaseFragment {

    @BindView(R.id.standard_toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.standard_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.list_home_goods)
    ListView mHomeGoods;
    private ImageView[] mIvPromotes = new ImageView[4];
    private TextView mTvView;
    private BannarAdapter<Banner> bannarAdapter;
    private HomeListAdapter mListAdapter;
    private PtrWarp mPtrWarp;
    private boolean mBannerRefreshed = false;
    private boolean mCategoryRefreshed = false;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected void initView() {
        new ToolbarWarp(this).setShowTitle(true).setShowBack(false).setTitle(R.string.home_title);
        mPtrWarp = new PtrWarp(this,false) {

            @Override
            public void onRefresh() {
                mBannerRefreshed = false;
                mCategoryRefreshed = false;
               getHomeData();
            }


            @Override
            public void onLoadMore() {

            }
        };
        mPtrWarp.postRefreshDelayed(50);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.partial_home_header, mHomeGoods, false);
        BannarView mBannarView = ButterKnife.findById(view,R.id.layout_banner);
        bannarAdapter = new BannarAdapter<Banner>() {
            @Override
            protected void bind(viewHolder holder, Banner data) {
              Picasso.with(getContext()).load(data.getPicture().getLarge()).into(holder.mImgBannar);
            }
        };
        mBannarView.setAdapter(bannarAdapter);
        mIvPromotes[0] = ButterKnife.findById(view,R.id.image_promote_one);
        mIvPromotes[1] = ButterKnife.findById(view,R.id.image_promote_two);
        mIvPromotes[2] = ButterKnife.findById(view,R.id.image_promote_three);
        mIvPromotes[3] = ButterKnife.findById(view,R.id.image_promote_four);
        //促销单品的字
        mTvView = ButterKnife.findById(view, R.id.text_promote_goods);
        mHomeGoods.addHeaderView(view);
        //适配器
        mListAdapter = new HomeListAdapter();
        mHomeGoods.setAdapter(mListAdapter);


    }
    //数据获取
    public void getHomeData() {
        //轮播图和促销单品的数据网路获取
        UICallback uiCallback = new UICallback() {
            @Override
            protected void onBusinessResponse(boolean isSuccess, ResponseEntity responseEntity) {
                mBannerRefreshed = true;
                if (isSuccess) {
                    HomeBannerRsp homeBannerRsp = (HomeBannerRsp) responseEntity;
                    bannarAdapter.reset(homeBannerRsp.getData().getBanners());
                    setPromotesGoods(homeBannerRsp.getData().getGoodsList());
                }
                if (mBannerRefreshed && mCategoryRefreshed) {
                    mPtrWarp.stopRefresh();
                }
            }
        };
        //商品分类的网络获取
        UICallback categoryCallback = new UICallback() {
            @Override
            protected void onBusinessResponse(boolean isSuccess, ResponseEntity responseEntity) {
                mCategoryRefreshed = true;
                if (isSuccess) {
                    HomeCategoryRsp homeCategoryRsp = (HomeCategoryRsp) responseEntity;
                    mListAdapter.reset(homeCategoryRsp.getData());
                }
                if (mBannerRefreshed && mCategoryRefreshed) {
                    mPtrWarp.stopRefresh();
                }
            }
        };
        // 轮播图和促销单品
        InIntent.getInstance().enqueue(new ApiHomeBanner(), uiCallback,getClass().getSimpleName());
        // 分类和推荐商品
       InIntent.getInstance().enqueue(new ApiHomeCategory(), categoryCallback,getClass().getSimpleName());
    }

    //设置促销单品
    private void setPromotesGoods(List<SimpleGoods> goodsList){
       mTvView.setVisibility(View.VISIBLE);
        for (int i=0;i<mIvPromotes.length;i++){
            mIvPromotes[i].setVisibility(View.VISIBLE);
            //图片资源地址的填充
            final SimpleGoods simpleGoods=goodsList.get(i);
            Picture picture=simpleGoods.getImg();
            //数据的填充
            Picasso.with(getContext()).load(picture.getLarge())
                    .transform(new CropCircleTransformation())
                    .transform(new GrayscaleTransformation())
                    .into(mIvPromotes[i]);
            mIvPromotes[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 跳转到详情
                    int simpleGoodsId = simpleGoods.getId();
                    Intent intent = GoodsActivity.getStarIntent(getContext(), simpleGoodsId);
                    getActivity().startActivity(intent);
                }
            });


        }
    }



    @Override
    protected int getlayoutid() {
        return R.layout.fragment_home;
    }

}
