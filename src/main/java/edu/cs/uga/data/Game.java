package edu.cs.uga.data;

import java.sql.Date;

/**
 * A Java POJO object representing a game entity.
 */
public class Game extends Media {
    private int game_id;
    private Date release_date;
    private String developers;
    private String platforms;
    private int plays;

    public Game() {
        super();
        this.game_id = -1;
        this.release_date = null;
        this.developers = null;
        this.platforms = null;
        this.plays = 0;
    }

    public Game(String title, Double score, String summary, String genres, int review_count, Date release_date, String developers, String platforms, int plays) {
        super(title, score, summary, genres, review_count);
        this.game_id = -1;
        this.release_date = release_date;
        this.developers = developers;
        this.platforms = platforms;
        this.plays = plays;
    }

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    public String getDevelopers() {
        return developers;
    }

    public void setDevelopers(String developers) {
        this.developers = developers;
    }

    public String getPlatforms() {
        return platforms;
    }

    public void setPlatforms(String platforms) {
        this.platforms = platforms;
    }

    public int getPlays() {
        return plays;
    }

    public void setPlays(int plays) {
        this.plays = plays;
    }

    @Override
    public String toString() {
        return super.toString() + " Game{" +
                "game_id=" + game_id +
                ", release_date=" + release_date +
                ", developers='" + developers + '\'' +
                ", platforms='" + platforms + '\'' +
                ", plays=" + plays +
                '}';
    }
}
