package application.fetch.filter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by huangzebin on 2017/4/21.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlFilterTest {

    @Autowired
    UrlFilter urlFilter;

    @Test
    public void test(){
        Url url = new Url("http://www.baidu.com/index/test?a=1&b=3");
        urlFilter.filter(url);
    }

}