package Entities;

import java.sql.Date;

public class Blog {

    private int id;
    private String title;
    private String content;
    private String photo;
    private Date createdAt;
    private Category category;
    private int authorId;

    public Blog() {
    }

    public Blog(int id, String title, String content,
                String photo, Date createdAt,
                Category category, int authorId) {

        this.id = id;
        this.title = title;
        this.content = content;
        this.photo = photo;
        this.createdAt = createdAt;
        this.category = category;
        this.authorId = authorId;
    }

    public Blog(int id, String title, String content,
                String photo, Date createdAt,
                Category category) {

        this.id = id;
        this.title = title;
        this.content = content;
        this.photo = photo;
        this.createdAt = createdAt;
        this.category = category;
        this.authorId = 0;
    }

    public Blog(String title, String content,
                String photo, Date createdAt,
                Category category, int authorId) {

        this.title = title;
        this.content = content;
        this.photo = photo;
        this.createdAt = createdAt;
        this.category = category;
        this.authorId = authorId;
    }

    public Blog(String title, String content,
                String photo, Date createdAt,
                Category category) {

        this.title = title;
        this.content = content;
        this.photo = photo;
        this.createdAt = createdAt;
        this.category = category;
        this.authorId = 0;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getPhoto() {
        return photo;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Category getCategory() {
        return category;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
}