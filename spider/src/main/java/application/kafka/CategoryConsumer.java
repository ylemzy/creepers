package application.kafka;/**
 * Created by huangzebin on 2017/4/26.
 */

import application.elastic.document.Link;
import application.fetch.UrlFlow;
import application.fetch.url.CategoryFlow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import util.JsonHelper;

@Component
public class CategoryConsumer implements UrlConsumer {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    CategoryFlow categoryFlow;

    public void process(UrlFlow urlFlow, String content){
        log("Category", content);
        try{
            KafkaMessage kafkaMessage = JsonHelper.toObject(content, KafkaMessage.class);
            Link link = kafkaMessage.getLink();
            //urlFlow.flow(link, true);
        }catch (Exception e){
            logger.error(content, e);
        }
    }

    @KafkaListener(topics = TopicConfig.categoryUrlTopic)
    public void processMessage(String content) {
        process(categoryFlow, content);

    }

    private void log(String type, Object sendObject){
        logger.info("UrlConsumer {}:{} -> {}", type, Thread.currentThread().getName(), JsonHelper.toJSON(sendObject));
    }
}
