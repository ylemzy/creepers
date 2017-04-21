package application.fetch.filter;/**
 * Created by huangzebin on 2017/4/19.
 */

import application.fetch.templates.UrlDigger;
import application.http.UrlMaker;
import application.redis.RedisServiceImpl;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UrlFilter {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private RedisServiceImpl redisService;

    private static UrlFilter filter;

    @PostConstruct
    public void init(){
        filter = this;
    }

    public static UrlFilter get(){
        return filter;
    }

    public void filter(Url url) {
        Observable.just(url)
                .map(link -> new UrlMaker(link.getUrl()))
                .filter(link -> true)
                .subscribe(urlMaker -> {
                    System.out.println(urlMaker.getUrl());
                    redisService.sAdd(urlMaker.getHost(), urlMaker.getUrl());
                    new UrlDigger(urlMaker.getUrl()).dig();
                });
    }
}
