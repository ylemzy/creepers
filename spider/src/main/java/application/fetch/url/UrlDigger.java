package application.fetch.url;/**
 * Created by huangzebin on 2017/4/19.
 */

import application.elastic.document.Url;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class UrlDigger {
    private static final Logger logger = LogManager.getLogger();

    String url;

    static ExecutorService executorService =
            Executors.newFixedThreadPool(1, new ThreadFactory() {
                AtomicInteger atomic = new AtomicInteger();

                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "Thread " + atomic.getAndIncrement());
                }
            });

    public UrlDigger(String url) {
        this.url = url;
    }

    public void dig() {
        logger.info("Dig-------------------------------------------->");
        Observable.just(url)
                //.subscribeOn(Schedulers.from(executorService))
                .subscribe(link -> {
                    logger.info("Jsoup: {} -> {}", Thread.currentThread().getName(), url);
                            Document document = Jsoup.connect(url).get();
                            Elements urls = document.getElementsByTag("a");
                            urls.forEach(a -> {
                                UrlFilter.get().filter(new Url(a.absUrl("href")));
                            });
                        },
                        throwable -> {
                            logger.error("dig url:" + url, throwable);
                        });
    }


}
