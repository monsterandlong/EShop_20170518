package com.feicuiedu.eshop_20170518.fragment.adapter;

import android.view.View;
import android.widget.TextView;

import com.feicuiedu.eshop_20170518.R;
import com.feicuiedu.eshop_20170518.base.utils.BaseListAdapter;
import com.feicuiedu.eshop_20170518.entity.CategoryBase;

import butterknife.BindView;

/**
 * Created by 张志龙 on 2017/5/23.
 */

public class ChildrenAdapter extends BaseListAdapter<CategoryBase, ChildrenAdapter.viewHolder> {



    @Override
    public int getItemLayoutId() {
        return R.layout.item_children_category;
    }

    @Override
    protected viewHolder getItemViewHolder(View view) {
        return new viewHolder(view);
    }

    class viewHolder extends BaseListAdapter.viewHolder {
        @BindView(R.id.text_category)
        TextView mTvCategory;

        public viewHolder(View Itemview) {
            super(Itemview);
        }

        @Override
        public void buid(int Position) {
          mTvCategory.setText(getItem(Position).getName());
        }
    }
}
