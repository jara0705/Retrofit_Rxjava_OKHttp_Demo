package com.jara.retrofit_rxjava_okhttp_demo.rxjava;

import com.jara.retrofit_rxjava_okhttp_demo.okhttp.ICallBack;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;

import okhttp3.Response;

/**
 *
 * Created by jara on 2017-5-3.
 */

public abstract class SubscriberBase<T> implements Subscriber<T> {

    public T result;
    @Override
    public void onSubscribe(Subscription s) {
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(T t) {
        result = t;
        try {
            onResponse(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable t) {
        onError((Exception) t);
    }

    @Override
    public void onComplete() {

    }

    public abstract void onResponse(T t) throws IOException;
    public abstract void onError(Exception e);


}
