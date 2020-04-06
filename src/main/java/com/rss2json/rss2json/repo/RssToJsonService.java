package com.rss2json.rss2json.repo;

import com.rss2json.rss2json.model.Rss;

import javax.xml.xpath.XPathExpressionException;

public interface RssToJsonService {

    Rss xmlToJson(String xml) throws XPathExpressionException;
}
