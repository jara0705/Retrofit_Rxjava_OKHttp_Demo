package com.jara.retrofit_rxjava_okhttp_demo.okhttp;

import java.io.FileNotFoundException;
import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jara on 2017-4-27.
 */

public interface ICallBack<T> {
    void onError(Exception e);
    void onResponse(Response response) throws IOException;
}
