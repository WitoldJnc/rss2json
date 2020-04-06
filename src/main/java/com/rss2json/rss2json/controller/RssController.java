package com.rss2json.rss2json.controller;

import com.rss2json.rss2json.model.Rss;
import com.rss2json.rss2json.repo.RssToJsonService;
import com.rss2json.rss2json.repo.UrlValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.xpath.XPathExpressionException;

@RestController
@RequestMapping("/api/v1")
public class RssController {

    @Autowired
    private UrlValidatorService validatorService;

    @Autowired
    private RssToJsonService rssToJsonService;

    @GetMapping("/url")
    public ResponseEntity<Rss> geByUrl(@RequestParam("url") String address) throws XPathExpressionException {
        if (validatorService.isValid(address)) {
            return new ResponseEntity<>(rssToJsonService.getFeedByUrl(address), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


}
