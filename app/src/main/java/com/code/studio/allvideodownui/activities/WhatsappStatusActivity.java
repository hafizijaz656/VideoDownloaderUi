package com.code.studio.allvideodownui.activities;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.code.studio.allvideodownui.R;
import com.code.studio.allvideodownui.databinding.ActivityStatusWaWbBinding;
import com.code.studio.allvideodownui.fragments.WaFragment;

import java.util.ArrayList;
import java.util.List;

public class WhatsappStatusActivity extends AppCompatActivity {

    ActivityStatusWaWbBinding binding;
    WaFragment mImagesFragment;
    WaFragment mVideosFragment;

    private void FetchXMLId() {
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitle("Whatsapp Status");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(v -> finish());

        binding.relativeProgress.setVisibility(View.VISIBLE);
    }

    public void setupPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        mImagesFragment = new WaFragment();
        mVideosFragment = new WaFragment();
        viewPagerAdapter.addFragment(mImagesFragment, "Images");
        viewPagerAdapter.addFragment(mVideosFragment, "Videos");
        binding.viewPagerSub.setAdapter(viewPagerAdapter);
        binding.realtiveTab1.callOnClick();
        binding.relativeProgress.setVisibility(View.GONE);
    }

    public void tabSelectionTask(int i) {
        if (i == 0) {
            binding.txtTba1Text.setTextColor(getResources().getColor(R.color.white));
            binding.txtTba2Text.setTextColor(getResources().getColor(R.color.subTextColor));
            binding.realtiveTab1.setBackgroundResource(R.drawable.drawable_tab_select);
            binding.realtiveTab2.setBackgroundResource(R.drawable.drawable_un_select);
        } else if (i == 1) {
            binding.txtTba1Text.setTextColor(getResources().getColor(R.color.subTextColor));
            binding.txtTba2Text.setTextColor(getResources().getColor(R.color.white));
            binding.realtiveTab1.setBackgroundResource(R.drawable.drawable_un_select);
            binding.realtiveTab2.setBackgroundResource(R.drawable.drawable_tab_select);
        }
        binding.viewPagerSub.setCurrentItem(i, true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStatusWaWbBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FetchXMLId();
        setupPager();

        binding.realtiveTab1.setOnClickListener(view -> tabSelectionTask(0));
        binding.realtiveTab2.setOnClickListener(view -> tabSelectionTask(1));

        binding.viewPagerSub.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {
            }

            public void onPageScrolled(int i, float f, int i2) {
            }

            public void onPageSelected(int i) {
                tabSelectionTask(i);
            }
        });
    }

    public static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList();
        private final List<String> mFragmentTitleList = new ArrayList();

        public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, int i) {
            super(fragmentManager, i);
        }

        public void addFragment(Fragment fragment, String str) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(str);
        }

        public int getCount() {
            return mFragmentList.size();
        }

        @NonNull
        public Fragment getItem(int i) {
            return mFragmentList.get(i);
        }

        public int getItemPosition(@NonNull Object obj) {
            return super.getItemPosition(obj);
        }

        public CharSequence getPageTitle(int i) {
            return mFragmentTitleList.get(i);
        }
    }


}
