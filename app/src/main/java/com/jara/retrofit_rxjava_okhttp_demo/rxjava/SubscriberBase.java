package com.jara.retrofit_rxjava_okhttp_demo.rxjava;

import com.jara.retrofit_rxjava_okhttp_demo.okhttp.ICallBack;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import okhttp3.Response;

/**
 *
 * Created by jara on 2017-5-3.
 */

public class SubscriberBase<T> implements Subscriber<T>, ICallBack {
    @Override
    public void onSubscribe(Subscription s) {
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Exception e) {

    }

    @Override
    public void onResponse(Response response) {

    }
}
