package com.jara.retrofit_rxjava_okhttp_demo.okhttp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jara on 2017-4-26.
 */

public class OkHttpDemoUtil {

    public static OkHttpClient okHttpClient;

    private static final String TAG = "OkHttpDemoUtil";
    private static final String GET_UTL = "http://www.baidu.com";
    private Context context;

    public OkHttpDemoUtil() {
        getOkHttpClient();
    }

    /**
     * 异步Get请求
     */
    public static void getAsynHttp() {
        Request.Builder builder = new Request.Builder().url(GET_UTL);
        builder.method("GET", null);
        Request request = builder.build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().toString();
                Log.i(TAG, "当前线程名---->" + Thread.currentThread().getName());

            }
        });
    }

    public static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build();
        }
        return okHttpClient;
    }

}
