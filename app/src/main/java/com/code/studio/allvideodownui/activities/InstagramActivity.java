package com.code.studio.allvideodownui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.code.studio.allvideodownui.R;
import com.code.studio.allvideodownui.databinding.ActivityInstagramBinding;

public class InstagramActivity extends AppCompatActivity {

    ActivityInstagramBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityInstagramBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgBackPress.setOnClickListener(v -> finish());



    }
}