package com.jara.retrofit_rxjava_okhttp_demo.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.jara.retrofit_rxjava_okhttp_demo.bean.Common;
import com.jara.retrofit_rxjava_okhttp_demo.bean.Constants;
import com.jara.retrofit_rxjava_okhttp_demo.bean.Splash;
import com.jara.retrofit_rxjava_okhttp_demo.okhttp.ICallBack;
import com.jara.retrofit_rxjava_okhttp_demo.okhttp.OkHttpDemoUtil;
import com.jara.retrofit_rxjava_okhttp_demo.retrofit.RetrofitDemo;
import com.jara.retrofit_rxjava_okhttp_demo.rxjava.SubscriberBase;
import com.jara.retrofit_rxjava_okhttp_demo.util.SerializableUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Response;

import static com.jara.retrofit_rxjava_okhttp_demo.util.SerializableUtils.readObject;

/**
 * Created by jara on 2017-6-23.
 */

public class SplashDownLoadService extends IntentService {

    private Splash mScreen;
    public static final int TYPE_ANDROID = 1;
    private static final String SPLASH_FILE_NAME = "splash.srr";

    public SplashDownLoadService() {
        super("SplashDownLoad");
    }

    public static void startDownLoadSplashImage(Context context, String action) {
        Intent intent = new Intent(context, SplashDownLoadService.class);
        intent.putExtra(Constants.EXTRA_DOWNLOAD, action);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String action = intent.getStringExtra(Constants.EXTRA_DOWNLOAD);
            if (action.equals(Constants.DOWNLOAD_SPLASH)) {
                loadSplashNetDate();
            }
        }
    }

    private void loadSplashNetDate() {
        RetrofitDemo.getService().getSplashImage(TYPE_ANDROID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SubscriberBase<Common>() {
                    @Override
                    public void onResponse(Common common) throws IOException {
                        if (common.isValid() && common.attachment != null) {
                            mScreen = common.attachment.flashScreen;
                            Splash splashLocal = getSplashLocal();
                            if (mScreen != null) {
                                if (splashLocal == null) {
                                    startDownLoadSplash(Constants.SPLASH_PATH, mScreen.burl);
                                } else if (isNeedDownLoad(splashLocal.savePath, mScreen.burl)) {
                                    startDownLoadSplash(Constants.SPLASH_PATH, mScreen.burl);
                                }
                            } else {
                                if (splashLocal != null) {
                                    File splashFile = SerializableUtils.getSerializableFile(Constants.SPLASH_PATH, Constants.SPLASH_FILE_NAME);
                                    if (splashFile.exists()) {
                                        splashFile.delete();
                                        Log.d("SplashDemo","mScreen为空删除本地文件");
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.i("SplashDownLoadService", "loadSplashNetDate() failed");
                    }
                });
    }

    private Splash getSplashLocal() {
        Splash splash = null;
        try {
            File splashFile = SerializableUtils.getSerializableFile(Constants.SPLASH_PATH, SPLASH_FILE_NAME);
            splash = (Splash) readObject(splashFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return splash;
    }

    private void startDownLoadSplash(final String splashPath, final String burl) {
        OkHttpDemoUtil.getmInstance().getAsynHttp(burl, new ICallBack() {
            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                FileOutputStream fos = null;
                InputStream is = response.body().byteStream();
                File file = new File(splashPath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                String[] split = burl.split("/");
                String fileName = split[split.length - 1];
                File mApkFile = new File(splashPath, fileName);
                String savePath = mApkFile.getAbsolutePath();
                fos = new FileOutputStream(mApkFile, false);
                byte[] buf = new byte[1024];
                while(true) {
                    int read = is.read(buf);
                    if (read == -1) {
                        break;
                    }
                    fos.write(buf, 0, read);
                }
                if (mScreen != null) {
                    mScreen.savePath = savePath;
                }
                SerializableUtils.writeObject(mScreen, Constants.SPLASH_PATH + "/" + SPLASH_FILE_NAME);
                fos.flush();
                fos.close();
                is.close();
            }
        });
    }

    /**
     * @param path 本地存储的图片绝对路径
     * @param url  网络获取url
     * @return 比较储存的 图片名称的哈希值与 网络获取的哈希值是否相同
     */
    private boolean isNeedDownLoad(String path, String url) {
        if (TextUtils.isEmpty(path)) {
            Log.d("SplashDemo","本地url " + TextUtils.isEmpty(path));
            Log.d("SplashDemo","本地url " + TextUtils.isEmpty(url));
            return true;
        }
        File file = new File(path);
        if (!file.exists()) {
            Log.d("SplashDemo","本地file " + file.exists());
            return true;
        }
        if (getImageName(path).hashCode() != getImageName(url).hashCode()) {
            Log.d("SplashDemo","path hashcode " + getImageName(path) + " " + getImageName(path).hashCode());
            Log.d("SplashDemo","url hashcode " + getImageName(url) + " " + getImageName(url).hashCode());
            return true;
        }
        return false;
    }

    private String getImageName(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        String[] split = url.split("/");
        String nameWith_ = split[split.length - 1];
        String[] split1 = nameWith_.split("\\.");
        return split1[0];
    }

}
