package com.jara.retrofit_rxjava_okhttp_demo.retrofit;

import com.google.gson.Gson;

import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.jara.retrofit_rxjava_okhttp_demo.bean.Common;
import com.jara.retrofit_rxjava_okhttp_demo.bean.IpModel;
import com.jara.retrofit_rxjava_okhttp_demo.http.GitHubApi;
import com.jara.retrofit_rxjava_okhttp_demo.okhttp.OKHttpClientFactory;
import com.jara.retrofit_rxjava_okhttp_demo.rxjava.SubscriberBase;


import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jara on 2017-5-9.
 */

public class RetrofitDemo {

    private static final String TAG = "RetrofitDemo";
    private static RetrofitDemo instance;
    private static GitHubApi service;

    private RetrofitDemo() {
        initService();
    }

    public static synchronized RetrofitDemo getInstance() {
        if (instance == null) {
            instance = new RetrofitDemo();
        }
        return instance;
    }

    public static GitHubApi getService() {
        if (service == null) {
            initService();
        }
        return service;
    }

    private static void initService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://")
                .client(OKHttpClientFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        service = retrofit.create(GitHubApi.class);
    }

    public static void getIp(String ip) {
        getService().getIpMsg(ip)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SubscriberBase<IpModel>() {

                    @Override
                    public void onResponse(IpModel ipModel) {
                        Log.i(TAG, "country---->" + ipModel.getData().getCountry());
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                        Log.i(TAG, "country--->failed");
                    }
                });
    }

    public static void getSplashImage(int type) {
        getService().getSplashImage(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SubscriberBase<Common>() {
                    @Override
                    public void onResponse(Common common) {

                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
    }

}
