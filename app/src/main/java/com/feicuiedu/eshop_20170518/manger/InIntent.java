package com.feicuiedu.eshop_20170518.manger;

import android.util.Log;

import com.feicuiedu.eshop_20170518.entity.GoodsInfoReq;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by 张志龙 on 2017/5/22.
 */

public class InIntent {

    private static final String BASE_URL = "http://106.14.32.204/eshop/emobile/?url=";
    private OkHttpClient okHttpClient;
    private static InIntent mInIntent;
    private InIntent(){
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

    }
    public static synchronized InIntent getInstance(){
        if (mInIntent==null){
            mInIntent = new InIntent();
        }
        return mInIntent;
    }
    //商品分类
    public Call getCategory(){
        Request request = new Request.Builder()
                .get()
                .url(BASE_URL+"/category")
                .build();
        return okHttpClient.newCall(request);
    }
    public Call getHomeBanner(){
        Request request=new Request.Builder()
                .get()
                .url(BASE_URL+"/home/data")
                .build();
        return okHttpClient.newCall(request);
    }
    public Call getHomeCategory(){
        Request request=new Request.Builder()
                .get()
                .url(BASE_URL+"/home/category")
                .build();
        return okHttpClient.newCall(request);
    }
    


    /*******************************************************************/

    public  void InImg(){

        //构建请求
        final Request request=new Request.Builder()
                //请求方式
                .get()
                .url("http://106.14.32.204/eshop/emobile/?url=/category")
                .addHeader("abc","123")
                .addHeader("123","123")
                .tag(getClass().getSimpleName())
                .build();
//        //同步执行
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Response response=okHttpClient.newCall(request).execute();
//                    response.code();
//                    response.body();
//                    response.header("123");
//                    Headers headers=response.headers();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
        //异步回调
        Call call= okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            //请求失败
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("tag","请求失败");
            }
            //请求成功
            @Override
            public void onResponse(Call call, Response response) throws IOException {
             response.body();
             response.code();
                //看是否真的成功
                if (response.isSuccessful()){
                    String s=response.body().string();
                    Log.e("tag","tag="+s);
                }
            }
        });
        //等待执行
        List<Call> calls= okHttpClient.dispatcher().queuedCalls();
        //正在执行
        List<Call> callsl= okHttpClient.dispatcher().runningCalls();
    }
    protected void onDestroy() {
//        super.onDestroy();
        List<Call> calls = okHttpClient.dispatcher().queuedCalls();
        for (Call call :
                calls) {
//            call.cancel();// 全部取消
            if (call.request().tag()==getClass().getSimpleName()){
                call.cancel();
            }
        }

        List<Call> calllist = okHttpClient.dispatcher().runningCalls();
        for (Call call :
                calllist) {
//            call.cancel();// 全部取消
            if (call.request().tag()==getClass().getSimpleName()){
                call.cancel();
            }
        }
    }

    //上传
    public Call getInfo(){
        GoodsInfoReq goodsInfoReq=new GoodsInfoReq();
        goodsInfoReq.setmGoodsId(78);
        String json=new Gson().toJson(goodsInfoReq);
        //请求体的构建
        RequestBody requestBody=new FormBody.Builder()
                .add("json",json)
                .build();
        Request request=new Request.Builder()
                .post(requestBody)
                .url("http://106.14.32.204/eshop/emobile/?url=/goods")
                .build();
        return okHttpClient.newCall(request);
    }
}
