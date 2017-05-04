package com.jara.retrofit_rxjava_okhttp_demo.rxjava;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.jara.retrofit_rxjava_okhttp_demo.okhttp.ICallBack;
import com.jara.retrofit_rxjava_okhttp_demo.okhttp.OkHttpDemoUtil;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;


/**
 *
 * Created by jara on 2017-5-3.
 */

public class RxJavaDemo {

    public static void get(String s, final ICallBack iCallBack) {

        Subscriber<String> subscriber1 = new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(String s) {
                Log.i("RxJavaDemo", "url---->" + s);
                OkHttpDemoUtil.getmInstance().getAsynHttpByRxJava(s, iCallBack);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        };

        Log.i("RxJavaDemo", "url---->" + s);
        Flowable.just(s)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber1);
    }

}
