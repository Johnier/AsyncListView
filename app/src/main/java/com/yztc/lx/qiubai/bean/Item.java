package com.yztc.lx.qiubai.bean;

/**
 * Created by Lx on 2016/8/13.
 */

public class Item {
    private String image;
    private String content;
    private String published_at;
    private String id;
    private User user;

    public Item() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublished_at() {
        return published_at;
    }

    public void setPublished_at(String published_at) {
        this.published_at = published_at;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Item{" +
                "image='" + image + '\'' +
                ", content='" + content + '\'' +
                ", published_at='" + published_at + '\'' +
                ", id='" + id + '\'' +
                ", user=" + user +
                '}';
    }
}
