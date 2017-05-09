package application.kafka;/**
 * Created by huangzebin on 2017/5/8.
 */

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Properties;

public class Consumer {
    private static final Logger logger = LogManager.getLogger();

    private final KafkaConsumer<String, String> consumer;

    public Consumer(String topic){
        Properties prop = KafkaProperties.get().getProp();
        consumer = new KafkaConsumer<String, String>(prop);
        consumer.subscribe(Arrays.asList(topic));
    }
}
