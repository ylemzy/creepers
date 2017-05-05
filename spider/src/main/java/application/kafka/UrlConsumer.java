package application.kafka;/**
 * Created by huangzebin on 2017/4/26.
 */

import application.fetch.templates.UrlDigger;
import util.JsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UrlConsumer {
    private static final Logger logger = LogManager.getLogger();


    @KafkaListener(topics = "test1")
    public void processMessage(String content) {
        KafkaMessage kafkaMessage = JsonHelper.toObject(content, KafkaMessage.class);
        //logger.info(JsonHelper.toJSON(kafkaMessage));
        UrlDigger urlDigger = new UrlDigger(kafkaMessage.getUrl().getUrl());
        try {
            urlDigger.dig();
        } catch (IOException e) {
            logger.error(e, e);
        }
    }
}
