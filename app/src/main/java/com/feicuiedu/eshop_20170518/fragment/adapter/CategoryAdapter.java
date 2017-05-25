package com.feicuiedu.eshop_20170518.fragment.adapter;

import android.view.View;
import android.widget.TextView;

import com.feicuiedu.eshop_20170518.R;

import com.feicuiedu.eshop_20170518.base.utils.BaseListAdapter;
import com.feicuiedu.eshop_20170518.entity.CategoryPrimary;

import butterknife.BindView;

/**
 * Created by 张志龙 on 2017/5/23.
 */

public class CategoryAdapter extends BaseListAdapter<CategoryPrimary,CategoryAdapter.viewholder> {



    @Override
    public int getItemLayoutId() {
        return R.layout.item_primary_category;
    }

    @Override
    protected viewholder getItemViewHolder(View view) {
        return new viewholder(view);
    }


    class viewholder extends BaseListAdapter.viewHolder {
        @BindView(R.id.text_category)
        TextView mTvCategory;

        public viewholder(View ItemView) {
            super(ItemView);
        }

        @Override
        public void buid(int Position) {
          mTvCategory.setText(getItem(Position).getName());
        }
    }
}
