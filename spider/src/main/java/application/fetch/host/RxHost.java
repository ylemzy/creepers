package application.fetch.host;/**
 * Created by huangzebin on 2017/5/9.
 */

import application.elastic.HostLinkBatchSaver;
import application.elastic.UrlBatchSaver;
import application.elastic.document.HostLink;
import application.elastic.document.Link;
import application.fetch.url.ExecutorManager;
import application.fetch.url.UrlHelper;
import application.kafka.ProducerPipeline;
import application.redis.RedisKeyConfig;
import application.redis.RedisServiceImpl;
import http.UrlMaker;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class RxHost {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private RedisServiceImpl redisService;

    @Autowired
    private ProducerPipeline producerPipeline;

    @Autowired
    UrlBatchSaver urlBatchSaver;

    @Autowired
    HostLinkBatchSaver hostLinkBatchSaver;

    private final String[] exclude = {
            ".mp3",
            ".apk",
            ".exe",
            ".gz",
            ".tar",
            ".gzip",
            ".zap",
            "zip"
    };

    public boolean isValid(Link link){
        //logger.info("Check link {}, {}", link.getUrl(), Thread.currentThread().getName());
        return StringUtils.isNotEmpty(link.getUrl());
    }

    public UrlMaker make(Link link) throws MalformedURLException {
        return new UrlMaker(link.getUrl());
    }

    public boolean noFetchedInRedis(UrlMaker urlMaker){
        return !redisService.sIsMember(RedisKeyConfig.DIG_URL, urlMaker.getRowUrl());
    }

    public boolean excludeFormat(UrlMaker urlMaker){
        String rowUrl = urlMaker.getRowUrl();
        for (String s : exclude) {
            if (rowUrl.endsWith(s))
                return false;
        }
        return true;
    }

    public List<UrlMaker> dig(UrlMaker urlMaker) throws IOException {
        long l = System.currentTimeMillis();
        Document document = Jsoup.connect(urlMaker.getRowUrl())
                .timeout((int)TimeUnit.SECONDS.toMillis(10))
                .get();
        Elements urls = document.getElementsByTag("a");
        List<UrlMaker> res = new ArrayList<>();
        urls.forEach(a -> {
            try {
                String href = a.absUrl("href");
                if (StringUtils.isNotEmpty(href)){
                    res.add(UrlMaker.make(a.absUrl("href")));
                }
            } catch (MalformedURLException e) {
                logger.error("dig at {}, {}", a.absUrl("href"), e);
            }
        });

        long l1 = System.currentTimeMillis() - l;
        logger.info("Dig -->>> {}, {}ms", urlMaker.getRowUrl(), l1);
        return res;
    }

    public void toQueue(UrlMaker urlMaker){
        Link link = new Link(urlMaker.getRowUrl());
        link.setType(UrlHelper.filter(urlMaker));
        redisService.sAdd(RedisKeyConfig.DIG_URL, urlMaker.getRowUrl());
        //logger.info("To queue {}, {}", urlMaker.getRowUrl(), Thread.currentThread().getName());
        producerPipeline.send(link);
    }

    public void error(Link url, Throwable throwable){
        logger.error("url:" + url.getUrl(), throwable);
        redisService.sAdd(RedisKeyConfig.ERROR_URL, url.getUrl());
    }

    public Link saveLink(Link link){
        urlBatchSaver.save(link);
        return link;
    }

    public Link saveHostLink(Link link) throws MalformedURLException {
        HostLink hostLink = new HostLink();
        hostLink.setHost(UrlMaker.make(link.getUrl()).getUri());
        hostLinkBatchSaver.save(hostLink);
        return link;
    }

    public long sleepSecond(){
        ThreadPoolExecutor diggerService = (ThreadPoolExecutor) ExecutorManager.getDiggerService();
        int activeCount = diggerService.getActiveCount();
        BlockingQueue<Runnable> queue = diggerService.getQueue();
        int size = queue.size();
        //logger.info("active Count = {}, complete = {}, queue={}", activeCount, diggerService.getCompletedTaskCount(), size);

        int sleep = diggerService.getQueue().size() / diggerService.getMaximumPoolSize();
        if (sleep > 10){
            return sleep;
        }
        return 0;
    }

    public UrlMaker sleepUntilIdle(UrlMaker link) throws InterruptedException {

        /*long l = sleepSecond();
        if (l > 0)
        Thread.sleep(TimeUnit.SECONDS.toMillis(50));*/

        long l = sleepSecond();
        long time = 0;
        while (l > 0){
            time += l;
            Thread.sleep(TimeUnit.SECONDS.toMillis(l));
            l = sleepSecond();
        }
        //logger.info("Sleep time {}", time);
        return link;
    }

}
