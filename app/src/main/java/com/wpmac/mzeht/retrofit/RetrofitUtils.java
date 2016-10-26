package com.wpmac.mzeht.retrofit;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wpmac on 16/9/10.
 */
public class RetrofitUtils {

    private Retrofit retrofit;
    private String baseUrl = "http://www.kuaidi100.com/";
    //    private static class SingleLoader{
    private static RetrofitUtils INSTANCE;

    //    }

    /**
     *
     * @return
     */
    public static RetrofitUtils getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RetrofitUtils();
        }
        return INSTANCE;

    }

    public void initOkHttp(@NonNull Context context) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)       //设置连接超时
                .readTimeout(10000L, TimeUnit.MILLISECONDS)          //设置读取超时
                .writeTimeout(10000L, TimeUnit.MILLISECONDS)         //设置写入超时
                .cache(new Cache(context.getCacheDir(), 10 * 1024 * 1024))   //设置缓存目录和10M缓存
                .addInterceptor(interceptor)    //添加日志拦截器（该方法也可以设置公共参数，头信息）
                .build();
        retrofit = new Retrofit.Builder()
                .client(client)        //设置OkHttp
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create()) //  添加数据解析ConverterFactory
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())   //添加RxJava
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
