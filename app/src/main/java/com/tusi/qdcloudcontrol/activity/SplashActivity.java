package com.tusi.qdcloudcontrol.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import java.util.concurrent.TimeUnit;
//启动界面
public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
// CountDownTimer（a,b) a倒计时，b每次间隔的时间
    private CountDownTimer mCountDown = new CountDownTimer(TimeUnit.SECONDS.toMillis(3), TimeUnit.SECONDS.toMillis(1)) {
        @Override
        public void onTick(long millisUntilFinished) {
            Log.d(TAG, "onTick() called with: millisUntilFinished = [" + millisUntilFinished + "]");//输出蓝色debug
        }

        @Override
        public void onFinish() {
//            toHome();
            navigateToHome(SplashActivity.this);
            finish();
        }
    };

    public void navigateToHome(Context context) {
        final Intent intentToHome = HomeActivity.getCallingIntent(context);
        context.startActivity(intentToHome);
    }

//    @Override
    protected void setupStatusBar() {

    }

//    private void toHome() {
//        mNavigator.navigateToHome(this);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startCountdown();
    }

    private void startCountdown() {
        mCountDown.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCountDown.cancel();
        mCountDown.onFinish();
    }
}
