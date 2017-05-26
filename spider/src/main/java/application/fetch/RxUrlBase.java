package application.fetch;/**
 * Created by huangzebin on 2017/5/25.
 */

import application.elastic.document.Link;
import application.kafka.ProducerPipeline;
import application.redis.RedisKeyConfig;
import application.redis.RedisServiceImpl;
import http.UrlMaker;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class RxUrlBase {
    private static final Logger logger = LogManager.getLogger();

    protected final String[] exclude = {
            ".mp3",
            ".apk",
            ".exe",
            ".gz",
            ".tar",
            ".gzip",
            ".zap",
            "zip"
    };

    @Autowired
    protected RedisServiceImpl redisService;

    @Autowired
    private ProducerPipeline producerPipeline;

    public void toQueue(Link link){
        producerPipeline.send(link);
    }

    public boolean isValid(Link link){
        return StringUtils.isNotEmpty(link.getUrl());
    }

    public boolean excludeFormat(Link link){
        for (String s : exclude) {
            if (link.getUrl().endsWith(s))
                return false;
        }
        return true;
    }

    public void error(Link url, Throwable throwable){
        logger.error("url:" + url.getUrl(), throwable);
        redisService.sAdd(RedisKeyConfig.ERROR_URL, url.getUrl());
    }

    public long sleepSecond(){
        ThreadPoolExecutor diggerService = (ThreadPoolExecutor) ExecutorManager.getDiggerService();
        int sleep = diggerService.getQueue().size() / diggerService.getMaximumPoolSize();
        if (sleep > 10){
            return sleep;
        }
        return 0;
    }

    public Link sleepUntilIdle(Link link) throws InterruptedException {

        long l = sleepSecond();
        long time = 0;
        while (l > 0){
            time += l;
            Thread.sleep(TimeUnit.SECONDS.toMillis(l));
            l = sleepSecond();
        }

        return link;
    }
}
