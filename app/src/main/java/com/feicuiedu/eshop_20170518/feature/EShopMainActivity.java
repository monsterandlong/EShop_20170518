package com.feicuiedu.eshop_20170518.feature;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.feicuiedu.eshop_20170518.R;
import com.feicuiedu.eshop_20170518.base.utils.BaseActivity;
import com.feicuiedu.eshop_20170518.base.utils.TextFragment;
import com.feicuiedu.eshop_20170518.fragment.CategoryFragment;
import com.feicuiedu.eshop_20170518.fragment.HomeFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;

// 主页面
public class EShopMainActivity extends BaseActivity implements OnTabSelectListener {

    @BindView(R.id.bottom_bar)
    BottomBar mBottomBar;
    HomeFragment mHome;
    CategoryFragment mCategory;
    TextFragment mCart;
    TextFragment mMine;
    Fragment mCurrentFragment;


    public void initView(){
        //看是否有Fragment。有就手动恢复
        restoreFragment();
        mBottomBar.setOnTabSelectListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_eshop_main;
    }

    private void restoreFragment() {
        //防止横竖屏切换时否，发生重叠
        FragmentManager manager=getSupportFragmentManager();
        mHome= (HomeFragment) manager.findFragmentByTag(HomeFragment.class.getName());
        mCategory= (CategoryFragment) manager.findFragmentByTag(CategoryFragment.class.getName());
        mCart= (TextFragment) manager.findFragmentByTag("CartFragment");
        mMine= (TextFragment) manager.findFragmentByTag("MineFragment");

    }

    @Override
    public void onTabSelected(@IdRes int tabId) {
        switch (tabId){
            case R.id.tab_home:
                if (mHome==null){
                    mHome=HomeFragment.newInstance();
                }
                switchFragment(mHome);
                break;
            case R.id.tab_category:
                if (mCategory==null){
                    mCategory= CategoryFragment.newInstance();
                }
                switchFragment(mCategory);
                break;
            case R.id.tab_cart:
                Toast.makeText(this, "购物车", Toast.LENGTH_SHORT).show();
                if (mCart==null){
                    mCart=TextFragment.newInstance("CartFragment");
                }
                switchFragment(mCart);
                break;
            case R.id.tab_mine:
                Toast.makeText(this, "我的", Toast.LENGTH_SHORT).show();
                if (mMine==null){
                    mMine=TextFragment.newInstance("MineFragment");
                }
                switchFragment(mMine);
                break;
            default:
                throw new UnsupportedOperationException("unsupport");
        }
    }
    private void switchFragment(Fragment fragment){
        if (mCurrentFragment== fragment)return;
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        if (mCurrentFragment!=null){
            //代表显示的有，就隐藏
            transaction.hide(mCurrentFragment);
        }
        //添加
        if (fragment.isAdded()){
            transaction.show(fragment);
        }else {
            String tag;
            if (fragment instanceof TextFragment){
                tag=((TextFragment)fragment).getArgumentsText();
            }else {
                tag=fragment.getClass().getName();
            }
            transaction.add(R.id.layout_container,fragment,tag);
        }
        transaction.commit();
        mCurrentFragment=fragment;

    }
    public void onBackPressed(){
        if (mCurrentFragment!=mHome){
            mBottomBar.selectTabWithId(R.id.tab_home);
            return;
        }
        //如果不是首页，不关程序，退到后台运行
        moveTaskToBack(true);

    }

}
