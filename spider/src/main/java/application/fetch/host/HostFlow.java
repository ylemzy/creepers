package application.fetch.host;/**
 * Created by huangzebin on 2017/4/19.
 */

import application.elastic.document.Link;
import application.fetch.url.ExecutorManager;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HostFlow {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private RxHost rxHost;

    public void flow(Link url, boolean wait) {
        long start = System.currentTimeMillis();
        Observable.just(url)
                .filter(link -> rxHost.isValid(link))
                .map(link -> rxHost.saveLink(link))
                .map(link -> rxHost.saveHostLink(link))
                .map(link -> rxHost.make(link))
                .map(link -> {
                    if (wait) return rxHost.sleepUntilIdle(link);
                    return link;
                })
                .observeOn(Schedulers.from(ExecutorManager.getDiggerService()))
                .flatMapIterable(link -> rxHost.dig(link))
                .filter(link -> rxHost.excludeFormat(link))
                .filter(urlMaker -> rxHost.noFetchedInRedis(urlMaker))
                .subscribe(urlMaker -> rxHost.toQueue(urlMaker),
                        throwable -> rxHost.error(url, throwable));
        long end = System.currentTimeMillis();
        //if (end - start > 0)
        //logger.info("------------------------------------------------------------------------->>>>>> {}ms", end - start);
    }
}
