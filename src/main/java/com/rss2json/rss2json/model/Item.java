package com.rss2json.rss2json.model;

import java.util.List;

public class Item {
    private String guid;
    private String title;
    private String link;
    private String description;
    private String pubDate;
    private List<String> enclosure;
    private List<String> category;

    public Item() {
    }

    public Item(String guid, String title, String link, String description, String pubDate, List<String> enclosure, List<String> category) {
        this.guid = guid;
        this.title = title;
        this.link = link;
        this.description = description;
        this.pubDate = pubDate;
        this.enclosure = enclosure;
        this.category = category;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public List<String> getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(List<String> enclosure) {
        this.enclosure = enclosure;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }
}
