package com.rss2json.rss2json.controller;

import com.rss2json.rss2json.model.Rss;
import com.rss2json.rss2json.repo.RssToJsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class RssController {

    @Autowired
    private RssToJsonService rssToJsonService;

    @GetMapping("/url")
    public ResponseEntity<Rss> geByUrl(@RequestParam("url") String address) {
        return rssToJsonService.getRssAsPojo(address);

    }

}
