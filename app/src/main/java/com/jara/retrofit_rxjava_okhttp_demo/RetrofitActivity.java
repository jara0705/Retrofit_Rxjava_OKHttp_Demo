package com.jara.retrofit_rxjava_okhttp_demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jara.retrofit_rxjava_okhttp_demo.retrofit.RetrofitDemo;

/**
 *
 * Created by jara on 2017-5-18.
 */

public class RetrofitActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitDemo.getIp("59.108.54.37");
            }
        });
    }
}
