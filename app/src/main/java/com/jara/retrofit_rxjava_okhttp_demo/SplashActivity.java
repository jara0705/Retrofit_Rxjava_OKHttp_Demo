package com.jara.retrofit_rxjava_okhttp_demo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.CountDownTimer;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jara.retrofit_rxjava_okhttp_demo.bean.Constants;
import com.jara.retrofit_rxjava_okhttp_demo.bean.Splash;
import com.jara.retrofit_rxjava_okhttp_demo.service.SplashDownLoadService;
import com.jara.retrofit_rxjava_okhttp_demo.util.SerializableUtils;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity {

    private Splash splash;
    @BindView(R.id.sp_bg)
    ImageView spBgImage;
    @BindView(R.id.sp_jump_btn)
    Button spJumpBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        showAndDownSplash();
    }

    @OnClick(R.id.sp_jump_btn)
    void onClick() {
        gotoMainActivity();
    }

    private CountDownTimer countDownTimer = new CountDownTimer(3400, 1000) {
        @SuppressLint("SetTextI18n")
        @Override
        public void onTick(long millisUntilFinished) {
            spJumpBtn.setText("跳过(" + millisUntilFinished / 1000 + "s)");
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onFinish() {
            spJumpBtn.setText("跳过(0s)");
            gotoMainActivity();
        }
    };

    private void showAndDownSplash() {
        showSplash();
        startImageDownload();
    }

    private void showSplash() {
        splash = getLocalSplash();
        if (splash != null && !TextUtils.isEmpty(splash.savePath)) {
            Glide.with(this).load(splash.savePath).dontAnimate().into(spBgImage);
            startClock();
            Log.i("SplashActivity","start clock");
        } else {
//            spJumpBtn.setVisibility(View.INVISIBLE);
            Log.i("SplashActivity","no start clock");
//            spJumpBtn.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    gotoMainActivity();
//                }
//            }, 500);
            startClock();
        }
    }

    private void startImageDownload() {
        SplashDownLoadService.startDownLoadSplashImage(this, Constants.DOWNLOAD_SPLASH);
    }

    private Splash getLocalSplash() {
        Splash splash = null;
        try {
            File serializableFile = SerializableUtils.getSerializableFile(Constants.SPLASH_PATH,
                    Constants.SPLASH_FILE_NAME);
            splash = (Splash) SerializableUtils.readObject(serializableFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return splash;
    }

    private void startClock() {
        spJumpBtn.setVisibility(View.VISIBLE);
        countDownTimer.start();
    }

    private void gotoMainActivity() {
        countDownTimer.cancel();
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
