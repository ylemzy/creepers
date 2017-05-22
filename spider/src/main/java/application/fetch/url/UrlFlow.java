package application.fetch.url;/**
 * Created by huangzebin on 2017/4/19.
 */

import application.elastic.document.Link;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UrlFlow {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private RxUrl rxUrl;

    public void flow(Link url, boolean wait) {
        long start = System.currentTimeMillis();
        Observable.just(url)
                .filter(link -> rxUrl.isValid(link))
                .map(link -> rxUrl.saveLink(link))
                .map(link -> rxUrl.saveHostLink(link))
                .map(link -> rxUrl.make(link))
                .map(link -> {
                    if (wait) return rxUrl.sleepUntilIdle(link);
                    return link;
                })
                .observeOn(Schedulers.from(ExecutorManager.getDiggerService()))
                .flatMapIterable(link -> rxUrl.dig(link))
                .filter(link -> rxUrl.excludeFormat(link))
                .filter(urlMaker -> rxUrl.noFetchedInRedis(urlMaker))
                .subscribe(urlMaker -> rxUrl.toQueue(urlMaker),
                        throwable -> rxUrl.error(url, throwable));
        long end = System.currentTimeMillis();
        //if (end - start > 0)
        //logger.info("------------------------------------------------------------------------->>>>>> {}ms", end - start);
    }
}
