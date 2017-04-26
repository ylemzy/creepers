package application.kafka;/**
 * Created by huangzebin on 2017/4/26.
 */

import application.util.JsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class Sender {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void sendMessage(){
        KafkaMessage m = new KafkaMessage();
        m.setId(System.currentTimeMillis());
        m.setMsg(UUID.randomUUID().toString());
        m.setSendTime(new Date());
        kafkaTemplate.send("test1", JsonHelper.toJSON(m));
    }
}
