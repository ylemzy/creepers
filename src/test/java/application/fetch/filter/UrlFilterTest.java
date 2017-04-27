package application.fetch.filter;

import application.http.UrlMaker;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

/**
 * Created by huangzebin on 2017/4/21.
 */
/*@RunWith(SpringRunner.class)
@SpringBootTest*/
public class UrlFilterTest {
    private static final Logger logger = LogManager.getLogger();
/*    @Autowired
    UrlFilter urlFilter;*/

    @Test
    public void test() {
       /* Url url = new Url("http://www.baidu.com/index/test?a=1&b=3");
        urlFilter.filter(url);*/
    }

    @Test
    public void tesTfilter() throws InterruptedException {
        List<Url> list = new ArrayList<>();
        for (int i = 0; i < 1000; ++i){
            list.add(new Url("http://www." + i + "baidu.com"));
        }

        ExecutorService executorService =
                Executors.newFixedThreadPool(10, new ThreadFactory() {
                    AtomicInteger atomic = new AtomicInteger();
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "Thread " + atomic.getAndIncrement());
                    }
                });

        try{
            list.forEach(url->{
                Observable.just(url)
                        .map(link -> {
                            logger.info("Map: Current thread " + Thread.currentThread().getName());
                            return new UrlMaker(link.getUrl());})
                        //.filter(urlMake -> !redisService.sIsMember(urlMake.getHost(), urlMake.getUrl()))
                        .subscribeOn(Schedulers.from(executorService))
                        .subscribe(new Consumer<UrlMaker>() {
                                       @Override
                                       public void accept(@NonNull UrlMaker urlMaker) throws Exception {
                                           logger.info("accept: Current thread " + Thread.currentThread().getName());
                                       }
                                   },
                                new Consumer<Throwable>() {
                                    @Override
                                    public void accept(@NonNull Throwable throwable) throws Exception {
                                        logger.error("sss:" + url.getUrl(), throwable);
                                    }
                                }
                        );
            });

        }catch (Exception e){
            logger.error("----------------<<<<<<>>>><><><><>");
        }



        Thread.sleep(TimeUnit.SECONDS.toMillis(10));
    }


}