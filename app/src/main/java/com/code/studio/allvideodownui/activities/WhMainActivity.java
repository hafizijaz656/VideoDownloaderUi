package com.code.studio.allvideodownui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.code.studio.allvideodownui.databinding.ActivityWhatsStatusSaverBinding;

public class WhMainActivity extends AppCompatActivity {

    ActivityWhatsStatusSaverBinding binding;
    Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWhatsStatusSaverBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity= WhMainActivity.this;
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(v -> finish());

        binding.realative1.setOnClickListener(v -> {
            Intent intent=new Intent(activity,WhatsappStatusActivity.class);
            startActivity(intent);
        });

        binding.realative2.setOnClickListener(v -> {
            Intent intent=new Intent(activity, MyWhStatusActivity.class);
            startActivity(intent);
        });


    }
}
