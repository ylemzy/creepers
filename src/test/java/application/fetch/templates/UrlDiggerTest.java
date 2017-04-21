package application.fetch.templates;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * Created by huangzebin on 2017/4/21.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlDiggerTest {

    @Test
    public void dig() throws IOException {
        new UrlDigger("http://www.toutiao.com/").dig();
    }

}