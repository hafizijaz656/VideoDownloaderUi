package com.code.studio.allvideodownui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.code.studio.allvideodownui.activities.FacebookActivity;
import com.code.studio.allvideodownui.activities.InstagramActivity;
import com.code.studio.allvideodownui.activities.SettingScreen;
import com.code.studio.allvideodownui.activities.WhMainActivity;
import com.code.studio.allvideodownui.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {


    FragmentHomeBinding binding;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(getLayoutInflater());

        binding.iconOverflow.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), SettingScreen.class);
            startActivity(intent);
        });

        binding.realtivrInstaLink.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), InstagramActivity.class);
            startActivity(intent);
        });


        binding.realtivrFbLink.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), FacebookActivity.class);
            startActivity(intent);
        });

        binding.realative4.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), WhMainActivity.class);
            startActivity(intent);
        });

        return binding.getRoot();
    }
}