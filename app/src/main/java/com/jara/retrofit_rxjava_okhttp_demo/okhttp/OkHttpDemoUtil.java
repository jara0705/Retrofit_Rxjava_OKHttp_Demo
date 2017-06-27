package com.jara.retrofit_rxjava_okhttp_demo.okhttp;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by jara on 2017-4-26.
 */

public class OkHttpDemoUtil {

    private volatile static OkHttpDemoUtil mInstance;

    private OkHttpClient.Builder okBuild;
    private Handler mHandler;

    private static final String TAG = "OkHttpDemoUtil";

    public static OkHttpDemoUtil getmInstance() {
        if (mInstance == null) {
            synchronized (OkHttpDemoUtil.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpDemoUtil();
                }
            }
        }
        return mInstance;
    }

    private OkHttpDemoUtil() {
        mHandler = new Handler();
        okBuild = new OkHttpClient.Builder();
        okBuild.connectTimeout(60, TimeUnit.SECONDS);
        okBuild.readTimeout(60, TimeUnit.SECONDS);
        okBuild.writeTimeout(60, TimeUnit.SECONDS);
    }

    /**
     * 异步Get请求
     */
    public void getAsynHttp(String url, ICallBack iCallBack) {
        Log.i(TAG, "getAsynHttp---->" + url);
        Request.Builder builder = new Request.Builder().url(url);
        builder.method("GET", null);
        Request request = builder.build();
        Call call = getOkHttpClient().newCall(request);
        result(call, iCallBack);
    }

    public void getAsynHttpByRxJava(String url, ICallBack iCallBack) {
        Log.i(TAG, "getAsynHttp---->" + url);
        Request.Builder builder = new Request.Builder().url(url);
        builder.method("GET", null);
        Request request = builder.build();
        Call call = getOkHttpClient().newCall(request);
        result1(call, iCallBack);
    }

    /**
     * 异步Post请求
     */
    public void postAsynHttp(String url, ICallBack iCallBack) {
        RequestBody body = new FormBody.Builder()
                .add("size", "10")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = getOkHttpClient().newCall(request);
        result(call, iCallBack);
    }

    private void result1(Call call, final ICallBack iCallBack) {
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                Log.i(TAG, "okhttp连接失败");

                if (iCallBack != null) {
                    iCallBack.onError(e);
                }

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                Log.i(TAG, "okhttp连接成功");
                if (iCallBack != null) {
                    iCallBack.onResponse(response);
                }

            }
        });
    }

    private void result(Call call, final ICallBack iCallBack) {
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                Log.i(TAG, "okhttp连接失败");
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (iCallBack != null) {
                            iCallBack.onError(e);
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                Log.i(TAG, "okhttp连接成功");
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (iCallBack != null) {
                            try {
                                iCallBack.onResponse(response);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
    }

    private OkHttpDemoUtil setCache(Context context) {
        File sdcache = context.getExternalCacheDir();
        int cacheSize = 1024 * 1024 * 10;
        okBuild.cache(new Cache(sdcache, cacheSize));
        return mInstance;
    }

    private OkHttpClient getOkHttpClient() {

        return okBuild.build();
    }

}
