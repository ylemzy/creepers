package application.kafka;/**
 * Created by huangzebin on 2017/4/26.
 */

import application.elastic.UrlBatchSaver;
import application.elastic.document.Link;
import application.fetch.url.ExecutorManager;
import application.fetch.url.UrlDigger;
import application.fetch.url.UrlFlow;
import io.reactivex.Observable;
import org.springframework.beans.factory.annotation.Autowired;
import util.JsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class UrlConsumer {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    UrlFlow urlFlow;

    int sleepTime = 0;
    @PostConstruct
    public void start(){
        //Ex
    }

    private void handle(Link link){
        ExecutorManager.getDiggerService().execute(new Runnable() {
            @Override
            public void run() {
                urlFlow.flow(link);
            }
        });
    }

    @Autowired
    UrlBatchSaver urlBatchSaver;

    private void process(String content){
        try{
            KafkaMessage kafkaMessage = JsonHelper.toObject(content, KafkaMessage.class);
            Link link = kafkaMessage.getLink();
            urlBatchSaver.save(link);
            handle(link);
        }catch (Exception e){
            logger.error(e, e);
        }
    }

    @KafkaListener(topics = "url")
    public void processMessage(String content) {
        log("url", content);

        Observable<String> just = Observable.just(content);


        ThreadPoolExecutor diggerService = (ThreadPoolExecutor) ExecutorManager.getDiggerService();
        int activeCount = diggerService.getActiveCount();
        BlockingQueue<Runnable> queue = diggerService.getQueue();
        int size = queue.size();
        logger.info("active Count = {}, complete = {}, queue={}", activeCount, diggerService.getCompletedTaskCount(), size);

        int sleep = diggerService.getQueue().size() / diggerService.getMaximumPoolSize();
        while (sleep > 10){
            just = just.delay(sleep, TimeUnit.SECONDS);
            logger.info("Sleep {}s...", sleep);
            sleepTime += sleep;
            sleep = queue.size() / diggerService.getMaximumPoolSize();
        }

        just.subscribe(str -> processMessage(str));

    }

    private void log(String type, Object sendObject){
        //logger.info("Consumer {}:{} -> {}", type, Thread.currentThread().getName(), JsonHelper.toJSON(sendObject));
    }
}
