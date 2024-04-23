package com.code.studio.allvideodownui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.code.studio.allvideodownui.R;
import com.code.studio.allvideodownui.databinding.ActivityPasteLinkBinding;

public class PasteLinkActivity extends AppCompatActivity {

    ActivityPasteLinkBinding binding;
    Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityPasteLinkBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity=PasteLinkActivity.this;
        binding.imgBackPress.setOnClickListener(v -> finish());

        binding.btnDownload.setOnClickListener(v -> {
            Intent intent=new Intent(activity,VidLinksActivity.class);
            startActivity(intent);
        });

    }
}
