package application.fetch.url;

import application.elastic.document.Link;
import application.fetch.UrlType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * Created by huangzebin on 2017/5/10.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlRawUrlFlowTest {


    @Autowired
    RawUrlFlow rawUrlFlow;

    @Test
    public void flow() throws Exception {
        Link link = new Link("http://lx.huanqiu.com/", UrlType.RAW);
        rawUrlFlow.flow(link, true);

        Thread.sleep(TimeUnit.HOURS.toMillis(1));
    }

}