package com.code.studio.allvideodownui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.code.studio.allvideodownui.databinding.ScreenDownloadedBinding;

public class ScreenDownloaded extends AppCompatActivity {

    ScreenDownloadedBinding binding;
    Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ScreenDownloadedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = ScreenDownloaded.this;

        binding.imgBackPress.setOnClickListener(v -> finish());

        binding.imgPrivate.setOnClickListener(v -> {
            Intent intent = new Intent(activity, VideoLockActivity.class);
            startActivity(intent);
        });
    }
}
