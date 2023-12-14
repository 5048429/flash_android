package com.google.mlkit.vision.demo.java;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.mlkit.vision.demo.R;

public class MainActivity extends BaseActivity{
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    private ImageView profileImageView;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int savedNightMode = getSavedNightModeState();
        AppCompatDelegate.setDefaultNightMode(savedNightMode);

        getSupportActionBar().hide();

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        profileImageView = findViewById(R.id.Profile_img);

        // 设置主要内容和侧滑栏的开关按钮
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);

        // 设置ImageButton的点击事件，打开侧滑栏
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(navigationView, true);
            }
        });

        MyPagerAdapter pagerAdapter = new MyPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    // 设置标签名称
                    switch (position) {
                        case 0:
                            tab.setText("推荐");
                            break;
                        case 1:
                            tab.setText("直播间");
                            break;
                        case 2:
                            tab.setText("减脂课");
                            break;
                        case 3:
                            tab.setText("会员");
                            break;
                    }
                }
        ).attach();

        LinearLayout diaryNav = findViewById(R.id.diary);
        LinearLayout libraryNav = findViewById(R.id.action);
        LinearLayout profileNav = findViewById(R.id.person);

        ImageView imageView = findViewById(R.id.fab);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击 "日记" 导航项，启动相应的 Activity
                Intent intent = new Intent(MainActivity.this, LivePreviewActivity.class);
                startActivity(intent);
            }
        });

        diaryNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击 "日记" 导航项，启动相应的 Activity
                Intent intent = new Intent(MainActivity.this, Activity_diary.class);
                startActivity(intent);
            }
        });

        libraryNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击 "动作库" 导航项，启动相应的 Activity
                Intent intent = new Intent(MainActivity.this, Activity_action.class);
                startActivity(intent);
            }
        });

        profileNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击 "个人" 导航项，启动相应的 Activity
                Intent intent = new Intent(MainActivity.this, Activity_person.class);
                startActivity(intent);
            }
        });

        // 处理侧滑栏中菜单项的点击事件
        navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_item1) {
                // 处理 Item 1 的点击事件
            } else if (itemId == R.id.nav_item2) {
                // 处理 Item 2 的点击事件
            } else {
                // 处理更多菜单项的点击事件
            }
            // 关闭侧滑栏
            drawerLayout.closeDrawers();
            return true;
        });

    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        // 处理 ActionBar 上的开关按钮点击事件
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // 同步开关按钮的状态
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // 处理屏幕旋转等配置变化时的操作
        drawerToggle.onConfigurationChanged(newConfig);
    }
}
