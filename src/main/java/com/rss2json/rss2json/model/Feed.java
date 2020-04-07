package com.rss2json.rss2json.model;

public class Feed {
    private String language;
    private String title;
    private String description;
    private String link;
    private Image image;
    private String lastBuildDate;

    public Feed() {
    }

    public Feed(String language, String title, String description, String link,
                String lastBuildDate) {
        this.language = language;
        this.title = title;
        this.description = description;
        this.link = link;
        this.lastBuildDate = lastBuildDate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getLastBuildDate() {
        return lastBuildDate;
    }

    public void setLastBuildDate(String lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }
}