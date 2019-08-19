package com.udacity.course3.reviews.entity.mongo;

import com.udacity.course3.reviews.entity.Comment;

public class CommentInfo {
    private Integer id;

    private String title;

    private String text;

    public CommentInfo() {}

    public CommentInfo(Comment comment) {
        id = comment.getId();
        title = comment.getTitle();
        text = comment.getText();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
