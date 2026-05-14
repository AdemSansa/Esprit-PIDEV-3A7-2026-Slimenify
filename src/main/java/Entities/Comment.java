package Entities;

import java.sql.Date;

public class Comment {

    private int id;

    private String content;

    private Date createdAt;

    private int rating;

    private Blog blog;

    // CONSTRUCTORS

    public Comment() {
    }

    public Comment(String content,
                   Date createdAt,
                   int rating,
                   Blog blog) {

        this.content = content;
        this.createdAt = createdAt;
        this.rating = rating;
        this.blog = blog;
    }

    public Comment(int id,
                   String content,
                   Date createdAt,
                   int rating,
                   Blog blog) {

        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.rating = rating;
        this.blog = blog;
    }

    // GETTERS & SETTERS

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    @Override
    public String toString() {

        return content;
    }
}