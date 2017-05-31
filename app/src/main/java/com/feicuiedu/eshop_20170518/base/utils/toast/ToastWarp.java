package com.feicuiedu.eshop_20170518.base.utils.toast;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 张志龙 on 2017/5/25.
 */

public class ToastWarp {

    private static Context mContext;
    private static Toast mToast;

    public static void init(Context context){
        mContext = context;
        mToast = Toast.makeText(mContext,null,Toast.LENGTH_SHORT);

    }
    public static void show(int resId,Object... args){
        String string=mContext.getString(resId,args);
        mToast.setText(string);
        mToast.show();

    }
    public static void show(CharSequence charSequence,Object...args){
        String text=String.format(charSequence.toString(),args);
        mToast.setText(text);
        mToast.show();
    }
}
