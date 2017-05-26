package application.fetch.url;/**
 * Created by huangzebin on 2017/4/19.
 */

import application.elastic.document.Link;
import application.fetch.ExecutorManager;
import application.fetch.UrlFlow;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RawUrlFlow implements UrlFlow{
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private RawUrlTap rawUrlTap;

    @Override
    public void flow(Link url, boolean wait) {
        Observable.just(url)
                .filter(link -> rawUrlTap.isValid(link))
                .filter(link -> rawUrlTap.noFetchedInRedis(link))
                .map(link -> rawUrlTap.saveRedis(link))
                .map(link -> rawUrlTap.saveLink(link))
                .map(link -> rawUrlTap.saveHostLink(link))
                .map(link -> {
                    if (wait) return rawUrlTap.sleepUntilIdle(link);
                    return link;
                })
                .observeOn(Schedulers.from(ExecutorManager.getDiggerService()))
                .flatMapIterable(link -> rawUrlTap.dig(link))
                .filter(link -> rawUrlTap.excludeFormat(link))
                .filter(link -> rawUrlTap.noFetchedInRedis(link))
                .subscribe(link -> rawUrlTap.toQueue(link),
                        throwable -> rawUrlTap.error(url, throwable));
    }
}
