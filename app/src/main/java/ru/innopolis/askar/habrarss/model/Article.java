package ru.innopolis.askar.habrarss.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 21.07.2017.
 */

public class Article implements Serializable {
    private String title;
    private String guid;
    private String link;
    private String description;
    private Date pubDate;
    private String dcCreator;
    private List<String> categories;

    public Article(String title, String guid, String link, String description, Date pubDate, String dcCreator, List<String> category) {
        this.title = title;
        this.guid = guid;
        this.link = link;
        this.description = description;
        this.pubDate = pubDate;
        this.dcCreator = dcCreator;
        this.categories = category;
    }

    public String getTitle() {
        return title;
    }

    public String getGuid() {
        return guid;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public String getDcCreator() {
        return dcCreator;
    }

    public List<String> getCategories() {
        return categories;
    }
}
