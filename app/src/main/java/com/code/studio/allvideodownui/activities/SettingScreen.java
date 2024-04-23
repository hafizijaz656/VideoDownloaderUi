package com.code.studio.allvideodownui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.code.studio.allvideodownui.R;
import com.code.studio.allvideodownui.databinding.ScreenSettingsBinding;

public class SettingScreen extends AppCompatActivity {

    ScreenSettingsBinding binding;
    Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ScreenSettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity=SettingScreen.this;
        binding.toolbar.setNavigationOnClickListener(v -> finish());

        binding.realtiveHowUse.setOnClickListener(v -> {
            Intent intent=new Intent(activity,ScreenHelpForDownload.class);
            startActivity(intent);
        });

    }
}
