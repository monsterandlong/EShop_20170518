package com.feicuiedu.eshop_20170518.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.feicuiedu.eshop_20170518.R;
import com.feicuiedu.eshop_20170518.base.utils.BaseFragment;
import com.feicuiedu.eshop_20170518.entity.GoodsInfo;
import com.feicuiedu.eshop_20170518.entity.Picture;
import com.feicuiedu.eshop_20170518.manger.GoodsActivity;
import com.feicuiedu.eshop_20170518.view.BannarAdapter;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by 张志龙 on 2017/6/1.
 */

public class GoodsInfoFragment extends BaseFragment {
    private static final String GOODS_INFO = "goods_info";
    @BindView(R.id.text_goods_name)
    TextView mTVGoodsName;
    @BindView(R.id.text_goods_price)
    TextView mTVGoodsPrice;
    Unbinder unbinder;
    @BindView(R.id.button_favorite)
    ImageButton mBtFavorite;
    @BindView(R.id.text_market_price)
    TextView mTvMarketPrice;
    @BindView(R.id.text_goods_details)
    TextView mTvGoodsDetails;
    @BindView(R.id.text_goods_comments)
    TextView textGoodsComments;
    @BindView(R.id.indicator)
    CircleIndicator mIndicator;
    private GoodsInfo mGoodsInfo;
    @BindView(R.id.pager_goods_pictures)
    ViewPager mPicturesPager;

    public static GoodsInfoFragment newInstance(@NonNull GoodsInfo goodsInfo) {
        GoodsInfoFragment goodsInfoFragment = new GoodsInfoFragment();
        //bundle可以放对象传递
        Bundle bundle = new Bundle();
        bundle.putString(GOODS_INFO, new Gson().toJson(goodsInfo));
        goodsInfoFragment.setArguments(bundle);
        return goodsInfoFragment;
    }

    @Override
    protected void initView() {
        String str = getArguments().getString(GOODS_INFO);
        mGoodsInfo = new Gson().fromJson(str, GoodsInfo.class);
        BannarAdapter<Picture> adapter = new BannarAdapter<Picture>() {
            @Override
            protected void bind(viewHolder holder, Picture data) {
                Picasso.with(getContext()).load(data.getLarge()).into(holder.mImgBannar);
            }
        };
        adapter.reset(mGoodsInfo.getPictures());
        mPicturesPager.setAdapter(adapter);
        mIndicator.setViewPager(mPicturesPager);
        adapter.registerDataSetObserver(mIndicator.getDataSetObserver());
        mTVGoodsName.setText(mGoodsInfo.getName());
        mTVGoodsPrice.setText(mGoodsInfo.getShopPrice());
        String markPrice = mGoodsInfo.getMarketPrice();
        SpannableString spannableString = new SpannableString(markPrice);
        spannableString.setSpan(new StrikethroughSpan(), 0, markPrice.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvMarketPrice.setText(spannableString);
        mBtFavorite.setSelected(mGoodsInfo.isCollected());
    }

    @OnClick({R.id.text_goods_comments, R.id.text_goods_details})
    public void onClick(View view) {
        GoodsActivity goodsActivity= (GoodsActivity) getActivity();
        switch (view.getId()) {
            case R.id.text_goods_comments:
                goodsActivity.selectPage(1);
                break;
            case R.id.text_goods_details:
                goodsActivity.selectPage(2);
                break;
        }
    }

    @Override
    protected int getlayoutid() {
        return R.layout.fragment_goods_info;
    }

}
