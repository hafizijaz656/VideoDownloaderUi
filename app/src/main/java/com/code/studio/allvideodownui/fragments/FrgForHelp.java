package com.code.studio.allvideodownui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.code.studio.allvideodownui.R;

public class FrgForHelp extends Fragment {

    private ImageView imgBackImage;
    private int intId = 0;
    private TextView txtSocialTwo;

    private void AssinnDataToFrag() {
        int i = intId;
        if (i == 0) {
            Glide.with(requireActivity())
                    .load(R.drawable.main_img_help_one)
                    .into(this.imgBackImage);
            this.txtSocialTwo.setVisibility(View.VISIBLE);
            this.txtSocialTwo.setText("Search or input video URL");
        } else if (i == 1) {
            Glide.with(requireActivity())
                    .load(R.drawable.main_img_help_two)
                    .into(this.imgBackImage);
            this.txtSocialTwo.setVisibility(View.VISIBLE);
            this.txtSocialTwo.setText("Click the video to play");
        } else if (i == 2) {
            Glide.with(requireActivity())
                    .load(R.drawable.new_fg_three)
                    .into(this.imgBackImage);
            this.txtSocialTwo.setVisibility(View.VISIBLE);
            this.txtSocialTwo.setText("Click the detected button");
        } else if (i == 3) {
            Glide.with(requireActivity())
                    .load(R.drawable.main_img_help_four)
                    .into(this.imgBackImage);
            this.txtSocialTwo.setVisibility(View.GONE);
        }
    }

    private void initView(View view) {
        this.imgBackImage = view.findViewById(R.id.imgBackImage);
        this.txtSocialTwo = view.findViewById(R.id.txtSocialTwo);
    }

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.intId = getArguments() != null ? getArguments().getInt("int_data") : 0;
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return layoutInflater.inflate(R.layout.frag_how_to_main, viewGroup, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        initView(view);
        AssinnDataToFrag();
    }
}
