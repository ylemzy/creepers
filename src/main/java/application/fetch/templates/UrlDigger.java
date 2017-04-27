package application.fetch.templates;/**
 * Created by huangzebin on 2017/4/19.
 */

import application.fetch.filter.Url;
import application.fetch.filter.UrlFilter;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class UrlDigger {
    private static final Logger logger = LogManager.getLogger();

    String url;

    static ExecutorService executorService =
            Executors.newFixedThreadPool(10, new ThreadFactory() {
                AtomicInteger atomic = new AtomicInteger();
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "Thread " + atomic.getAndIncrement());
                }
            });

    public UrlDigger(String url){
        this.url = url;
    }

    public void dig() throws IOException {
        Observable.just(url)
                .subscribeOn(Schedulers.from(executorService))
                .subscribe(link ->{
                    Document document = Jsoup.connect(url).get();
                    Elements urls = document.getElementsByTag("a");
                    urls.forEach(a->{
                        UrlFilter.get().filter(new Url(a.absUrl("href")));
                    });
                });
    }



}
