package com.rss2json.rss2json.impl;

import com.rss2json.rss2json.model.*;
import com.rss2json.rss2json.repo.RssToJsonService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class RssToJsonServiceImpl implements RssToJsonService {


    public String getRssAsXml(String url) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        return forEntity.getBody();
    }

    @Override
    public Rss getFeedByUrl(String url) throws XPathExpressionException {
        String xml = getRssAsXml(url);
        xml = formattingXml(xml);
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        InputSource source = new InputSource(new StringReader(xml));
        Document doc = (Document) xpath.evaluate("/", source, XPathConstants.NODE);


        String feedLang = xpath.evaluate("//feed/language", doc);
        String feedTitle = xpath.evaluate("//feed/title", doc);
        String feedDescription = xpath.evaluate("//feed/description", doc);
        String feedLink = xpath.evaluate("//feed/link", doc);
        String lasBuild = xpath.evaluate("//feed/lastBuildDate", doc);

        String feedImageUrl = xpath.evaluate("//feed/image/url", doc);
        String feedImageTitle = xpath.evaluate("//feed/image/title", doc);
        String feedImageLink = xpath.evaluate("//feed/image/link", doc);
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
            item.setLink(xpath.evaluate(String.format("//items/item[%s]/link", i), doc));
            item.setDescription(xpath.evaluate(String.format("//items/item[%s]/description", i), doc).trim());
            item.setPubDate(xpath.evaluate(String.format("//items/item[%s]/pubDate", i), doc));
            items.add(item);
        }

        Channel channel = new Channel(feed, items);
        return new Rss(channel);

    }

    private String formattingXml(String xml) {
        xml = xml.replace("<![CDATA[", "");
        xml = xml.replace("]]", "");
        xml = xml.replaceFirst("<channel>", "<channel>\n<feed>");
        xml = xml.replaceFirst("<item>", "</feed>\n<items>\n<item>");
        xml = xml.replaceFirst("</channel>", "</items></channel>");

        return xml;
    }

}
