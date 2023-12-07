package edu.cs.uga.data;

import java.util.Date;

public class Post {
    private int thread_id;
    private String title;
    private String body;
    private int user_id;
    private Date created;
    private int forum_id;
    private String category;

    public Post() {
        this.thread_id = -1;
        this.title = null;
        this.body = null;
        this.user_id = -1;
        this.created = null;
        this.forum_id = -1;
        this.category = null;
    }

    public Post(String title, String body, int user_id, Date created, int forum_id) {
        this.thread_id = -1;
        this.title = title;
        this.body = body;
        this.user_id = user_id;
        this.created = created;
        this.forum_id = forum_id;
        this.category = null;
    }

    public int getThread_id() {
        return thread_id;
    }

    public void setThread_id(int thread_id) {
        this.thread_id = thread_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getForum_id() {
        return forum_id;
    }

    public void setForum_id(int forum_id) {
        this.forum_id = forum_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Post{" +
                "thread_id=" + thread_id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", user_id=" + user_id +
                ", created=" + created +
                ", forum_id=" + forum_id +
                ", category='" + category + '\'' +
                '}';
    }
}
