
package com.google.mlkit.vision.demo.java;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.mlkit.vision.demo.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.ai.ChatMessageListener;

public class ChatDialogFragment extends DialogFragment {

    RecyclerView recyclerView;
    EditText messageEditText;
    Button sendButton;

    List<Message> messageList;
    MessageAdapter messageAdapter;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // 创建对话框
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setContentView(R.layout.fragment_chat); // 设置对话框的布局

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; // Optional: 设置窗口动画

        // 设置宽度和高度（这里的宽度和高度可以根据需要进行调整）
        layoutParams.width = getResources().getDimensionPixelSize(R.dimen.dialog_width);
        layoutParams.height = getResources().getDimensionPixelSize(R.dimen.dialog_height);

        // 将LayoutParams设置给对话框窗口
        dialog.getWindow().setAttributes(layoutParams);

        // 初始化对话框内的组件
        recyclerView = dialog.findViewById(R.id.msg_recycle_view);
        messageEditText = dialog.findViewById(R.id.message_edit);
        sendButton = dialog.findViewById(R.id.send_bt);

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理发送按钮点击事件
                String question = messageEditText.getText().toString().trim();

                if (question.equals(""))
                    return;

                addToChat(question, Message.SEND_BY_ME);
                messageEditText.setText("");

                BmobApp.bmobAI.Chat(question, "mysession", new ChatMessageListener() {
                    @Override
                    public void onMessage(String s) {
                        // 在ChatMessageListener中的onFinish回调中添加对话框中消息的处理
                        addToChat(s, Message.SEND_BY_BOT);
                    }

                    @Override
                    public void onFinish(String s) {
                        // 这个方法是在对话完成后调用的，你可以在这里添加对话框中消息的处理
                    }

                    @Override
                    public void onError(String s) {
                        // 处理错误情况
                    }

                    @Override
                    public void onClose() {
                        // 处理对话关闭情况
                    }
                });
            }
        });

        return dialog;
    }

    private void addToChat(String message, String sendBy) {
        // 添加消息到对话框
        messageList.add(new Message(message, sendBy));
        messageAdapter.notifyDataSetChanged();
        recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
    }
}