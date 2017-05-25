package com.feicuiedu.eshop_20170518.fragment;

import android.util.Log;

import com.feicuiedu.eshop_20170518.R;
import com.feicuiedu.eshop_20170518.base.utils.BaseFragment;
import com.feicuiedu.eshop_20170518.view.BannarAdapter;
import com.feicuiedu.eshop_20170518.view.BannarView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 张志龙 on 2017/5/24.
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.layout_banner)
    BannarView mBannar;
    public static HomeFragment newInstance(){
        return new HomeFragment();
    }

    @Override
    protected void initView() {
        Log.e("id","idbannar="+mBannar.getId());
       BannarAdapter<Object> bannaradapter=new BannarAdapter<Object>() {
           @Override
           protected void bind(viewHolder holder, Object data) {
            holder.mImgBannar.setImageResource(R.drawable.image_holder_banner);
           }
       };
        mBannar.setAdapter(bannaradapter);
        List<Object> data = new ArrayList<>();
        data.add(new Object());
        data.add(new Object());
        data.add(new Object());
        data.add(new Object());
        data.add(new Object());
        bannaradapter.reset(data);

    }

    @Override
    protected int getlayoutid() {
        return R.layout.fragment_home;
    }

}
