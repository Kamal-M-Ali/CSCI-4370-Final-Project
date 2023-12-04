package edu.cs.uga.data;

import java.sql.Date;

/**
 * A Java POJO object representing a book entity.
 */
public class Book extends Media {
    private int book_id;
    private String author;
    private String series;
    private int num_pages;
    private Date publication_date;
    private String publishers;

    public Book() {
        super();
        this.book_id = -1;
        this.author = null;
        this.series = null;
        this.num_pages = 0;
        this.publication_date = null;
        this.publishers = null;
    }

    public Book(String title, Double score, String summary, String genres, int review_count, String author, String series, int num_pages, Date publication_date, String publishers) {
        super(title, score, summary, genres, review_count);
        this.book_id = -1;
        this.author = author;
        this.series = series;
        this.num_pages = num_pages;
        this.publication_date = publication_date;
        this.publishers = publishers;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public int getNum_pages() {
        return num_pages;
    }

    public void setNum_pages(int num_pages) {
        this.num_pages = num_pages;
    }

    public Date getPublication_date() {
        return publication_date;
    }

    public void setPublication_date(Date publication_date) {
        this.publication_date = publication_date;
    }

    public String getPublishers() {
        return publishers;
    }

    public void setPublishers(String publishers) {
        this.publishers = publishers;
    }

    @Override
    public String toString() {
        return super.toString() + " Book{" +
                "book_id=" + book_id +
                ", author='" + author + '\'' +
                ", series='" + series + '\'' +
                ", num_pages=" + num_pages +
                ", publication_date=" + publication_date +
                ", publishers='" + publishers + '\'' +
                '}';
    }
}
