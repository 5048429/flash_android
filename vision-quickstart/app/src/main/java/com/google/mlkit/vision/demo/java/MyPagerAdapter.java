package com.google.mlkit.vision.demo.java;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyPagerAdapter extends FragmentStateAdapter {
    public MyPagerAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @Override
    public Fragment createFragment(int position) {
        // 返回与选项卡相关的片段
        switch (position) {
            case 0:
                return new Fragment1();
            case 1:
                return new Fragment2();
            case 2:
                return new Fragment3();
            case 3:
                return new Fragment4();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 4; // 你有多少个选项卡
    }
}
