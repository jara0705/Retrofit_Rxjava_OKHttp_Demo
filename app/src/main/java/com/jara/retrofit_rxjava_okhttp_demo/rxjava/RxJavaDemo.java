package com.jara.retrofit_rxjava_okhttp_demo.rxjava;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.jara.retrofit_rxjava_okhttp_demo.bean.IpModel;
import com.jara.retrofit_rxjava_okhttp_demo.okhttp.ICallBack;
import com.jara.retrofit_rxjava_okhttp_demo.okhttp.OkHttpDemoUtil;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by jara on 2017-5-3.
 */

public class RxJavaDemo {

    private static final String TAG = "RxJavaDemo";

    public static void get(String s, final ICallBack iCallBack) {

        Subscriber<String> subscriber1 = new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "url---->" + s);
                OkHttpDemoUtil.getmInstance().getAsynHttpByRxJava(s, iCallBack);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        };

        Log.i(TAG, "url---->" + s);
        Flowable.just(s)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber1);
    }

}
