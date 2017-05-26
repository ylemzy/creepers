package application.fetch.url;

import application.elastic.document.Link;
import application.fetch.UrlType;
import application.kafka.ProducerPipeline;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * Created by huangzebin on 2017/5/25.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryFlowTest {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    CategoryFlow categoryFlow;

    @Autowired
    ProducerPipeline producerPipeline;

    @Test
    public void flow() throws Exception {
        Link link = new Link("http://lx.huanqiu.com/", UrlType.HOST);
        categoryFlow.flow(link, true);
        Thread.sleep(TimeUnit.HOURS.toMillis(1));
    }

    @Test
    public void consumer() throws InterruptedException {
        Link link = new Link("http://lx.huanqiu.com/", UrlType.HOST);
        producerPipeline.send(link);

        Thread.sleep(TimeUnit.HOURS.toMillis(1));
    }

}