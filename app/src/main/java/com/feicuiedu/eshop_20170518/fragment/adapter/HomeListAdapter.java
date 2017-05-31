package com.feicuiedu.eshop_20170518.fragment.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.feicuiedu.eshop_20170518.R;
import com.feicuiedu.eshop_20170518.base.utils.BaseListAdapter;
import com.feicuiedu.eshop_20170518.entity.CategoryHome;
import com.feicuiedu.eshop_20170518.entity.Picture;
import com.feicuiedu.eshop_20170518.entity.SimpleGoods;
import com.feicuiedu.eshop_20170518.manger.GoodsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

/**
 * Created by 张志龙 on 2017/5/25.
 */

public class HomeListAdapter extends BaseListAdapter<CategoryHome,HomeListAdapter.viewHolder> {


    @Override
    public int getItemLayoutId() {
        return R.layout.item_home_goods;
    }

    @Override
    protected viewHolder getItemViewHolder(View view) {
        return new viewHolder(view);
    }
    class viewHolder extends BaseListAdapter.viewHolder{
        @BindView(R.id.text_category)
        TextView  mTvCategory;
        @BindViews({
                R.id.image_goods_01,
                R.id.image_goods_02,
                R.id.image_goods_03,
                R.id.image_goods_04
        })
        ImageView[] mImageViews;
        private CategoryHome mCategoryHome;


        public viewHolder(View ItemView) {
            super(ItemView);
        }

        @Override
        public void buid(int Position) {
            mCategoryHome = getItem(Position);
            mTvCategory.setText(mCategoryHome.getName());
            final List<SimpleGoods> goodsList=mCategoryHome.getHotGoodsList();
            for (int i=0;i<mImageViews.length;i++){
                Picture picture=goodsList.get(i).getImg();
                Picasso.with(getContext()).load(picture.getLarge()).into(mImageViews[i]);
                final int idx=i;
                mImageViews[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SimpleGoods simpleGoods=goodsList.get(idx);
                        Intent intent=GoodsActivity.getStarIntent(getContext(),simpleGoods.getId());
                        getContext().startActivity(intent);
                    }
                });
            }


        }
        @OnClick(R.id.text_category)
        void onClick(){
            Toast.makeText(getContext(),mCategoryHome.getName(), Toast.LENGTH_SHORT).show();

        }
    }

}
