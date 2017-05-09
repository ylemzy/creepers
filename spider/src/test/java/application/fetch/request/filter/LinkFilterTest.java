package application.fetch.request.filter;

import application.elastic.document.Link;
import http.UrlMaker;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by huangzebin on 2017/4/21.
 */
/*@RunWith(SpringRunner.class)
@SpringBootTest*/
public class LinkFilterTest {
    private static final Logger logger = LogManager.getLogger();
/*    @Autowired
    UrlFilter urlFilter;*/

    @Test
    public void test() throws MalformedURLException {
        URL url = new URL("http://baidu.com.ss.ff.ee:9999/");
        logger.info("host {}", url.getHost());
        logger.info("auth {}", url.getAuthority());
        logger.info("path {}", url.getPath());
        logger.info("protocol {}", url.getProtocol());
        logger.info("ref {}", url.getRef());
        logger.info("userInfo {}", url.getUserInfo());

    }

    @Test
    public void testFilter() throws InterruptedException {
        List<Link> list = new ArrayList<>();
        for (int i = 0; i < 1000; ++i){
            list.add(new Link("http://www." + i + "baidu.com"));
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
                        //.filter(urlMake -> !redisService.sIsMember(urlMake.getHost(), urlMake.getRowUrl()))
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