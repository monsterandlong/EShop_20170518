package com.feicuiedu.eshop_20170518.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.feicuiedu.eshop_20170518.R;
import com.feicuiedu.eshop_20170518.base.utils.BaseActivity;
import com.feicuiedu.eshop_20170518.base.utils.toast.ToastWarp;
import com.feicuiedu.eshop_20170518.entity.GoodsInfo;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 张志龙 on 2017/6/2.
 */

public class GoodsPopupWindow extends PopupWindow implements PopupWindow.OnDismissListener {

    @BindView(R.id.image_goods)
    ImageView mImgGoods;
    @BindView(R.id.text_goods_price)
    TextView mTvGoodsPrice;
    @BindView(R.id.text_inventory)
    TextView mTvInventory;
    @BindView(R.id.text_inventory_value)
    TextView mTvInventoryValue;
    @BindView(R.id.text_number)
    TextView mTvNumber;
    @BindView(R.id.text_number_value)
    TextView mTvNumberValue;
    @BindView(R.id.button_ok)
    Button buttonOk;
    @BindView(R.id.number_picker)
    SimpleNumberPicker mNumberPicker;
    private BaseActivity activity;
    private GoodsInfo goodsInfo;
    private final ViewGroup mParent;
    private OnConfirmListener mOnConfirmListener;

    public GoodsPopupWindow(BaseActivity activity, @NonNull GoodsInfo goodsInfo) {
        this.activity = activity;
        this.goodsInfo = goodsInfo;
        // 布局的填充
        // 获取顶层视图
        mParent = (ViewGroup) activity.getWindow().getDecorView();
        Context context = mParent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.popup_goods_spec, mParent, false);
        // 设置布局
        setContentView(view);
        // 设置宽和高
        setHeight(context.getResources().getDimensionPixelSize(R.dimen.size_400));
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置背景
        setBackgroundDrawable(new ColorDrawable());
        // 设置得到焦点
        setFocusable(true);
        // 设置点击窗口外部，PopupWindow消失
        setOutsideTouchable(true);
        // 设置消失的监听：弹出和隐藏的时候改变透明度
        setOnDismissListener(this);
        ButterKnife.bind(this, view);
        initView();
    }

    // 视图的初始化
    private void initView() {
        Picasso.with(mParent.getContext()).load(goodsInfo.getImg().getLarge()).into(mImgGoods);
        mTvGoodsPrice.setText(goodsInfo.getShopPrice());
        mTvInventory.setText(String.valueOf(goodsInfo.getNumber()));
        // 商品的数量：通过数量选择器决定
        mTvNumber.setText(String.valueOf(mNumberPicker.getNumber()));
        mNumberPicker.setOnNumberChangeListener(new SimpleNumberPicker.OnNumberChangeListener() {
            @Override
            public void onNumberChanged(int number) {
                mTvNumber.setText(String.valueOf(number));
            }
        });

    }

    @OnClick(R.id.button_ok)
    public void onClickOk(View view){
        // 具体选择了多少商品需要给使用者传递出去，具体的操作事件我们并不知道
        // 具体的事件交给使用者处理
        int number=mNumberPicker.getNumber();
        if (number==0){
            ToastWarp.show(R.string.goods_msg_must_choose_number);
            return;
        }
        mOnConfirmListener.onConfirm(number);
    }
    // show方法：展示PopupWindow,参数中将监听传递过来
    public void show(OnConfirmListener onConfirmListener){
        this.mOnConfirmListener = onConfirmListener;
        // 从哪显示出来
        showAtLocation(mParent, Gravity.BOTTOM,0,0);
        backgroundAlpha(0.5f);// 具体操作

    }

    private void backgroundAlpha(float v) {
        // 设置给activity的窗体的透明度
        WindowManager.LayoutParams layoutParams=activity.getWindow().getAttributes();
        layoutParams.alpha=v;
        activity.getWindow().setAttributes(layoutParams);
    }

    @Override
    public void onDismiss() {
        backgroundAlpha(1.0f);

    }
    // 利用接口回调：将选择的数量传递出去
    public interface OnConfirmListener{
        void onConfirm(int number);
    }
}
