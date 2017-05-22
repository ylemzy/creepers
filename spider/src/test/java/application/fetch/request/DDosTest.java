package application.fetch.request;/**
 * Created by huangzebin on 2017/5/18.
 */

import application.metrics.Metrics;
import com.codahale.metrics.*;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class DDosTest {
    private static final Logger logger = LogManager.getLogger();
    private static MetricRegistry metrics = Metrics.getMetrics();
    private static Meter counter = metrics.meter("requests");
    private static Meter errorCounter = metrics.meter("error");
    //private final Histogram responseTimes = metrics.histogram("response-times");
    private final Timer timer = metrics.timer("responses");

    @Test
    public void testRequest() throws InterruptedException {



        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.SECONDS);


        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10000; ++i){
            list.add(i);
        }

        ExecutorService executorService =
                Executors.newFixedThreadPool(100, new ThreadFactory() {
                    AtomicInteger atomic = new AtomicInteger();

                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "Thread " + atomic.getAndIncrement());
                    }
                });

        Observable.fromArray(list.toArray())
                .flatMap(item ->{
                    return Observable.create(inner ->{
                        query((Integer) item);
                        inner.onComplete();
                    }).subscribeOn(Schedulers.io());
                })
                .subscribeOn(Schedulers.io())
                .subscribe();


        while (counter.getCount() < list.size()){
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        }

    }


    private void query(int item) throws Exception {
        //Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        Timer.Context time = timer.time();

        //String remote = "http://106.75.148.97:9994/recommend/all?custId=14941330911700845&day=400";
        String remote = "http://localhost:9994/recommend/all?custId=14941330911700845&day=400";
        try{
            Document document =
                    Jsoup.connect(remote)
                            .ignoreContentType(true)
                            .timeout((int) TimeUnit.SECONDS.toMillis(10))
                            .get();
        }catch (Exception e){
            errorCounter.mark();
        }

        //logger.info("id={},Thread name {}: {}", item, Thread.currentThread().getName(), document.body().text());
        time.stop();
        counter.mark();
    }



}
