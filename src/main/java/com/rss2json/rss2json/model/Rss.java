package com.rss2json.rss2json.model;

import java.util.Objects;

public class Rss {
    private Channel channel;

    public Rss() {
    }

    public Rss(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rss rss = (Rss) o;
        return Objects.equals(channel, rss.channel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(channel);
    }
}
