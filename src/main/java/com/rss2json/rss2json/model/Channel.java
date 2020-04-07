package com.rss2json.rss2json.model;

import java.util.List;

public class Channel {
    private Feed feed;
    private List<Item> items;

    public Channel() {
    }

    public Channel(Feed feed, List<Item> items) {
        this.feed = feed;
        this.items = items;
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
