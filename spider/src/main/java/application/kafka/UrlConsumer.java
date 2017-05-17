package application.kafka;/**
 * Created by huangzebin on 2017/4/26.
 */

import application.elastic.document.Link;
import application.fetch.url.UrlFlow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import util.JsonHelper;

@Component
public class UrlConsumer {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    UrlFlow urlFlow;

    private void process(String content){
        try{
            KafkaMessage kafkaMessage = JsonHelper.toObject(content, KafkaMessage.class);
            Link link = kafkaMessage.getLink();
            urlFlow.flow(link, true);
        }catch (Exception e){
            logger.error(content, e);
        }
        log("url", content);

    }

    @KafkaListener(topics = "url")
    public void processMessage(String content) {
        process(content);

    }

    private void log(String type, Object sendObject){
        //logger.info("Consumer {}:{} -> {}", type, Thread.currentThread().getName(), JsonHelper.toJSON(sendObject));
    }
}
