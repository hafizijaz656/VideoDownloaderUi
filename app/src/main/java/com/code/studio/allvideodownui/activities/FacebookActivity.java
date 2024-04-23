package com.code.studio.allvideodownui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.code.studio.allvideodownui.R;
import com.code.studio.allvideodownui.databinding.ActivityFacebookBinding;

public class FacebookActivity extends AppCompatActivity {

    ActivityFacebookBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFacebookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgBackPress.setOnClickListener(v -> finish());
    }
}