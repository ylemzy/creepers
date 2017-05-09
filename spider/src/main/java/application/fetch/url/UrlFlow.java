package application.fetch.url;/**
 * Created by huangzebin on 2017/4/19.
 */

import application.elastic.document.Link;
import io.reactivex.Observable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

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

    public void flow(Link url) {
        Observable.just(url)
                .filter( link -> rx.isValid(link))
                .map(link -> rx.make(link))
                .flatMapIterable(link -> rx.dig(link))
                .filter(urlMaker -> rx.noFetchedInRedis(urlMaker))
                .subscribe(urlMaker -> rx.toQueue(url, urlMaker),
                        throwable -> rx.error(url, throwable));
    }
}
