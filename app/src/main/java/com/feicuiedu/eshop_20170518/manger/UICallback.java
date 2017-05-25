package com.feicuiedu.eshop_20170518.manger;


import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 张志龙 on 2017/5/22.
 */

public abstract class UICallback implements Callback {
    private Handler mHandler=new Handler(Looper.getMainLooper());
    @Override
    public void onFailure(final Call call, final IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                onFailureInUI(call,e);
            }
        });
    }

    @Override
    public void onResponse(final Call call, final Response response) throws IOException {
     mHandler.post(new Runnable() {
         @Override
         public void run() {
             try {
                 onResponseInUI(call,response);
             } catch (IOException e) {
                 e.printStackTrace();
             }

         }
     });
    }
    public abstract void onFailureInUI(Call call, IOException e);
    public abstract void onResponseInUI(Call call, Response response) throws IOException;
}
