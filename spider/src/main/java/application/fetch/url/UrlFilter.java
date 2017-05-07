package application.fetch.url;/**
 * Created by huangzebin on 2017/4/19.
 */

import application.elastic.UrlBatchSaver;
import application.elastic.document.Url;
import application.kafka.ProducerPipeline;
import http.UrlMaker;
import application.kafka.UrlProducer;
import application.redis.RedisServiceImpl;
import io.reactivex.Observable;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UrlFilter {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private RedisServiceImpl redisService;

    @Autowired
    private ProducerPipeline producerPipeline;

    private static UrlFilter filter;

    @PostConstruct
    public void init() {
        filter = this;
    }

    public static UrlFilter get() {
        return filter;
    }

    public void filter(Url url) {
        Observable.just(url)
                .filter(link -> StringUtils.isNotEmpty(link.getUrl()))
                .map(link -> new UrlMaker(link.getUrl()))
                .filter(urlMake -> !redisService.sIsMember("allUrl", urlMake.getRowUrl()))
                .subscribe(urlMaker -> {
                            url.setType(UrlHelper.filter(urlMaker));

                            redisService.sAdd("allUrl", urlMaker.getRowUrl());
                            producerPipeline.send(url);
                        },
                        throwable -> {
                            logger.error("url:" + url.getUrl(), throwable);
                            redisService.sAdd("errorUlr", url.getUrl());
                        });
    }
}
