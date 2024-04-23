package com.code.studio.allvideodownui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.code.studio.allvideodownui.R;
import com.code.studio.allvideodownui.fragments.FrgForHelp;

import java.util.ArrayList;
import java.util.List;

public class ScreenHelpForDownload extends AppCompatActivity implements View.OnClickListener {

    public ImageView[] dots;
    public int dotscount;
    public Button txtOK;
    public int vpPos = 0;
    private LinearLayout SliderDots;
    private FrgForHelp mFragmentHelp1 = null;
    private FrgForHelp mFragmentHelp2 = null;
    private FrgForHelp mFragmentHelp3 = null;
    private FrgForHelp mFragmentHelp4 = null;
    private ViewPagerAdapter mViewPagerAdapter;
    private TextView txtSkip;
    private ViewPager viewPager;

    private void AssignPageVericalDots() {
        int count = mViewPagerAdapter.getCount();
        dotscount = count;
        dots = new ImageView[count];
        int i = 0;
        while (true) {
            int i2 = dotscount;
            if (i < i2) {
                SliderDots.setWeightSum((float) i2);
                dots[i] = new ImageView(this);
                dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_main_img));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
                layoutParams.setMargins(0, 0, 0, 0);
                layoutParams.weight = 1.0f;
                SliderDots.addView(dots[i], layoutParams);
                i++;
            } else {
                dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_small));
                return;
            }
        }
    }

    private void init() {
        viewPager = findViewById(R.id.viewPager);
        txtOK = findViewById(R.id.txtOK);
        txtSkip = findViewById(R.id.txtSkip);
        SliderDots = findViewById(R.id.SliderDots);
    }

    private void setupViewPager() {
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        if (mFragmentHelp1 == null) {
            mFragmentHelp1 = new FrgForHelp();
            Bundle bundle = new Bundle();
            bundle.putInt("int_data", 0);
            mFragmentHelp1.setArguments(bundle);
        }
        if (mFragmentHelp2 == null) {
            mFragmentHelp2 = new FrgForHelp();
            Bundle bundle2 = new Bundle();
            bundle2.putInt("int_data", 1);
            mFragmentHelp2.setArguments(bundle2);
        }
        if (mFragmentHelp3 == null) {
            mFragmentHelp3 = new FrgForHelp();
            Bundle bundle3 = new Bundle();
            bundle3.putInt("int_data", 2);
            mFragmentHelp3.setArguments(bundle3);
        }
        if (mFragmentHelp4 == null) {
            mFragmentHelp4 = new FrgForHelp();
            Bundle bundle4 = new Bundle();
            bundle4.putInt("int_data", 3);
            mFragmentHelp4.setArguments(bundle4);
        }
        mViewPagerAdapter.addFragment(mFragmentHelp1, "");
        mViewPagerAdapter.addFragment(mFragmentHelp2, "1");
        mViewPagerAdapter.addFragment(mFragmentHelp3, ExifInterface.GPS_MEASUREMENT_2D);
        mViewPagerAdapter.addFragment(mFragmentHelp4, ExifInterface.GPS_MEASUREMENT_3D);
        AssignPageVericalDots();
        viewPager.setCurrentItem(0);
        vpPos = 0;
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(mViewPagerAdapter);
        mViewPagerAdapter.notifyDataSetChanged();
        viewPager.setAdapter(mViewPagerAdapter);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.txtSkip) {
            finish();
        } else if (view.getId() == R.id.txtOK) {
            int i = vpPos;
            if (i == 3) {
                finish();
            } else {
                viewPager.setCurrentItem(i + 1);
            }
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.screen_how_to);
        init();
        setupViewPager();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {
            }

            public void onPageScrolled(int i, float f, int i2) {
            }

            public void onPageSelected(int i) {
                for (int i2 = 0; i2 < dotscount; i2++) {
                    dots[i2].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_main_img));
                }
                dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_small));
                vpPos = i;
                if (i == 3) {
                    txtOK.setText("Got It");
                } else {
                    txtOK.setText("Next");
                }
            }
        });
        txtSkip.setOnClickListener(this);
        txtOK.setOnClickListener(this);
    }

    public static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList();
        private final List<String> mFragmentTitleList = new ArrayList();

        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
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
