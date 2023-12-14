package com.google.mlkit.vision.demo.java;

/**
 * 聊天内容类
 */
public class Message {
    public static String SEND_BY_ME="me";
    public static String SEND_BY_BOT="bot";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSendby() {
        return sendby;
    }

    public Message(String message, String sendby) {
        this.message = message;
        this.sendby = sendby;
    }

    public void setSendby(String sendby) {
        this.sendby = sendby;
    }

    /**
     * 聊天内容
     */
    String message;
    /**
     * 发送者
     */
    String sendby;

}
