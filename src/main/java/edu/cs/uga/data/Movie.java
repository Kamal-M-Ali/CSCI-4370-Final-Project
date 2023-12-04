package edu.cs.uga.data;

import java.math.BigInteger;
import java.sql.Date;

/**
 * A Java POJO object representing a movie entity.
 */
public class Movie extends Media {
    private int movie_id;
    private String homepage;
    private long budget;
    private String production;
    private int runtime;
    private Date release_date;

    public Movie() {
        super();
        this.movie_id = -1;
        this.homepage = null;
        this.budget = 0;
        this.production = null;
        this.runtime = 0;
        this.release_date = null;
    }

    public Movie(String title, Double score, String summary, String genres, int review_count, String homepage, long budget, String production, int runtime, Date release_date) {
        super(title, score, summary, genres, review_count);
        this.movie_id = -1;
        this.homepage = homepage;
        this.budget = budget;
        this.production = production;
        this.runtime = runtime;
        this.release_date = release_date;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public long getBudget() {
        return budget;
    }

    public void setBudget(long budget) {
        this.budget = budget;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    @Override
    public String toString() {
        return super.toString() + " Movie{" +
                "movie_id=" + movie_id +
                ", homepage='" + homepage + '\'' +
                ", budget=" + budget +
                ", production='" + production + '\'' +
                ", runtime=" + runtime +
                ", release_date='" + release_date + '\'' +
                '}';
    }
}
