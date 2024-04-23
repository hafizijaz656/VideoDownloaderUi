package com.code.studio.allvideodownui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.code.studio.allvideodownui.R;
import com.code.studio.allvideodownui.databinding.VidLockActivityBinding;

public class VideoLockActivity extends AppCompatActivity {

    VidLockActivityBinding binding;
    Activity activity;

    private String actType = "";
    private int changeCount = 0;
    private int lockType = -1;
    private String strCurrentKey = "";
    private TextView txtForgetPin;
    private TextView txtHeader;
    private TextView txtPinGuide;
    private int wrongCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = VidLockActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = VideoLockActivity.this;

        //binding.imgBackPress.setOnClickListener(v -> finish());

        init();
        strCurrentKey = "";
        if (strCurrentKey.isEmpty()) {
            lockType = 0;
            txtHeader.setText("Set PIN");
            txtPinGuide.setText("Set your PIN");
        } else if (actType.equalsIgnoreCase("open_it")) {
            lockType = 1;
            txtHeader.setText("Private Files");
            txtPinGuide.setText("Enter your PIN");
        } else {
            lockType = 2;
            txtHeader.setText("Change PIN");
            txtPinGuide.setText("Enter Current PIN");
        }

        binding.circleField.setOnTextCompleteListener(str -> {
            PinProccess(str);
            return true;
        });

        txtForgetPin.setOnClickListener(this::ResetPin);


    }


    private void digiClick(int i) {
        if (i == -1) {
            String obj = binding.circleField.getText().toString();
            if (obj.length() > 0) {
                binding.circleField.setText(obj.substring(0, obj.length() - 1));
                return;
            }
            return;
        }
        binding.circleField.append(String.valueOf(i));
    }

    private void init() {
        txtHeader = findViewById(R.id.txtHeader);
        txtPinGuide = findViewById(R.id.txtPinGuide);
        txtForgetPin = findViewById(R.id.txtForgetPin);
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(v -> finish());

        binding.digipad.digipad0.setOnClickListener(this::digiZero);
        binding.digipad.digipad1.setOnClickListener(this::digiOne);
        binding.digipad.digipad2.setOnClickListener(this::digiTwo);
        binding.digipad.digipad3.setOnClickListener(this::digiThree);
        binding.digipad.digipad4.setOnClickListener(this::digiFour);
        binding.digipad.digipad5.setOnClickListener(this::digiFive);
        binding.digipad.digipad6.setOnClickListener(this::digiSix);
        binding.digipad.digipad7.setOnClickListener(this::digiSeven);
        binding.digipad.digipad8.setOnClickListener(this::digiEight);
        binding.digipad.digipad9.setOnClickListener(this::digiNine);
        binding.digipad.digipadLeft.setOnClickListener(this::digiRemove);

    }

    private void PinProccess(String str) {
        int i = lockType;
        if (i == 0) {
//            prefs.edit().putString(ConstantForApp.PRIVATE_LOCK_KEY, str).apply();
//            Intent intent = new Intent(this, ClassDownloadedVideos.class);
//            intent.putExtra("from_", "private");
//            startActivity(intent);
//            finish();
//            overridePendingTransition(0, 0);
        } else if (i == 1) {
            if (strCurrentKey.equals(str)) {
//                Intent intent2 = new Intent(this, ClassDownloadedVideos.class);
//                intent2.putExtra("from_", "private");
//                startActivity(intent2);
//                finish();
//                overridePendingTransition(0, 0);
            } else {
                binding.circleField.setText("");
                binding.circleField.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
                int i2 = wrongCount + 1;
                wrongCount = i2;
                if (i2 > 1) {
                    txtForgetPin.setVisibility(View.VISIBLE);
                } else {
                    txtForgetPin.setVisibility(View.GONE);
                }
            }
        } else if (changeCount != 0) {
//            prefs.edit().putString(ConstantForApp.PRIVATE_LOCK_KEY, str).apply();
//            startActivity(new Intent(this, ClassDownloadedVideos.class));
//            finish();
//            overridePendingTransition(0, 0);
        } else if (strCurrentKey.equals(str)) {
            changeCount++;
            txtHeader.setText("Change PIN");
            txtPinGuide.setText("Enter New PIN");
            binding.circleField.setText("");
        } else {
            binding.circleField.setText("");
            binding.circleField.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
        }
    }


    public void ResetPin(View view) {
        try {
            Toast.makeText(activity, "Reset Screen", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(this, ResetPINActivity.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void digiRemove(View view) {
        digiClick(-1);
    }

    public void digiZero(View view) {
        digiClick(0);
    }

    public void digiOne(View view) {
        digiClick(1);
    }

    public void digiTwo(View view) {
        digiClick(2);
    }

    public void digiThree(View view) {
        digiClick(3);
    }

    public void digiFour(View view) {
        digiClick(4);
    }

    public void digiFive(View view) {
        digiClick(5);
    }

    public void digiSix(View view) {
        digiClick(6);
    }

    public void digiSeven(View view) {
        digiClick(7);
    }

    public void digiEight(View view) {
        digiClick(8);
    }

    public void digiNine(View view) {
        digiClick(9);
    }

    public void onBackPressed() {
        finish();
    }
}
