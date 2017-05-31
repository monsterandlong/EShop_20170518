package com.feicuiedu.eshop_20170518.manger;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.feicuiedu.eshop_20170518.R;
import com.feicuiedu.eshop_20170518.base.utils.BaseListAdapter;
import com.feicuiedu.eshop_20170518.entity.SimpleGoods;
import com.squareup.picasso.Picasso;

import butterknife.BindView;

/**
 * Created by 张志龙 on 2017/5/31.
 */

public class SearchGoodsAdapter extends BaseListAdapter<SimpleGoods, SearchGoodsAdapter.viewHolder> {

    @Override
    public int getItemLayoutId() {
        return R.layout.item_search_goods;
    }

    @Override
    protected viewHolder getItemViewHolder(View view) {
        return new viewHolder(view);
    }

    class viewHolder extends BaseListAdapter.viewHolder {
        @BindView(R.id.image_goods)
        ImageView mImgGoods;
        @BindView(R.id.text_goods_name)
        TextView mtVGoodsName;
        @BindView(R.id.text_goods_price)
        TextView mTvGoodsPrice;
        @BindView(R.id.text_market_price)
        TextView mTvMarketPrice;

        public viewHolder(View ItemView) {
            super(ItemView);
        }

        @Override
        public void buid(int Position) {
            SimpleGoods simpleGoods=getItem(Position);
            mtVGoodsName.setText(simpleGoods.getName());
            mTvGoodsPrice.setText(simpleGoods.getBrief());
            // 设置商场的价格：原有的字符串加了一个删除线,采用字符串的一个处理类:SpannableString
            String marketPrice = simpleGoods.getMarketPrice();
            SpannableString spannableString=new SpannableString(marketPrice);
            spannableString.setSpan(new StrikethroughSpan(),0,marketPrice.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTvMarketPrice.setText(spannableString);
            // 图片的加载
            Picasso.with(getContext()).load(simpleGoods.getImg().getLarge()).into(mImgGoods);
        }
    }
}
