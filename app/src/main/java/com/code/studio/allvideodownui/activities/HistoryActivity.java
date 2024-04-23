package com.code.studio.allvideodownui.activities;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.code.studio.allvideodownui.databinding.HistoryActivityBinding;

public class HistoryActivity extends AppCompatActivity {

    HistoryActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = HistoryActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgBackPress.setOnClickListener(v -> finish());
    }
}
