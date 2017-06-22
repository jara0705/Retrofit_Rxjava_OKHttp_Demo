package com.jara.retrofit_rxjava_okhttp_demo.bean;

import com.jara.retrofit_rxjava_okhttp_demo.MyApplication;

/**
 * Created by Administrator on 2017-6-22.
 */

public interface Constants {

    String API_DEBUG_SERVER_URL = "http://beta.goldenalpha.com.cn/";

    String EXTRA_KEY_EXIT = "extra_key_exit";

    String DOWNLOAD_SPLASH = "download_splash";
    String EXTRA_DOWNLOAD = "extra_download";

    //动态闪屏序列化地址
    String SPLASH_PATH = MyApplication.getContext().getFilesDir().getAbsolutePath() + "/alpha/splash";

    String SPLASH_FILE_NAME = "splash.srr";
}
