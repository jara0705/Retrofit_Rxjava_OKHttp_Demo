package com.jara.retrofit_rxjava_okhttp_demo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jara.retrofit_rxjava_okhttp_demo.okhttp.ICallBack;
import com.jara.retrofit_rxjava_okhttp_demo.okhttp.OkHttpDemoUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 验证封装的OkHttp3，实现下载图片并展示
 * Created by jara on 2017-5-2.
 */

public class OkHttpActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button button;

    private static final String IMAGE_URL =
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493702692204&di=369d437aa1f4cf303ccc7c9c6a25ef03&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fitem%2F201602%2F10%2F20160210215552_eUz32.thumb.700_0.jpeg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);

        imageView = (ImageView) findViewById(R.id.image_get);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpDemoUtil.getmInstance().getAsynHttp(IMAGE_URL, new ICallBack() {
                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                        Log.i("MainActivity","okhttp出现异常");
                    }

                    @Override
                    public void onResponse(Response response) {
                        Log.i("MainActivity","okhttp连接成功");
                        ResponseBody responseBody = response.body();
                        InputStream is = responseBody.byteStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.WEBP, 100, baos);
                        Glide.with(OkHttpActivity.this).load(baos.toByteArray()).into(imageView);
                        try {
                            responseBody.close();
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
