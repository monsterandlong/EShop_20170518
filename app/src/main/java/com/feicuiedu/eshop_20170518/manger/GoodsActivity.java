package com.feicuiedu.eshop_20170518.manger;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.feicuiedu.eshop_20170518.R;
import com.feicuiedu.eshop_20170518.base.utils.BaseActivity;
import com.feicuiedu.eshop_20170518.base.utils.ToolbarWarp;
import com.feicuiedu.eshop_20170518.base.utils.toast.ToastWarp;
import com.feicuiedu.eshop_20170518.entity.GoodsInfo;
import com.feicuiedu.eshop_20170518.entity.GoodsInfoRsp;
import com.feicuiedu.eshop_20170518.fragment.Api.ApiGoodsInfo;
import com.feicuiedu.eshop_20170518.manger.base.ResponseEntity;
import com.feicuiedu.eshop_20170518.manger.base.UICallback;
import com.feicuiedu.eshop_20170518.view.GoodsPopupWindow;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

/**
 * Created by 张志龙 on 2017/5/31.
 */

public class GoodsActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.pager_goods)
    ViewPager mGoodsPager;
    @BindViews({R.id.text_tab_goods,R.id.text_tab_details,R.id.text_tab_comments})
    List<TextView> mTvTabList;
    private static final String EXTRA_GOODS_ID = "goodsId";
    private GoodsInfo goodsInfo;
    private GoodsPopupWindow mGoodsPopupWindow;


    public static Intent getStarIntent(Context context,int Id){
        Intent intent=new Intent(context,GoodsActivity.class);
        intent.putExtra(EXTRA_GOODS_ID,Id);
        return intent;

    }
    @Override
    protected void initView() {
        new ToolbarWarp(this);
        mGoodsPager.addOnPageChangeListener(this);
        // 拿到传递的数据
        int goodId=getIntent().getIntExtra(EXTRA_GOODS_ID, 0);
        // 获取数据
        InIntent.getInstance().enqueue(new ApiGoodsInfo(goodId),mGoosCallback,getClass().getSimpleName());

    }
    protected UICallback mGoosCallback=new UICallback() {



        @Override
        protected void onBusinessResponse(boolean isSuccess, ResponseEntity responseEntity) {
          if (isSuccess){
              GoodsInfoRsp goodsInfoRsp= (GoodsInfoRsp) responseEntity;
              goodsInfo = goodsInfoRsp.getData();
              mGoodsPager.setAdapter(new GoodsInfoAdapter(getSupportFragmentManager(), goodsInfo));
              chooseTab(0);
          }
        }
    };

    @OnClick({R.id.text_tab_goods,R.id.text_tab_details,R.id.text_tab_comments})
    public void onClickTab(TextView textView){
        int position=mTvTabList.indexOf(textView);
        mGoodsPager.setCurrentItem(position);
        chooseTab(position);

    }

    private void chooseTab(int position) {
        Resources resources=getResources();
        for (int i = 0; i < mTvTabList.size(); i++) {
            mTvTabList.get(i).setSelected(i==position);
            float textSize=i==position?resources.getDimension(R.dimen.font_large):resources.getDimension(R.dimen.font_normal);
            mTvTabList.get(i).setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
        }
    }

    @OnClick({R.id.button_add_cart,R.id.button_buy})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.button_add_cart:
                showGoodsPopupWindow();
                break;
            case R.id.button_buy:
                showGoodsPopupWindow();
                break;

        }

    }
    // 展示商品选择的弹窗
    private void showGoodsPopupWindow(){
        if (goodsInfo==null)return;
        if (mGoodsPopupWindow==null){
            mGoodsPopupWindow = new GoodsPopupWindow(this,goodsInfo);
        }
        mGoodsPopupWindow.show(new GoodsPopupWindow.OnConfirmListener() {
            @Override
            public void onConfirm(int number) {
                // 具体操作
                ToastWarp.show("Confirm:"+number);
                mGoodsPopupWindow.dismiss();
            }
        });

    }
    // 填充选项菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_goods,menu);
        return super.onCreateOptionsMenu(menu);
    }
    // 处理选项菜单的item选择事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.menu_search){
            ToastWarp.show(R.string.action_share);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_goods;
    }
    //**********pager的监听
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        chooseTab(position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    public void selectPage(int position){
          mGoodsPager.setCurrentItem(position,true);
        chooseTab(position);
    }
}
