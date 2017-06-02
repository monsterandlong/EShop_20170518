package com.feicuiedu.eshop_20170518.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.feicuiedu.eshop_20170518.R;
import com.feicuiedu.eshop_20170518.view.SimpleNumberPicker;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 张志龙 on 2017/5/31.
 */

public class TestFragment extends Fragment {
    private static final String ARGUMENTS_TEXT = "arguments_text";
    @BindView(R.id.text)
    TextView mText;
    @BindView(R.id.numberPicker)
    SimpleNumberPicker mNumberPicker;
    // 不建议在构造方法中传递数据，官方推荐的方式是采用setArguments()的方法传递数据
    public static TestFragment newInstance(String text){
        TestFragment testFragment = new TestFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENTS_TEXT,text);
        testFragment.setArguments(bundle);// 传递数据
        return testFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        ButterKnife.bind(this, view);

        // 切换到不同的Fragment，展示不同的文本
        mText.setText(getArgumentsText());
        mNumberPicker.setOnNumberChangeListener(new SimpleNumberPicker.OnNumberChangeListener() {
            @Override
            public void onNumberChanged(int number) {
                mText.setText(String.valueOf(number));
            }
        });

        return view;
    }

    // 拿到传递的数据
    public String getArgumentsText(){
        return getArguments().getString(ARGUMENTS_TEXT);
    }
}
