package com.feicuiedu.eshop_20170518.base.utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.feicuiedu.eshop_20170518.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 张志龙 on 2017/5/19.
 */

public class TextFragment extends Fragment {
    private static final String ARGUMENTS_TEXT = "arguments_text";
    @BindView(R.id.text)
    TextView mText;

    public static TextFragment newInstance(String text){
        TextFragment textFragment=new TextFragment();
        Bundle bundle=new Bundle();
        bundle.putString(ARGUMENTS_TEXT,text);
        textFragment.setArguments(bundle);
        return textFragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        ButterKnife.bind(this,view);
        mText.setText(getArgumentsText());
        return view;
    }
    public String getArgumentsText(){
        return getArguments().getString(ARGUMENTS_TEXT );
    }

}
