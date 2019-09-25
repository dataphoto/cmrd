package com.photo.comicsapplication.model;

import java.util.HashMap;
import java.util.Map;

import static com.photo.comicsapplication.Constants.CATEGORIES_DESCRIPTION;
import static com.photo.comicsapplication.Constants.CATEGORIES_LINK_ICON;
import static com.photo.comicsapplication.Constants.CATEGORIES_NAME;

public class CategoriesModel {
    private String name;
    private String linkIcon;
    private String description;

    public String getName() {
        return name;
    }

    public String getLinkIcon() {
        return linkIcon;
    }

    public String getDescription() {
        return description;
    }

    public CategoriesModel(String name, String linkIcon, String description) {
        this.name = name;
        this.linkIcon = linkIcon;
        this.description = description;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> categories = new HashMap<>();

        categories.put(CATEGORIES_NAME, name);
        categories.put(CATEGORIES_DESCRIPTION, description);
        categories.put(CATEGORIES_LINK_ICON, linkIcon);

        return categories;
    }
}
