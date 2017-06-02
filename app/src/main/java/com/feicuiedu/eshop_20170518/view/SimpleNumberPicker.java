package com.feicuiedu.eshop_20170518.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feicuiedu.eshop_20170518.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 张志龙 on 2017/6/1.
 */

public class SimpleNumberPicker extends RelativeLayout {
    @BindView(R.id.image_minus)
    ImageView mImgMinus;
    @BindView(R.id.text_number)
    TextView mTvNumber;
    @BindView(R.id.image_plus)
    ImageView mImgPlus;
    private int mMin;
    private int number;
    private OnNumberChangeListener mOnNumberChangeListener;

    public SimpleNumberPicker(Context context) {
        super(context);
        init(context,null);
    }

    public SimpleNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public SimpleNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.widget_simple_number_picker, this, true);
        ButterKnife.bind(this);
        if (attrs == null) return;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SimpleNumberPicker);
        mMin = typedArray.getInteger(R.styleable.SimpleNumberPicker_min_number, 0);
        typedArray.recycle();
        setNumber(mMin);
        mImgMinus.setImageResource(R.drawable.btn_minus_gray);

    }


    @OnClick({R.id.image_minus, R.id.image_plus})
    public void onClick(View view) {
        if (view.getId()==R.id.image_minus){
            if (getNumber()==mMin){
                return;
            }
            setNumber(getNumber()-1);
        }else {
            setNumber(getNumber()+1);
        }
        if (getNumber()==mMin){
            mImgMinus.setImageResource(R.drawable.btn_minus_gray);
        }else {
            mImgMinus.setImageResource(R.drawable.btn_minus);
        }
        if (mOnNumberChangeListener!=null){
            mOnNumberChangeListener.onNumberChanged(getNumber());
        }
    }

    public void setNumber(int number) {
        if (number<mMin){
            throw new IllegalArgumentException("Min Number is "+mMin+" while number is "+number);
        }
            mTvNumber.setText(String.valueOf(number));

    }
    public int getNumber(){
        String str=mTvNumber.getText().toString();
        return Integer.parseInt(str);
    }
    public void setOnNumberChangeListener(OnNumberChangeListener onNumberChangeListener){
        mOnNumberChangeListener= onNumberChangeListener;
    }

    // 对外提供一个接口：利用接口回调，写一个方法：用于传递数量的变化
    public interface OnNumberChangeListener{
        void onNumberChanged(int number);
    }
}
