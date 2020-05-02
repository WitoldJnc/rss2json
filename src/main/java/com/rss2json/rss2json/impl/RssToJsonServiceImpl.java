package com.rss2json.rss2json.impl;

import com.rss2json.rss2json.model.*;
import com.rss2json.rss2json.repo.RssToJsonService;
import com.rss2json.rss2json.repo.UrlValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

@Service
public class RssToJsonServiceImpl implements RssToJsonService {

    @Autowired
    private UrlValidatorService validatorService;

    @Override
    public ResponseEntity<Rss> getRssAsPojo(String url) {
        if (validatorService.isValid(url)) {
            try {
                URL website = new URL(url);
                URLConnection connection = website.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null)
                    response.append(inputLine);
                in.close();

                if (isRssFeed(response.toString())) {
                    return convertXml(response.toString());
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Rss> convertXml(String inputXml) {
        String xml = inputXml;
        xml = formattingXml(xml);
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        InputSource source = new InputSource(new StringReader(xml));
        Document doc = null;
        try {
            doc = (Document) xpath.evaluate("/", source, XPathConstants.NODE);


            String feedLang = xpath.evaluate("//feed/language", doc);
            String feedTitle = xpath.evaluate("//feed/title", doc);
            String feedDescription = xpath.evaluate("//feed/description", doc);
            String feedLink = xpath.evaluate("//feed/linkurl", doc);
            String lasBuild = xpath.evaluate("//feed/lastBuildDate", doc);

            String feedImageUrl = xpath.evaluate("//feed/image/url", doc);
            String feedImageTitle = xpath.evaluate("//feed/image/title", doc);
            String feedImageLink = xpath.evaluate("//feed/image/linkUrl", doc);
            String feedImageWidth = xpath.evaluate("//feed/image/width", doc);
            String feedImageHeight = xpath.evaluate("//feed/image/height", doc);

            Feed feed = new Feed(feedLang,
                    feedTitle,
                    feedDescription,
                    feedLink,
                    lasBuild);

            Image image = new Image(feedImageUrl,
                    feedImageTitle,
                    feedImageLink,
                    feedImageWidth,
                    feedImageHeight);

            feed.setImage(image);

            List<Item> items = new ArrayList<>();
            int countOfPosts = Integer.parseInt(xpath.evaluate("count(//items/item) +1", doc));

            for (int i = 1; i < countOfPosts; i++) {
                List<String> enclosure = new ArrayList<>();

                int enclosureCount = Integer.parseInt(xpath.evaluate(String.format("count(//items/item[%s]/enclosure/@url)", i), doc));
                if (enclosureCount > 1) {
                    for (int j = 1; j < enclosureCount + 1; j++) {
                        enclosure.add(xpath.evaluate(String.format("//items/item[%s]/enclosure[%s]/@url", i, j), doc));
                    }
                } else {
                    enclosure.add(xpath.evaluate(String.format("//items/item[%s]/enclosure/@url", i), doc));
                }

                List<String> categories = new ArrayList<>();
                int categoryCount = Integer.parseInt(xpath.evaluate(String.format("count(//items/item[%s]/category)", i), doc));
                if (categoryCount > 1) {
                    for (int j = 1; j < categoryCount + 1; j++) {
                        categories.add(xpath.evaluate(String.format("//items/item[%s]/category[%s]", i, j), doc));
                    }
                } else {
                    categories.add(xpath.evaluate(String.format("//items/item[%s]/category", i), doc));
                }


                Item item = new Item();
                item.setEnclosure(enclosure);
                item.setCategory(categories);

                item.setGuid(xpath.evaluate(String.format("//items/item[%s]/guid", 1), doc));
                item.setTitle(xpath.evaluate(String.format("//items/item[%s]/title", i), doc));
                item.setLink(xpath.evaluate(String.format("//items/item[%s]/linkurl", i), doc));
                item.setDescription(xpath.evaluate(String.format("//items/item[%s]/description", i), doc).trim());
                item.setPubDate(xpath.evaluate(String.format("//items/item[%s]/pubdate", i), doc));
                items.add(item);
            }
            Channel channel = new Channel(feed, items);
            return new ResponseEntity<>(new Rss(channel), HttpStatus.OK);
        } catch (XPathExpressionException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public String formattingXml(String xml) {
        xml = xml.replaceFirst("<channel>", "<channel>\n<feed>");
        xml = xml.replaceFirst("<item>", "</feed>\n<items>\n<item>");
        xml = xml.replaceFirst("</channel>", "</items></channel>");

        return xml;
    }

    @Override
    public boolean isRssFeed(String body) {
        return body.contains("<channel");
    }

}
