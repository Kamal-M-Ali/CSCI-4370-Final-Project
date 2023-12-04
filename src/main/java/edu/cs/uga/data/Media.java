package edu.cs.uga.data;

public class Media {
    private int media_id;
    private String title;
    private Double score;
    private String summary;
    private String genres;
    private int review_count;

    public Media() {
        this.media_id = -1;
        this.title = null;
        this.score = null;
        this.summary = null;
        this.genres = null;
        this.review_count = 0;
    }

    public Media(String title, Double score, String summary, String genres, int review_count) {
        this.media_id = -1;
        this.title = title;
        this.score = score;
        this.summary = summary;
        this.genres = genres;
        this.review_count = review_count;
    }

    public int getMedia_id() {
        return media_id;
    }

    public void setMedia_id(int media_id) {
        this.media_id = media_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public int getReview_count() {
        return review_count;
    }

    public void setReview_count(int review_count) {
        this.review_count = review_count;
    }

    @Override
    public String toString() {
        return "Media{" +
                "media_id=" + media_id +
                ", title='" + title + '\'' +
                ", score=" + score +
                ", summary='" + summary + '\'' +
                ", genres='" + genres + '\'' +
                ", review_count=" + review_count +
                '}';
    }
}
