package application.kafka;/**
 * Created by huangzebin on 2017/5/8.
 */

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Properties;

@Configuration
public class KafkaProperties {
    private static final Logger logger = LogManager.getLogger();

    @Value("spring.kafka.bootstrap-servers")
    public String servers;

    @Value("spring.kafka.consumer.group-id")
    public String group;

    @Value("spring.kafka.producer.key-serializer")
    public String keySerializer;

    @Value("spring.kafka.consumer.key-deserializer")
    public String keyDeserializer;

    @Value("spring.kafka.producer.value-serializer")
    public String valueSerializer;

    @Value("spring.kafka.consumer.value-deserializer")
    public String valueDeserializer;


    private static KafkaProperties kafkaProperties;
    @PostConstruct()
    public void init(){
        KafkaProperties.kafkaProperties = this;
    }

    public static KafkaProperties get(){
        return KafkaProperties.kafkaProperties;
    }

    public Properties getProp(){
        Properties props = new Properties();

        props.put("group.id", group);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        //props.put("enable.auto.commit", "true");        //本例使用自动提交位移
        //props.put("auto.commit.interval.ms", "1000");
        //props.put("session.timeout.ms", "30000");
        //props.put(ProducerConfig.BATCH_SIZE_CONFIG, 4096);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        props.put("key.deserializer", keyDeserializer);
        props.put("value.deserializer", valueDeserializer);
        return props;
    }
}
