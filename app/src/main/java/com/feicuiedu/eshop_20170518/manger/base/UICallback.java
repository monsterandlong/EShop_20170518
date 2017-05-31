package com.feicuiedu.eshop_20170518.manger.base;


import android.os.Handler;
import android.os.Looper;

import com.feicuiedu.eshop_20170518.R;
import com.feicuiedu.eshop_20170518.base.utils.toast.ToastWarp;
import com.feicuiedu.eshop_20170518.manger.InIntent;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 张志龙 on 2017/5/22.
 */

public abstract class UICallback implements Callback {
    private Handler mHandler=new Handler(Looper.getMainLooper());
    //要转化的数据类型
    public Class<? extends ResponseEntity> mResponseType;

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
    //对请求做出的回应
    public  void onFailureInUI(Call call, IOException e){
        //失败的回应
        ToastWarp.show(R.string.error_network);

    }
    //请求成功的回应
    public  void onResponseInUI(Call call, Response response) throws IOException{
        if (response.isSuccessful()){
            ResponseEntity responseEntity=InIntent.getInstance().getResponseEntity(response,mResponseType);
            if (responseEntity==null||responseEntity.getStatus()==null){
                throw new RuntimeException("Fatal Api Error");
            }
            //判断响应数据是否正确
            if (responseEntity.getStatus().isSuccees()){
               onBusinessResponse(true,responseEntity);
            }else {
                ToastWarp.show(responseEntity.getStatus().getDesc());
                onBusinessResponse(false,responseEntity);
            }

        }
    }
    public void setResponseType(Class<? extends ResponseEntity> responseType){
        mResponseType = responseType;

    }
    //给调用者实现的方法
    protected abstract void onBusinessResponse(boolean isSuccess, ResponseEntity responseEntity);

}
