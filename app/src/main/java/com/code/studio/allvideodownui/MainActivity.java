package com.code.studio.allvideodownui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.code.studio.allvideodownui.activities.HistoryActivity;
import com.code.studio.allvideodownui.activities.PasteLinkActivity;
import com.code.studio.allvideodownui.activities.ScreenDownloaded;
import com.code.studio.allvideodownui.activities.SettingScreen;
import com.code.studio.allvideodownui.activities.VideoLockActivity;
import com.code.studio.allvideodownui.databinding.ActivityMainBinding;
import com.code.studio.allvideodownui.fragments.HomeFragment;
import com.code.studio.allvideodownui.utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ActivityMainBinding binding;
    Activity activity;
    FragmentTransaction transaction = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = MainActivity.this;
        setFragment(new HomeFragment());

        binding.navView.setNavigationItemSelectedListener(this);
        binding.appBar.navigation.setSelectedItemId(R.id.nav_home);

        binding.appBar.navigation.setOnNavigationItemSelectedListener(
                item -> {
                    int itemId = item.getItemId();
                    if (itemId == R.id.nav_tabs) {
                        binding.drawerLayout.openDrawer(GravityCompat.START);
                        Utils.toast("Tabs", this);
                        return true;
                    } else if (itemId == R.id.nav_new_page) {
                        Intent intent = new Intent(activity, PasteLinkActivity.class);
                        startActivity(intent);
                        return true;
                    } else if (itemId == R.id.nav_home) {
                        Utils.toast("Home", this);
                        setFragment(new HomeFragment());
                        return false;
                    } else if (itemId == R.id.nav_downloads) {
                        Intent intent = new Intent(activity, ScreenDownloaded.class);
                        startActivity(intent);
                        return true;
                    } else if (itemId == R.id.nav_more) {
                        bottomMenu();
                        return true;
                    }
                    return false;
                });


    }

    private void setFragment(Fragment fragment) {
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        return true;
    }


    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            binding.appBar.navigation.setSelectedItemId(R.id.nav_home);
        } else {
            finish();
        }
    }

    private void bottomMenu() {

        final BottomSheetDialog dialogBottom = new BottomSheetDialog(this, R.style.SheetDialog);
        dialogBottom.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBottom.setContentView(R.layout.bottom_screen_for_menus_new);

        RelativeLayout history = dialogBottom.findViewById(R.id.realtive1);
        RelativeLayout privateVid = dialogBottom.findViewById(R.id.realtive4);
        RelativeLayout setting = dialogBottom.findViewById(R.id.realtive6);
        ImageView imgClose = dialogBottom.findViewById(R.id.imgClose);

        dialogBottom.show();

        assert history != null;
        history.setOnClickListener(view -> {
            Intent intent = new Intent(activity, HistoryActivity.class);
            startActivity(intent);
            dialogBottom.dismiss();
        });

        assert privateVid != null;
        privateVid.setOnClickListener(view -> {
            Intent intent = new Intent(activity, VideoLockActivity.class);
            startActivity(intent);
            dialogBottom.dismiss();
        });

        assert setting != null;
        setting.setOnClickListener(view -> {
            Intent intent = new Intent(activity, SettingScreen.class);
            startActivity(intent);
            dialogBottom.dismiss();
        });


        assert imgClose != null;
        imgClose.setOnClickListener(view -> dialogBottom.dismiss());
    }

}


