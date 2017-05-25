package application.kafka;/**
 * Created by huangzebin on 2017/4/26.
 */

import application.elastic.document.Link;
import application.fetch.UrlFlow;
import application.fetch.url.RawUrlFlow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import util.JsonHelper;

@Component
public class RawUrlConsumer implements UrlConsumer{
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    RawUrlFlow rawUrlFlow;

    @Override
    public void process(UrlFlow urlFlow, String content){
        log("Raw url", content);
        try{
            KafkaMessage kafkaMessage = JsonHelper.toObject(content, KafkaMessage.class);
            Link link = kafkaMessage.getLink();
            //urlFlow.flow(link, true);
            //log("Raw", kafkaMessage);
        }catch (Exception e){
            logger.error(content, e);
        }

    }

    @KafkaListener(topics = TopicConfig.rawUrlTopic)
    public void processMessage(String content) {
        process(rawUrlFlow, content);

    }

    private void log(String type, Object sendObject){
        logger.info("UrlConsumer {}:{} -> {}", type, Thread.currentThread().getName(), JsonHelper.toJSON(sendObject));
    }
}
