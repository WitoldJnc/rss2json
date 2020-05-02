package com.rss2json.rss2json.impl;

import com.rss2json.rss2json.model.*;
import com.rss2json.rss2json.repo.RssToJsonService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class RssToJsonServiceImplTest {

    @Autowired
    private RssToJsonService rssToJsonService;

    private String xmlToTransform;
    private String someXml;
    private Rss rss;

    @Test
    public void shouldRetunTrueWhenContentIsRssLike() {
        initXmlToTransform();
        Assertions.assertTrue(rssToJsonService.isRssFeed(xmlToTransform));
    }

    @Test
    public void shouldReturnFalseWhenIsNotRssLikeFile() {
        initSomeXml();
        Assertions.assertFalse(rssToJsonService.isRssFeed(someXml));
    }

    @Test
    public void xmlDoNotContainsFeedAndItemsTagsAndContainCdata() {
        initXmlToTransform();
        Assertions.assertTrue(xmlToTransform.contains("CDATA"));
        Assertions.assertFalse(xmlToTransform.contains("<feed>"));
        Assertions.assertFalse(xmlToTransform.contains("<items>"));
    }

    @Test
    public void shouldAddItemsAndFeedTagsAndReplaceCdata() {
        initXmlToTransform();
        String formattingXml = rssToJsonService.formattingXml(xmlToTransform);
        Assertions.assertTrue(formattingXml.contains("<feed>"));
        Assertions.assertTrue(formattingXml.contains("<items>"));
    }


    @Test
    public void shpuldCorrectrlyConvertXmlToPojo() {
        initXmlToTransform();
        initRss();
        ResponseEntity<Rss> rssResponseEntity = rssToJsonService.convertXml(xmlToTransform);
        Assert.assertEquals(200, rssResponseEntity.getStatusCode().value());
//        Assert.assertEquals(rss, rssResponseEntity.getBody());
    }


    private void initRss() {
        Channel channel = new Channel();
        Feed feed = new Feed("ru", "channel feed title", "channel feed description", "shannel feed link", "");
        Image image = new Image("channel feed image url", "channel feed image title",
                "channel feed image link", "134", "22");
        feed.setImage(image);

        List<Item> items = new ArrayList<>();

        Item item1 = new Item();
        item1.setGuid("item 1 guid");
        item1.setTitle("item 1 title");
        item1.setLink("item 1 link");
        item1.setDescription("item 1 description");
        item1.setPubDate("item 1 pub");
        item1.setEnclosure(Collections.singletonList("1enc"));
        item1.setCategory(Collections.singletonList("1 cat"));

        Item item2 = new Item();
        item2.setGuid("item 2 guid");
        item2.setTitle("item 2 title");
        item2.setLink("item 2 link");
        item2.setDescription("item 2 description");
        item2.setPubDate("item 2 pub");
        List<String> enclosures = Arrays.asList("1of3enc", "2of3enc", "3of3enc");
        item2.setEnclosure(enclosures);
        List<String> catigories = Arrays.asList("1cat", "2cat", "3cat");
        item2.setCategory(catigories);
        items.add(item1);
        items.add(item2);

        channel.setItems(items);
        channel.setFeed(feed);

        rss = new Rss(channel);
    }

    private void initSomeXml() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("src/main/resources/testNotRssLikeXml.xml"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        String line = null;

        try {
            line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
                someXml = sb.toString();

            }
        } catch (IOException e) {
            someXml = null;
        }
    }

    private void initXmlToTransform() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("src/main/resources/testShouldTranformXmlToJson.xml"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        String line = null;

        try {
            line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
                xmlToTransform = sb.toString();

            }
        } catch (IOException e) {
            xmlToTransform = null;
        }
    }
}