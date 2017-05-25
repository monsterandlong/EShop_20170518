package com.feicuiedu.eshop_20170518.view;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.feicuiedu.eshop_20170518.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 张志龙 on 2017/5/24.
 */

public abstract class BannarAdapter<T> extends PagerAdapter {
    private List<T> mData = new ArrayList<>();

    public void reset(List<T> data) {
        mData.clear();
        if (data != null) mData.addAll(data);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        viewHolder holder = (viewHolder) object;
        return view == holder.itemView;
    }

    //添加布局
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_banner, container, false);
        container.addView(view);
        viewHolder holder = new viewHolder(view);
        bind(holder, mData.get(position));
        return holder;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        viewHolder holder= (viewHolder) object;
        container.removeView(holder.itemView);
    }

    //必须让子类实现的方法填充
    protected abstract void bind(viewHolder holder, T data);

    public static class viewHolder {
        @BindView(R.id.image_banner_item)
        public ImageView mImgBannar;

        private View itemView;

        public viewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }
}
