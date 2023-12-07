package edu.cs.uga.data;

import java.util.Date;

/**
 * A Java POJO class representing a review entity.
 */
public class Review {
    private int review_id;
    private String body;
    private double score;
    private int media_id;
    private int user_id;
    private Date created;
    private String profile_name;

    public Review() {
        this.review_id = -1;
        this.body = null;
        this.score = -1.0;
        this.media_id = -1;
        this.user_id = -1;
        this.created = null;
        this.profile_name = null;
    }

    public Review(String body, double score, int media_id, int user_id) {
        this.review_id = -1;
        this.body = body;
        this.score = score;
        this.media_id = media_id;
        this.user_id = user_id;
        this.created = new Date();
        this.profile_name = null;
    }

    public Review(String body, double score, int media_id, int user_id, Date created, String profile_name) {
        this.review_id = -1;
        this.body = body;
        this.score = score;
        this.media_id = media_id;
        this.user_id = user_id;
        this.created = created;
        this.profile_name = profile_name;
    }

    public int getReview_id() {
        return review_id;
    }

    public void setReview_id(int review_id) {
        this.review_id = review_id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
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
        return "Review{" +
                "review_id=" + review_id +
                ", body='" + body + '\'' +
                ", score=" + score +
                ", media_id=" + media_id +
                ", user_id=" + user_id +
                ", created=" + created +
                ", profile_name='" + profile_name + '\'' +
                '}';
    }
}
