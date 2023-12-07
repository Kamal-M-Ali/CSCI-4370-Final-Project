package edu.cs.uga.data;

import java.util.Date;

/**
 * A Java POJO class representing a comment entity.
 */
public class Comment {
    private int comment_id;
    private String body;
    private int media_id;
    private int user_id;
    private Date created;
    private String profile_name;

    public Comment() {
        this.comment_id = -1;
        this.body = null;
        this.media_id = -1;
        this.user_id = -1;
        this.created = null;
        this.profile_name = null;
    }

    public Comment(String body, int media_id, int user_id) {
        this.comment_id = -1;
        this.body = body;
        this.media_id = media_id;
        this.user_id = user_id;
        this.created = new Date();
        this.profile_name = null;
    }

    public Comment(String body, int media_id, int user_id, Date created, String profile_name) {
        this.comment_id = -1;
        this.body = body;
        this.media_id = media_id;
        this.user_id = user_id;
        this.created = created;
        this.profile_name = profile_name;
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

    public int getMedia_id() {
        return media_id;
    }

    public void setMedia_id(int media_id) {
        this.media_id = media_id;
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

    public String getProfile_name() {
        return profile_name;
    }

    public void setProfile_name(String profile_name) {
        this.profile_name = profile_name;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "comment_id=" + comment_id +
                ", body='" + body + '\'' +
                ", media_id=" + media_id +
                ", user_id=" + user_id +
                ", created=" + created +
                ", profile_name='" + profile_name + '\'' +
                '}';
    }
}
