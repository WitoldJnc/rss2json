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
import java.util.Arrays;
import java.util.List;

@Service
public class RssToJsonServiceImpl implements RssToJsonService {

    //todo проверка на наличие тегов и переписать на более низкоуровневый код, ЕСЛИ БУДЕТ БЫСТРЕЕ
    public String getRssXml(String url) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        return forEntity.getBody();
    }

    @Override
    public Rss xmlToJson(String xmlBody) throws XPathExpressionException {
//        String xml = getRssXml(url);
//        xml = formattingXml(xml);

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<rss version=\"2.0\" xmlns:atom=\"http://www.w3.org/2005/Atom\">\n" +
                "    <channel>\n" +
                "        <language>ru</language>\n" +
                "        <title>Lenta.ru : Новости</title>\n" +
                "        <description>Новости, статьи, фотографии, видео. Семь дней в неделю, 24 часа в сутки.</description>\n" +
                "        <link>https://lenta.ru</link>\n" +
                "        <image>\n" +
                "            <url>https://assets.lenta.ru/small_logo.png</url>\n" +
                "            <title>Lenta.ru</title>\n" +
                "            <link>https://lenta.ru</link>\n" +
                "            <width>134</width>\n" +
                "            <height>22</height>\n" +
                "        </image>\n" +
                "        <atom:link rel=\"self\" type=\"application/rss+xml\" href=\"http://lenta.ru/rss\"/>\n" +
                "        <item>\n" +
                "            <guid>https://lenta.ru/news/2020/04/05/region/</guid>\n" +
                "            <title>Названы регионы России с наибольшим числом заразившихся коронавирусом</title>\n" +
                "            <link>https://lenta.ru/news/2020/04/05/region/</link>\n" +
                "            <description>\n" +
                "                Наиболее число заразившихся коронавирусом в России зафиксировано в Москве, Московской области и Санкт-Петербурге. В Москве зарегистрировано 3893 случая заражения, в Московской области — 305 случаев, в Петербурге — 191 случай. Еще 59 инфицированных зафиксировано в Республике Коми и 57 — в Ленинградской области.>\n" +
                "            </description>\n" +
                "            <pubDate>Sun, 05 Apr 2020 13:56:00 +0300</pubDate>\n" +
                "            <enclosure url=\"https://icdn.lenta.ru/images/2020/04/05/13/20200405135636052/pic_0bebb17f9f9841aa362582fa7802b15d.jpg\" type=\"image/jpeg\" length=\"80684\"/>\n" +
                "            <enclosure url=\"https://icdn.lenta.ru/images/2020/04/05/13/20200405135636052/pic_0bebb17f9f9841aa362582fa7802b15d.jpg\" type=\"image/jpeg\" length=\"80684\"/>\n" +
                "            <category>Россия, говно</category>\n" +
                "        </item>\n" +
                "        <item>\n" +
                "            <guid>https://lenta.ru/news/2020/04/05/kremlin/</guid>\n" +
                "            <title>В Кремле рассказали о сроках дистанционной работы Путина</title>\n" +
                "            <link>https://lenta.ru/news/2020/04/05/kremlin/</link>\n" +
                "            <description>\n" +
                "                Президент России Владимир Путин будет работать в дистанционном режиме еще как минимум неделю. Об этом заявил пресс-секретарь главы государства Дмитрий Песков. По его словам, президент ограничил физические контакты для минимизации риска заражения коронавирусной инфекцией.>\n" +
                "            </description>\n" +
                "            <pubDate>Sun, 05 Apr 2020 13:49:00 +0300</pubDate>\n" +
                "            <enclosure url=\"https://icdn.lenta.ru/images/2020/04/05/13/20200405135042580/pic_7b43621defb07acf3c1486b8578246bc.jpg\" type=\"image/jpeg\" length=\"77643\"/>\n" +
                "            <category>Россия</category>\n" +
                "        </item>\n" +
                "        <item>\n" +
                "            <guid>https://lenta.ru/news/2020/04/05/shri/</guid>\n" +
                "            <title>На Шри-Ланке освободили почти три тысячи заключенных из-за коронавируса</title>\n" +
                "            <link>https://lenta.ru/news/2020/04/05/shri/</link>\n" +
                "            <description>\n" +
                "                Власти Шри-Ланки освободили из тюрем 2961 заключенного под залог из-за коронавируса. Были освобождены осужденные за мелкие нарушения и те, чей срок почти закончился. В настоящее время в тюрьмах Шри-Ланки содержатся более 26 тысяч человек, в то время как общая вместимость не превышает десяти тысяч. >\n" +
                "            </description>\n" +
                "            <pubDate>Sun, 05 Apr 2020 13:42:00 +0300</pubDate>\n" +
                "            <enclosure url=\"https://icdn.lenta.ru/images/2020/04/05/13/20200405131942365/pic_b6541cda9a9f9d0d58eb969be42b9063.jpg\" type=\"image/jpeg\" length=\"48018\"/>\n" +
                "            <enclosure url=\"https://icdn.lenta.ru/images/2020/04/05/13/20200405131942365/pic_b6541cda9a9f9d0d58eb969be42b9062.jpg\" type=\"image/jpeg\" length=\"48018\"/>\n" +
                "            <category>Мир</category>\n" +
                "        </item>\n" +
                "        <item>\n" +
                "            <guid>https://lenta.ru/news/2020/04/05/minoboron/</guid>\n" +
                "            <title>Военкоматы России переведены на удаленную работу из-за коронавируса</title>\n" +
                "            <link>https://lenta.ru/news/2020/04/05/minoboron/</link>\n" +
                "            <description>\n" +
                "                Министерство обороны России перевело военкоматы на удаленную работу из-за коронавируса. Все сотрудники военных комиссариатов переведены на удаленный режим работы. Уточнение всей необходимой информации проводится по телефону, чтобы исключить личное посещение военкоматов. >\n" +
                "            </description>\n" +
                "            <pubDate>Sun, 05 Apr 2020 13:42:00 +0300</pubDate>\n" +
                "            <enclosure url=\"https://icdn.lenta.ru/images/2020/04/05/13/20200405134817678/pic_bc245ae40da3b60a4a0e4d526119c862.jpg\" type=\"image/jpeg\" length=\"63985\"/>\n" +
                "            <category>Россия</category>\n" +
                "        </item>\n" +
                "    </channel>\n" +
                "</rss>\n";

        xml = formattingXml(xml);
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        InputSource source = new InputSource(new StringReader(xml));
        Document doc = (Document) xpath.evaluate("/", source, XPathConstants.NODE);


        String feedLang = xpath.evaluate("//feed/language", doc);
        String feedTitle = xpath.evaluate("//feed/title", doc);
        String feedDescription = xpath.evaluate("//feed/description", doc);
        String feedLink = xpath.evaluate("//feed/link", doc);

        String feedImageUrl = xpath.evaluate("//feed/image/url", doc);
        String feedImageTitle = xpath.evaluate("//feed/image/title", doc);
        String feedImageLink = xpath.evaluate("//feed/image/link", doc);
        String feedImageWidth = xpath.evaluate("//feed/image/width", doc);
        String feedImageHeight = xpath.evaluate("//feed/image/height", doc);

        Feed feed = new Feed(feedLang,
                feedTitle,
                feedDescription,
                feedLink);

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
            if(enclosureCount > 1) {
                for (int j = 1; j < enclosureCount +1; j++) {
                    enclosure.add(xpath.evaluate(String.format("//items/item[%s]/enclosure[%s]/@url", i, j), doc));
                }
            } else {
                enclosure.add(xpath.evaluate(String.format("//items/item[%s]/enclosure/@url", i), doc));
            }


            List<String> category = Arrays.asList(
                    (xpath.evaluate(String.format("//items/item[%s]//category", i), doc).split(", ")
                    ));


            Item item = new Item();
            item.setEnclosure(enclosure);
            item.setCategory(category);


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
