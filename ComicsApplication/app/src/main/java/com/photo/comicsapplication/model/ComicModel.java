package com.photo.comicsapplication.model;

import java.util.HashMap;
import java.util.Map;

import static com.photo.comicsapplication.Constants.COMIC_AUTHOR;
import static com.photo.comicsapplication.Constants.COMIC_CATEGORIES;
import static com.photo.comicsapplication.Constants.COMIC_DESCRIPTION;
import static com.photo.comicsapplication.Constants.COMIC_ID;
import static com.photo.comicsapplication.Constants.COMIC_LINK_IMAGE;
import static com.photo.comicsapplication.Constants.COMIC_NAME;
import static com.photo.comicsapplication.Constants.COMIC_REVIEW;
import static com.photo.comicsapplication.Constants.COMIC_STATUS;
import static com.photo.comicsapplication.Constants.COMIC_TOTAL;

public class ComicModel {
    private String comicId;
    private String comicName;
    private String categories;
    private String description;
    private String linkImage;
    private String total;
    private String author;
    private String status;
    private String review;

    public ComicModel(){}

    public ComicModel(String id, String comicName, String categories, String description, String linkImage, String total, String author, String status, String review) {
        this.comicId = id;
        this.comicName = comicName;
        this.categories = categories;
        this.description = description;
        this.linkImage = linkImage;
        this.total = total;
        this.author = author;
        this.status = status;
        this.review = review;
    }

    public String getComicId() {
        return comicId;
    }

    public void setComicId(String comicId) {
        this.comicId = comicId;
    }

    public String getComicName() {
        return comicName;
    }

    public void setComicName(String comicName) {
        this.comicName = comicName;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> categoriesMap = new HashMap<>();

        categoriesMap.put(COMIC_ID, comicId);
        categoriesMap.put(COMIC_NAME, comicName);
        categoriesMap.put(COMIC_DESCRIPTION, description);
        categoriesMap.put(COMIC_CATEGORIES, categories);
        categoriesMap.put(COMIC_LINK_IMAGE, linkImage);
        categoriesMap.put(COMIC_TOTAL, total);
        categoriesMap.put(COMIC_AUTHOR, author);
        categoriesMap.put(COMIC_STATUS, status);
        categoriesMap.put(COMIC_REVIEW, review);

        return categoriesMap;
    }
}
