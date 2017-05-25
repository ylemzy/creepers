package application.fetch.url;/**
 * Created by huangzebin on 2017/5/9.
 */

import application.elastic.PageLinkBatchSaver;
import application.elastic.UrlBatchSaver;
import application.elastic.document.Link;
import application.elastic.document.PageLink;
import application.fetch.RxUrlBase;
import application.fetch.UrlType;
import application.kafka.ProducerPipeline;
import application.redis.RedisKeyConfig;
import application.redis.RedisServiceImpl;
import http.UrlMaker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.ElementUtil;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class CategoryTap extends RxUrlBase {
    private static final Logger logger = LogManager.getLogger();



    @Autowired
    UrlBatchSaver urlBatchSaver;

    @Autowired
    PageLinkBatchSaver pageLinkBatchSaver;


    public boolean noFetchedInRedis(Link link){
        return !redisService.sIsMember(RedisKeyConfig.CATEGORY_URL, link.getUrl());
    }

    public List<Link> dig(Link link) throws IOException {
        logger.info("Dig category {}", link.getUrl());
        Document document = Jsoup.connect(link.getUrl())
                .timeout((int)TimeUnit.SECONDS.toMillis(10))
                .get();
        Elements elements = document.getElementsByTag("a");

        Elements shortTextLink = elements
                .stream()
                .filter(element -> element.text().length() > 0)
                .collect(Collectors.toCollection(Elements::new));

        Map<Element, Set<Element>> elementSetMap = ElementUtil.ModuleFinder.aggModuleInElement(shortTextLink);

        List<Link> result = new ArrayList<>();
        if (ElementUtil.PageLogic.isPage(elementSetMap) ||
                ElementUtil.PageLogic.isPage(ElementUtil.ModuleFinder.aggModuleInElement(shortTextLink, elementSetMap))){
            link.setType(UrlType.PAGE);
            result.add(link);
            redisService.sAdd(RedisKeyConfig.PAGE_URL, link.getUrl());
        }

        Map<Element, Set<Element>> categorySet = ElementUtil.PageLogic.filterCategory(elementSetMap);

        categorySet.forEach((k, v) ->{
            for (Element element : v) {
                Link category = new Link(element.absUrl("href"), UrlType.CATEGORY);
                result.add(category);
            }
        });

        return result;
    }

    public Link saveRedis(Link link){
        redisService.sAdd(RedisKeyConfig.CATEGORY_URL, link.getUrl());
        return link;
    }




}
