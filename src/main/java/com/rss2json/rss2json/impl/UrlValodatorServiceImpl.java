package com.rss2json.rss2json.impl;

import com.rss2json.rss2json.repo.UrlValidatorService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class UrlValodatorServiceImpl implements UrlValidatorService {

    @Override
    public boolean isValid(String url) {
        try {
            URL curUrl = new URL(url);
            HttpURLConnection con = (HttpURLConnection) curUrl.openConnection();
            return con.getResponseMessage().equals(HttpStatus.OK.name());
        } catch (Exception e) {
            return false;
        }
    }
}
