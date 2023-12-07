package edu.cs.uga.data;

import java.util.Date;

/**
 * A Java POJO class representing a comment entity.
 */
public class ThreadComment {
    private int comment_id;
    private String body;
    private int thread_id;
    private int user_id;
    private Date created;
    private String extra;

    public ThreadComment() {
        this.comment_id = -1;
        this.body = null;
        this.thread_id = -1;
        this.user_id = -1;
        this.created = null;
        this.extra = null;
    }

    public ThreadComment(String body, int thread_id, int user_id) {
        this.comment_id = -1;
        this.body = body;
        this.thread_id = thread_id;
        this.user_id = user_id;
        this.created = new Date();
        this.extra = null;
    }

    public ThreadComment(String body, int thread_id, int user_id, Date created, String extra) {
        this.comment_id = -1;
        this.body = body;
        this.thread_id = thread_id;
        this.user_id = user_id;
        this.created = created;
        this.extra = extra;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getThread_id() {
        return thread_id;
    }

    public void setThread_id(int thread_id) {
        this.thread_id = thread_id;
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

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "comment_id=" + comment_id +
                ", body='" + body + '\'' +
                ", thread_id=" + thread_id +
                ", user_id=" + user_id +
                ", created=" + created +
                ", profile_name='" + extra + '\'' +
                '}';
    }
}
