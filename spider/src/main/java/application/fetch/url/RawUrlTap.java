package application.fetch.url;/**
 * Created by huangzebin on 2017/5/9.
 */

import application.elastic.HostLinkBatchSaver;
import application.elastic.UrlBatchSaver;
import application.elastic.document.HostLink;
import application.elastic.document.Link;
import application.fetch.RxUrlBase;
import application.fetch.UrlType;
import application.fetch.http.Rest;
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
import java.util.concurrent.TimeUnit;

@Component
public class RawUrlTap extends RxUrlBase{
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private RedisServiceImpl redisService;

    @Autowired
    private ProducerPipeline producerPipeline;

    @Autowired
    UrlBatchSaver urlBatchSaver;

    @Autowired
    HostLinkBatchSaver hostLinkBatchSaver;

    public UrlMaker make(Link link) throws MalformedURLException {
        return new UrlMaker(link.getUrl());
    }

    public boolean noFetchedInRedis(Link link){
        return !redisService.sIsMember(RedisKeyConfig.DIG_URL, link.getUrl())
                && !redisService.sIsMember(RedisKeyConfig.ERROR_URL, link.getUrl());
    }

    public List<Link> dig(Link link) throws IOException {
        Document document = Rest.get(link.getUrl());
        Elements urls = document.getElementsByTag("a");
        List<Link> res = new ArrayList<>();
        urls.forEach(a -> {
                String href = a.absUrl("href");
                if (UrlMaker.isURL(href)){
                    Link e = new Link(href, UrlType.RAW);
                    e.setFromUrl(link.getUrl());
                    res.add(e);
                }
        });
        return res;
    }

    public Link saveRedis(Link link){
        redisService.sAdd(RedisKeyConfig.DIG_URL, link.getUrl());
        return link;
    }

    public void toQueue(Link link){
        producerPipeline.send(link);
    }

    public void error(Link url, Throwable throwable){
        logger.error("url:" + url.getUrl(), throwable);
        redisService.sAdd(RedisKeyConfig.ERROR_URL, url.getUrl());
    }

    public Link saveLink(Link link){
        urlBatchSaver.save(link);
        return link;
    }

    public Link saveHostLink(Link link) throws MalformedURLException {
        HostLink hostLink = new HostLink();
        String uri = UrlMaker.make(link.getUrl()).getUri();
        hostLink.setHost(uri);
        hostLink.setFromUrl(uri);
        hostLinkBatchSaver.save(hostLink);
        logger.info("Save host -> {}", hostLink.getHost());
        return link;
    }
}
