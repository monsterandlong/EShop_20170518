package com.feicuiedu.eshop_20170518.manger;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.feicuiedu.eshop_20170518.entity.GoodsInfo;
import com.feicuiedu.eshop_20170518.fragment.TestFragment;

/**
 * Created by 张志龙 on 2017/5/31.
 */

public class GoodsInfoAdapter extends FragmentPagerAdapter {
    private GoodsInfo goodsInfo;

    public GoodsInfoAdapter(FragmentManager fm,GoodsInfo goodsInfo) {
        super(fm);
        this.goodsInfo=goodsInfo;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return TestFragment.newInstance(goodsInfo.getName());
            case 1:
                return TestFragment.newInstance("商品详情");
            case 2:
                return TestFragment.newInstance("商品评价");
            default:
                throw new UnsupportedOperationException("Position:"+position);
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
