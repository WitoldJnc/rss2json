package com.rss2json.rss2json.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    private String url;
    private String title;
    private String link;
    private String width;
    private String height;

}
