package application.fetch.url;/**
 * Created by huangzebin on 2017/4/19.
 */

import application.elastic.document.Link;
import application.fetch.UrlFlow;
import application.fetch.ExecutorManager;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryFlow implements UrlFlow{
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private CategoryTap rxCategory;

    @Override
    public void flow(Link url, boolean wait) {
        Observable.just(url)
                .filter(link -> rxCategory.isValid(link))
                .filter(link -> rxCategory.noFetchedInRedis(link))
                .map(link -> rxCategory.saveRedis(link))
                .map(link -> {
                    if (wait) return rxCategory.sleepUntilIdle(link);
                    return link;
                })
                .observeOn(Schedulers.from(ExecutorManager.getDiggerService()))
                .flatMapIterable(link -> rxCategory.dig(link))
                .subscribe(link -> rxCategory.toQueue(link),
                        throwable -> rxCategory.error(url, throwable));
    }
}
