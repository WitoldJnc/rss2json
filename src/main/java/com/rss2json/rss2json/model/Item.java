package com.rss2json.rss2json.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private String guid;
    private String title;
    private String link;
    private String description;
    private String pubDate;
    private List<String> enclosure;
    private List<String> category;

}
