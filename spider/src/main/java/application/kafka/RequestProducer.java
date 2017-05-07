package application.kafka;

import application.fetch.request.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import util.JsonHelper;

/**
 * Created by J on 5/7/2017.
 */
@Component
public class RequestProducer {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void sendRequest(Request request){
        kafkaTemplate.send("request", JsonHelper.toJSON(request));
    }
}
