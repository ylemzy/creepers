package application.fetch.url;/**
 * Created by huangzebin on 2017/4/19.
 */

import application.elastic.document.Link;
import http.UrlMaker;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Component
public class UrlFlow {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private Rx rx;

    private static UrlFlow filter;

    @PostConstruct
    public void init() {
        filter = this;
    }

    public static UrlFlow get() {
        return filter;
    }

    public void flow(Link url, boolean wait) {
        long start = System.currentTimeMillis();
        Observable.just(url)
                .filter(link -> rx.isValid(link))
                .map(link -> rx.saveLink(link))
                .map(link -> rx.saveHostLink(link))
                .map(link -> rx.make(link))
                .map(link -> {
                    if (wait) return rx.sleepUntilIdle(link);
                    return link;
                })
                .observeOn(Schedulers.from(ExecutorManager.getDiggerService()))
                .flatMapIterable(link -> rx.dig(link))
                .filter(link -> rx.excludeFormat(link))
                .filter(urlMaker -> rx.noFetchedInRedis(urlMaker))
                .subscribe(urlMaker -> rx.toQueue(urlMaker),
                        throwable -> rx.error(url, throwable));
        long end = System.currentTimeMillis();
        //if (end - start > 0)
        //logger.info("------------------------------------------------------------------------->>>>>> {}ms", end - start);
    }
}
