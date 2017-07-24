package com.jiuyuhulian.lotteryshop.rest;

/**
 * Created by Admin on 2017/3/6.
 */

import android.app.Application;
import android.util.Log;

import com.blankj.utilcode.utils.SPUtils;
import com.jiuyuhulian.lotteryshop.BuildConfig;
import com.jiuyuhulian.lotteryshop.LotteryShopApplication;
import com.jiuyuhulian.lotteryshop.api.ApiServices;
import com.jiuyuhulian.lotteryshop.config.Constant;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.jiuyuhulian.lotteryshop.config.Constant.CONNECT_TIME_OUT;
import static com.jiuyuhulian.lotteryshop.config.Constant.READ_TIME_OUT;

/**
 * 单例模式
 * 网络请求
 */
public class RetrofitHttp {

    private static final String DEFAULT_URL = Constant.BASEURL;
    private static ApiServices singletonwithtoken;
    private static SPUtils sp;

    public void init(Application app) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        //BODY 输出请求和响应的头部和请求体信息
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //缓存
        File cacheFile = new File(LotteryShopApplication.mcontext.getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb

        final CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(LotteryShopApplication.mcontext));

        //增加头部信息
        final Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
               // Log.i("token", getToken());
                Request build = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .url(chain.request().url() + "?jy-token=" + getToken())
                        .build();
                Log.e("token", chain.request().url().toString());
                return chain.proceed(build);
            }
        };


        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            //设置 Debug Log 模式
            builder.addInterceptor(interceptor);
        }
        builder.readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .cookieJar(cookieJar)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .addInterceptor(headerInterceptor)
                .addInterceptor(new ReceivedCookiesInterceptor(LotteryShopApplication.mcontext))
                .addInterceptor(new AddCookiesInterceptor(LotteryShopApplication.mcontext))
                .cache(cache)
                .build();

        OkHttpClient client = builder.build();
        singletonwithtoken = createRetrofit(client).create(ApiServices.class);
    }

    public static ApiServices getApiServiceWithToken(){
        return singletonwithtoken;
    }

    private static Retrofit createRetrofit(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DEFAULT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }


    private static String getToken() {
        sp = new SPUtils("regist");
        if (sp.getString("token", "").equals("") || sp.getString("token","") == null) {
            return null;
        } else {
           // Log.e("token", sp.getString("token",""));
            return sp.getString("token","");
        }
    }

}
