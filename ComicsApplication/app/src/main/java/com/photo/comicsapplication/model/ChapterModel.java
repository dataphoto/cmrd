package com.photo.comicsapplication.model;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static com.photo.comicsapplication.Constants.CHAPTER_DESCRIPTION;
import static com.photo.comicsapplication.Constants.CHAPTER_INDEX;
import static com.photo.comicsapplication.Constants.CHAPTER_LINK_FILE;
import static com.photo.comicsapplication.Constants.CHAPTER_NAME;

public class ChapterModel {
    private String linkFile;
    private String description;
    private String chapterName;
    private String indexChapter;

    public ChapterModel(){

    }

    public ChapterModel(String linkFile, String description, String chapterName, String indexChapter) {
        this.linkFile = linkFile;
        this.description = description;
        this.chapterName = chapterName;
        this.indexChapter = indexChapter;
    }

    public String getLinkFile() {
        return linkFile;
    }

    public void setLinkFile(String linkFile) {
        this.linkFile = linkFile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getIndexChapter() {
        return indexChapter;
    }

    public void setIndexChapter(String indexChapter) {
        this.indexChapter = indexChapter;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> chapter = new HashMap<>();

        chapter.put(CHAPTER_NAME, chapterName);
        chapter.put(CHAPTER_DESCRIPTION, description);
        chapter.put(CHAPTER_LINK_FILE, linkFile);
        chapter.put(CHAPTER_INDEX, indexChapter);

        return chapter;
    }
}
