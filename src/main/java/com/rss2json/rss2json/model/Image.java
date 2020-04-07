package com.rss2json.rss2json.model;

public class Image {
    private String url;
    private String title;
    private String link;
    private String width;
    private String height;

    public Image() {
    }

    public Image(String url, String title, String link, String width, String height) {
        this.url = url;
        this.title = title;
        this.link = link;
        this.width = width;
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
