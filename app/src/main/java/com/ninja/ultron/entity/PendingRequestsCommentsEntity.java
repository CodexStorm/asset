package com.ninja.ultron.entity;

/**
 * Created by Prabhu Sivanandam on 24-May-17.
 */

public class PendingRequestsCommentsEntity {

    int postedBy;
    String text;

    public PendingRequestsCommentsEntity(int postedBy, String text) {
        this.postedBy = postedBy;
        this.text = text;
    }

    public int getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(int postedBy) {
        this.postedBy = postedBy;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
