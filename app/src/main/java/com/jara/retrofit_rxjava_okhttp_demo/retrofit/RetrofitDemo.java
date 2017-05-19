package com.jara.retrofit_rxjava_okhttp_demo.retrofit;

import com.google.gson.Gson;

import android.util.Log;

import com.jara.retrofit_rxjava_okhttp_demo.bean.IpModel;
import com.jara.retrofit_rxjava_okhttp_demo.okhttp.OKHttpClientFactory;
import com.jara.retrofit_rxjava_okhttp_demo.okhttp.OkHttpDemoUtil;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 *
 * Created by jara on 2017-5-9.
 */

public class RetrofitDemo {

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://ip.taobao.com/service/")
            .client(OKHttpClientFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(new Gson()))
            .build();

    private static GitHubApi gitHubApi = retrofit.create(GitHubApi.class);

    interface GitHubApi{
        @GET("getIpInfo.php")
        Call<IpModel> getIpMsg(@Query("ip") String ip);

    }

    public static void getIp(String ip) {
        gitHubApi.getIpMsg(ip).enqueue(new Callback<IpModel>() {
            @Override
            public void onResponse(Call<IpModel> call, Response<IpModel> response) {
                String country = response.body().getData().getCountry();
                Log.i("Retrofit2", "country---->" + country);
            }

            @Override
            public void onFailure(Call<IpModel> call, Throwable t) {
                Log.i("Retrofit2", "country---->get failed");
            }
        });
    }

}
