package com.ninja.ultron.entity;

/**
 * Created by Prabhu Sivanandam on 22-May-17.
 */

public class CommentEntity {

    int userId;
    String text;

    public CommentEntity(int userId, String text) {
        this.userId = userId;
        this.text = text;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
