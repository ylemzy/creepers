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
import util.JsonHelper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
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
        URL url = new URL("http://baidu.com.ss.ff.ee/test?ss=ee");
        logger.info("-------------------------");
        logger.info("host {}", url.getHost());
        logger.info("port {}", url.getPort());
        logger.info("auth {}", url.getAuthority());
        logger.info("path {}", url.getPath());
        logger.info("protocol {}", url.getProtocol());
        logger.info("ref {}", url.getRef());
        logger.info("userInfo {}", url.getUserInfo());

    }

    @Test
    public void testFilter() throws InterruptedException {
        List<Link> list = new ArrayList<>();
        for (int i = 0; i < 100; ++i) {
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


        list.forEach(item -> {
            Observable<UrlMaker> map = Observable.just(item)
                    .map(link -> {
                        logger.info("Map {}: thread {}", ((Link) link).getUrl(), Thread.currentThread().getName());
                        return new UrlMaker(((Link) link).getUrl());
                    });

            map.delay(5, TimeUnit.SECONDS, Schedulers.single())
                    .map(link -> {
                        ThreadPoolExecutor executorService1 = (ThreadPoolExecutor) executorService;
                        logger.info("queue {}", executorService1.getQueue().size());
                        return link;
                    });
            map.map(link -> {
                logger.info("keep map");
                Thread.sleep(3000);
                //Observable.just(1).delay(5, TimeUnit.SECONDS, Schedulers.single());
                return link;
            }).map(link -> {
                logger.info("after delay");
                return link;
            }).observeOn(Schedulers.from(executorService))
                    .map(link -> {

                        List<String> res = new ArrayList<>();
                        for (int i = 0; i < 1; ++i) {
                            res.add(link.getUrl() + "<>" + i);
                        }
                        Thread.sleep(1000);
                        logger.info("Map to list {}: thread {}", JsonHelper.toJSON(res), Thread.currentThread().getName());
                        return res;
                    })
                    .subscribe(urlMaker -> logger.info("Subscribe {}: thread {}", JsonHelper.toJSON(urlMaker), Thread.currentThread().getName()),
                            throwable -> logger.error("sss:", throwable)
                    );

            logger.info("----------------------- {}", item);

        });

        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        Thread.sleep(TimeUnit.SECONDS.toMillis(13));
    }


}