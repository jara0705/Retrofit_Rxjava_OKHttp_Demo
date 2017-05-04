package com.jara.retrofit_rxjava_okhttp_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jara.retrofit_rxjava_okhttp_demo.okhttp.ICallBack;
import com.jara.retrofit_rxjava_okhttp_demo.rxjava.RxJavaDemo;

import okhttp3.Response;
import okhttp3.ResponseBody;

public class RxJava2Activity extends AppCompatActivity {

    private Button button;
    private static final String URL = "http://www.baidu.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java2);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxJavaDemo.get(URL, new ICallBack() {
                    @Override
                    public void onError(Exception e) {
                        Log.i("RxJava2Activity", "网络连接异常");
                    }

                    @Override
                    public void onResponse(Response response) {
                        Log.i("RxJava2Activity", "网络连接正常");
                        ResponseBody resposeBody = response.body();
                        Log.i("RxJava2Activity", "responseBody--->" + resposeBody.toString());
                    }
                });
            }
        });
    }
}
