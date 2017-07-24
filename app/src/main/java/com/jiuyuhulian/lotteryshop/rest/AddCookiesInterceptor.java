package com.jiuyuhulian.lotteryshop.rest;

import android.content.Context;

import com.blankj.utilcode.utils.SPUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by khb on 2017/4/25.
 */
public class AddCookiesInterceptor implements Interceptor {
    private Context context;

    public AddCookiesInterceptor(Context context) {
        super();
        this.context = context;

    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        final Request.Builder builder = chain.request().newBuilder();
        SPUtils spUtils = new SPUtils("cookie");
        Observable.just(spUtils.getString("cookie", ""))
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String cookie) {
                        //添加cookie
                        builder.addHeader("Cookie", cookie);
                    }
                });
        return chain.proceed(builder.build());
    }
}
