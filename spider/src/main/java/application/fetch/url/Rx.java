package application.fetch.url;/**
 * Created by huangzebin on 2017/5/9.
 */

import application.elastic.document.Link;
import application.kafka.ProducerPipeline;
import application.redis.RedisKeyConfig;
import application.redis.RedisServiceImpl;
import http.UrlMaker;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class Rx {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private RedisServiceImpl redisService;

    @Autowired
    private ProducerPipeline producerPipeline;


    public boolean isValid(Link link){
        return StringUtils.isNotEmpty(link.getUrl());
    }

    public UrlMaker make(Link link) throws MalformedURLException {
        return new UrlMaker(link.getUrl());
    }

    public boolean noFetchedInRedis(UrlMaker urlMaker){
        return !redisService.sIsMember(RedisKeyConfig.DIG_URL, urlMaker.getRowUrl());
    }

    public List<UrlMaker> dig(UrlMaker urlMaker) throws IOException {
        Document document = Jsoup.connect(urlMaker.getRowUrl()).get();
        Elements urls = document.getElementsByTag("a");
        List<UrlMaker> res = new ArrayList<>();
        urls.forEach(a -> {
            try {
                res.add(UrlMaker.make(a.absUrl("href")));
            } catch (MalformedURLException e) {
                logger.error(e, e);
            }
        });
        return res;
    }

    public void toQueue(Link url, UrlMaker urlMaker){
        url.setType(UrlHelper.filter(urlMaker));
        redisService.sAdd(RedisKeyConfig.DIG_URL, urlMaker.getRowUrl());
        producerPipeline.send(url);
    }

    public void error(Link url, Throwable throwable){
        logger.error("url:" + url.getUrl(), throwable);
        redisService.sAdd(RedisKeyConfig.ERROR_URL, url.getUrl());
    }
}
