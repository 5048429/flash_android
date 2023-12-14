package com.google.mlkit.vision.demo.java;


import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.mlkit.vision.demo.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class Fragment1 extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private TextView chatTextView;
    private String originalText;
    private ViewPager2 viewPager2;
    private static class MyHandler extends Handler {
        private final WeakReference<Fragment1> fragmentRef;

        MyHandler(Fragment1 fragment) {
            fragmentRef = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Fragment1 fragment = fragmentRef.get();
            if (fragment != null) {
                fragment.handleMessage(msg);
            }
        }
    }

    private MyHandler handler;

    public Fragment1() {
        // Required empty public constructor
    }

    public static Fragment1 newInstance(String param1, String param2) {
        Fragment1 fragment = new Fragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, container, false);

        viewPager2 = view.findViewById(R.id.viewPager2);

        // 创建照片数据列表
        List<Integer> photoList = new ArrayList<>();
        photoList.add(R.drawable.image1_placeholder);
        photoList.add(R.drawable.image2_placeholder);
        photoList.add(R.drawable.image3_placeholder);
        // 添加更多照片...

        // 创建并设置适配器
        PhotoAdapter photoAdapter = new PhotoAdapter(photoList,requireContext());
        viewPager2.setAdapter(photoAdapter);

        // 设置自动翻页
        startAutoCycle();

        CardView cardView = view.findViewById(R.id.imgCard1);

        // 为CardView设置点击监听器
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建并显示对话框式的ChatDialogFragment
                ChatDialogFragment dialogFragment = new ChatDialogFragment();
                dialogFragment.show(getFragmentManager(), "chat_dialog");
            }
        });

        // Find TextView
        chatTextView = view.findViewById(R.id.chattextview);

        // Get the originally set text
        originalText = chatTextView.getText().toString();

        // Clear the text in TextView for gradual display
        chatTextView.setText("");

        // Set up the handler
        handler = new MyHandler(this);

        // Set up the animation
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Set up the animation
                fadeInText(originalText);
            }
        }, 1000);
        return view;
    }

    private void startAutoCycle() {
        final int delay = 3000; // 设置延迟时间，单位为毫秒
        final int duration = 500; // 设置动画持续时间，单位为毫秒

        final Handler autoCycleHandler = new Handler();
        autoCycleHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int currentItem = viewPager2.getCurrentItem();
                int totalItems = viewPager2.getAdapter().getItemCount();

                // 如果当前项是最后一项，则切换到第一项，否则切换到下一项
                viewPager2.setCurrentItem((currentItem + 1) % totalItems);

                // 递归调用，实现循环
                autoCycleHandler.postDelayed(this, delay);
            }
        }, delay);
    }

    private void fadeInText(final String text) {
        final int length = text.length();
        final int durationPerCharacter = 120; // Set the time for each character to be displayed, in milliseconds

        for (int i = 0; i < length; i++) {
            final int index = i;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Add text character by character
                    chatTextView.append(String.valueOf(text.charAt(index)));

                    // If it's the last character, you can perform any additional actions if needed

                    // For example, you can trigger some other functionality or UI updates
                    if (index == length - 1) {
                        // Do something if needed
                    }
                }
            }, i * durationPerCharacter);
        }
    }

    private void startFadeInAnimation(int totalDuration) {
        // Set up the animation
        ValueAnimator fadeIn = ValueAnimator.ofFloat(0f, 1f);
        fadeIn.setDuration(totalDuration); // Total duration of the fade-in animation

        fadeIn.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float alpha = (float) valueAnimator.getAnimatedValue();
                chatTextView.setAlpha(alpha);
            }
        });

        fadeIn.start();
    }

    private void handleMessage(Message msg) {
        // No need for handling messages in this scenario
    }
}
