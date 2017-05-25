package application.fetch.url;

import application.elastic.document.Link;
import application.fetch.UrlType;
import application.kafka.PageConsumer;
import application.kafka.ProducerPipeline;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by J on 5/26/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PageFlowTest {

    @Autowired
    PageFlow pageFlow;

    @Autowired
    private ProducerPipeline producerPipeline;

    @Test
    public void test() throws InterruptedException {

        Link link = new Link("http://lx.huanqiueeeee.com/", UrlType.PAGE);
        producerPipeline.send(link);

        Thread.sleep(TimeUnit.HOURS.toMillis(1));
    }
}