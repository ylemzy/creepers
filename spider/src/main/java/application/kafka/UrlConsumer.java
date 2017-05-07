package application.kafka;/**
 * Created by huangzebin on 2017/4/26.
 */

import application.elastic.UrlBatchSaver;
import application.elastic.document.Url;
import application.fetch.url.UrlDigger;
import org.springframework.beans.factory.annotation.Autowired;
import util.JsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UrlConsumer {
    private static final Logger logger = LogManager.getLogger();


    @Autowired
    UrlBatchSaver urlBatchSaver;

    @KafkaListener(topics = "url")
    public void processMessage(String content) {
        log("url", content);
        try{
            KafkaMessage kafkaMessage = JsonHelper.toObject(content, KafkaMessage.class);
            Url url = kafkaMessage.getUrl();
            urlBatchSaver.save(url);
            UrlDigger urlDigger = new UrlDigger(url.getUrl());
            urlDigger.dig();
        }catch (Exception e){
            logger.error(e, e);
        }
    }

    private void log(String type, Object sendObject){
        logger.info("Consumer {}:{} -> {}", type, Thread.currentThread().getName(), JsonHelper.toJSON(sendObject));
    }
}
