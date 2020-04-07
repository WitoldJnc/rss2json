package com.rss2json.rss2json.repo;

import com.rss2json.rss2json.model.Rss;
import org.springframework.http.ResponseEntity;

public interface RssToJsonService {

    ResponseEntity<Rss> convertXml(String url);

    ResponseEntity<Rss> getRssAsPojo(String url);

    String formattingXml(String xml);

    boolean isRssFeed(String body);

}
