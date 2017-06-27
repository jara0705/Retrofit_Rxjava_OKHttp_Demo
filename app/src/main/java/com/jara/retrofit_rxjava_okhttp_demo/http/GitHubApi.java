package com.jara.retrofit_rxjava_okhttp_demo.http;

import com.jara.retrofit_rxjava_okhttp_demo.bean.Common;
import com.jara.retrofit_rxjava_okhttp_demo.bean.IpModel;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017-6-22.
 */

public interface GitHubApi {
    @GET("service/getIpInfo.php")
    Flowable<IpModel> getIpMsg(@Query("ip") String ip);

    @GET("http://beta.goldenalpha.com.cn/fundworks/media/getFlashScreen")
    Flowable<Common> getSplashImage(@Query("type") int type);
}
