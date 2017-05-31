package com.feicuiedu.eshop_20170518.manger;

import com.feicuiedu.eshop_20170518.entity.SearchReq;
import com.feicuiedu.eshop_20170518.manger.base.ApiInterface;
import com.feicuiedu.eshop_20170518.manger.base.ApiPath;
import com.feicuiedu.eshop_20170518.manger.base.ResponseEntity;
import com.feicuiedu.eshop_20170518.manger.base.UICallback;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
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
    private final Gson mGson;

    private InIntent(){
        mGson = new Gson();
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
                .url(BASE_URL+ ApiPath.HOME_DATA)
                .build();
        return okHttpClient.newCall(request);
    }
    public Call getHomeCategory(){
        Request request=new Request.Builder()
                .get()
                .url(BASE_URL+ApiPath.HOME_CATEGORY)
                .build();
        return okHttpClient.newCall(request);
    }
    //具体的实体类
    public Call getSearch(SearchReq searchReq){
        String json=new Gson().toJson(searchReq);
        RequestBody requestBody=new FormBody.Builder()
                .add("json",json)
                .build();
        Request request=new Request.Builder()
                .post(requestBody)
                .url(BASE_URL+ApiPath.SEARCH)
                .build();
        return okHttpClient.newCall(request);
    }
    //同步回调
    public <T extends ResponseEntity>T  excute(ApiInterface apiInterface) throws IOException {
        Response response=newApiCall(apiInterface,null).execute();
        Class<T> clazz= (Class<T>) apiInterface.getResponseEntity();
        return getResponseEntity(response,clazz);
    }
    //异步回调
    public Call enqueue(ApiInterface apiInterface,
                        UICallback uiCallback,String tag){
        //构建请求
        Call call=newApiCall(apiInterface,tag);
        //告诉ui要转化的数据类型
        uiCallback.setResponseType(apiInterface.getResponseEntity());
        call.enqueue(uiCallback);
        return call;
    }
    //解析实体类
    public   <T extends ResponseEntity>T getResponseEntity(Response response,Class<T> clazz) throws IOException {
      if (!response.isSuccessful()){
          throw new IOException("Response code is "+response.code());
      }
      return mGson.fromJson(response.body().string(),clazz);
    }
    //构建请求
    private Call newApiCall(ApiInterface apiInterface,String tag){
      Request.Builder builder=new Request.Builder();
        builder.url(BASE_URL+apiInterface.getPath());
        if (apiInterface.getRequestParam()!=null){
           String json=new Gson().toJson(apiInterface.getRequestParam());
            RequestBody requestBody=new FormBody.Builder()
                    .add("json",json)
                    .build();
            builder.post(requestBody);

        }
        builder.tag(tag);
        Request request=builder.build();
        return okHttpClient.newCall(request);
    }
    //取消请求
    public void cancelByTag(String tag){
        for (Call call:okHttpClient.dispatcher().queuedCalls()){
            if (call.request().tag().equals(tag)){
                call.cancel();
            }

        }
        for (Call call:okHttpClient.dispatcher().runningCalls()){
            if (call.request().tag().equals(tag)){
                call.cancel();
            }
        }

    }

}
