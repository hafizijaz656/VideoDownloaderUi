package com.code.studio.allvideodownui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 2000);

    }

    private void setTranslucentStatus() {
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        final Window win = getWindow();
        final WindowManager.LayoutParams winParams = win.getAttributes();
        winParams.flags |= bits;
        win.setAttributes(winParams);
    }
}