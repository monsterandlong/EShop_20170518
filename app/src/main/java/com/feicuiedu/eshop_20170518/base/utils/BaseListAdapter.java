package com.feicuiedu.eshop_20170518.base.utils;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by 张志龙 on 2017/5/24.
 */

public abstract class BaseListAdapter<T,V extends BaseListAdapter.viewHolder> extends BaseAdapter {

    private List<T> mData=new ArrayList<>();
    public void reset(List<T> data){
        mData.clear();
        if (data!=null)
        mData.addAll(data);
        notifyDataSetChanged();
    }
    public void addAll(List<T> data){
        if (data!=null)mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder holder=null;
        if (convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(getItemLayoutId(),parent,false);
            holder=getItemViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder= (viewHolder) convertView.getTag();
        holder.buid(position);

        return convertView;
    }

    @LayoutRes public abstract int getItemLayoutId();
    protected abstract V getItemViewHolder(View view);

    public abstract class viewHolder{
         View mItemView;

        public viewHolder(View ItemView) {
            ButterKnife.bind(this,ItemView);
            mItemView = ItemView;
        }

        public abstract void buid(int Position);
        protected final Context getContext(){
            return mItemView.getContext();
        }

    }
}
