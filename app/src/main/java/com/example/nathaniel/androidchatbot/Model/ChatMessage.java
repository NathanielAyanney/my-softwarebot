package com.example.nathaniel.androidchatbot.Model;

public class ChatMessage {

    private boolean isBotImage, isMyImage;
    private String content;

    public ChatMessage(boolean isBotImage, boolean isMyImage, String content) {
        this.isBotImage = isBotImage;
        this.isMyImage = isMyImage;
        this.content = content;
    }

    public boolean isBotImage() {
        return isBotImage;
    }

    public void setBotImage(boolean botimage) {
        isBotImage = botimage;
    }

    public boolean isMyImage() {
        return isMyImage;
    }

    public void IsMyImage(boolean myimage) {
        isMyImage = myimage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}