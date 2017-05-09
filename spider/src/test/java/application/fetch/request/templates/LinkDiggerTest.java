package application.fetch.request.templates;

import application.fetch.url.UrlDigger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by huangzebin on 2017/4/21.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class LinkDiggerTest {

    @Test
    public void dig() throws IOException, InterruptedException {
        new UrlDigger("http://www.toutiao.com/").dig();
        Thread.sleep(TimeUnit.HOURS.toMillis(1));
    }

}