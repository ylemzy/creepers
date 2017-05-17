package application.fetch.url;

import application.elastic.document.Link;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by huangzebin on 2017/5/10.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlFlowTest {


    @Autowired
    UrlFlow urlFlow;

    @Test
    public void flow() throws Exception {
        Link link = new Link("http://www.toutiao.com/");
        urlFlow.flow(link, true);

        Thread.sleep(10000);
    }

}