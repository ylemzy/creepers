package application.kafka;

import application.elastic.document.Link;
import application.fetch.UrlType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * Created by huangzebin on 2017/4/26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaMessageTest {

    @Autowired
    RawUrlConsumer rawUrlConsumer;

    @Autowired
    UrlProducer urlProducer;


    @Test
    public void test() throws InterruptedException {
        Link link = new Link("https://www.baidu.com/", UrlType.RAW);
        urlProducer.sendUrl(link);
        Thread.sleep(TimeUnit.SECONDS.toMillis(5));
    }
}