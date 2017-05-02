package com.jara.retrofit_rxjava_okhttp_demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jara.retrofit_rxjava_okhttp_demo.okhttp.ICallBack;
import com.jara.retrofit_rxjava_okhttp_demo.okhttp.OkHttpDemoUtil;

import java.io.InputStream;

import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button button;

    private static final String IMAGE_URL =
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493702692204&di=369d437aa1f4cf303ccc7c9c6a25ef03&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fitem%2F201602%2F10%2F20160210215552_eUz32.thumb.700_0.jpeg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.image_get);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, OkHttpActivity.class);
                startActivity(intent);
            }
        });

    }
}
