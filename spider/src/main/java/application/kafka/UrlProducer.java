package application.kafka;/**
 * Created by huangzebin on 2017/4/26.
 */

import application.elastic.document.Link;
import util.JsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class UrlProducer {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void sendUrl(Link link){
        KafkaMessage m = new KafkaMessage();
        m.setLink(link);
        m.setId(System.currentTimeMillis());
        m.setMsg(UUID.randomUUID().toString());
        m.setSendTime(new Date());
        kafkaTemplate.send("url", JsonHelper.toJSON(m));
    }
}
