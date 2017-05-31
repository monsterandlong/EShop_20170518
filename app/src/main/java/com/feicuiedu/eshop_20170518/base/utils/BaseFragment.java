package com.feicuiedu.eshop_20170518.base.utils;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feicuiedu.eshop_20170518.manger.InIntent;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 张志龙 on 2017/5/23.
 */

public abstract class BaseFragment extends Fragment {

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getlayoutid(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        InIntent.getInstance().cancelByTag(getClass().getSimpleName());
        unbinder.unbind();
        unbinder=null;
    }

    protected abstract void initView();
    @LayoutRes protected abstract int getlayoutid();
}
