package application.kafka;/**
 * Created by huangzebin on 2017/4/26.
 */

import application.util.JsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
    private static final Logger logger = LogManager.getLogger();


    @KafkaListener(topics = "test1")
    public void processMessage(String content) {
        KafkaMessage kafkaMessage = JsonHelper.toObject(content, KafkaMessage.class);
        logger.info(JsonHelper.toJSON(kafkaMessage));
    }
}
