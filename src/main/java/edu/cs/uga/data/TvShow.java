package edu.cs.uga.data;

import java.math.BigInteger;
import java.sql.Date;

/**
 * A Java POJO object representing a tv_show entity.
 */
public class TvShow extends Media {
    private int tv_show_id;
    private int num_seasons;
    private int num_episodes;
    private boolean is_adult;
    private Date first_air_date;
    private Date last_air_date;
    private String homepage;
    private String created_by;
    private String networks;

    public TvShow() {
        super();
        this.tv_show_id = -1;
        this.num_seasons = 0;
        this.num_episodes = 0;
        this.is_adult = false;
        this.first_air_date = null;
        this.last_air_date = null;
        this.homepage = null;
        this.created_by = null;
        this.networks = null;
    }

    public TvShow(String title, Double score, String summary, String genres, int review_count, int num_seasons, int num_episodes, boolean is_adult, Date first_air_date, Date last_air_date, String homepage, String created_by, String networks) {
        super(title, score, summary, genres, review_count);
        this.tv_show_id = -1;
        this.num_seasons = num_seasons;
        this.num_episodes = num_episodes;
        this.is_adult = is_adult;
        this.first_air_date = first_air_date;
        this.last_air_date = last_air_date;
        this.homepage = homepage;
        this.created_by = created_by;
        this.networks = networks;
    }

    public int getTv_show_id() {
        return tv_show_id;
    }

    public void setTv_show_id(int tv_show_id) {
        this.tv_show_id = tv_show_id;
    }

    public int getNum_seasons() {
        return num_seasons;
    }

    public void setNum_seasons(int num_seasons) {
        this.num_seasons = num_seasons;
    }

    public int getNum_episodes() {
        return num_episodes;
    }

    public void setNum_episodes(int num_episodes) {
        this.num_episodes = num_episodes;
    }

    public boolean isIs_adult() {
        return is_adult;
    }

    public void setIs_adult(boolean is_adult) {
        this.is_adult = is_adult;
    }

    public Date getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(Date first_air_date) {
        this.first_air_date = first_air_date;
    }

    public Date getLast_air_date() {
        return last_air_date;
    }

    public void setLast_air_date(Date last_air_date) {
        this.last_air_date = last_air_date;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getNetworks() {
        return networks;
    }

    public void setNetworks(String networks) {
        this.networks = networks;
    }

    @Override
    public String toString() {
        return super.toString() + " TvShow{" +
                "tv_show_id=" + tv_show_id +
                ", num_seasons=" + num_seasons +
                ", num_episodes=" + num_episodes +
                ", is_adult=" + is_adult +
                ", first_air_date=" + first_air_date +
                ", last_air_date=" + last_air_date +
                ", homepage='" + homepage + '\'' +
                ", created_by='" + created_by + '\'' +
                ", networks='" + networks + '\'' +
                '}';
    }
}
