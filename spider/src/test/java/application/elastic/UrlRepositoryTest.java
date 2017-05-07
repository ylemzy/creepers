package application.elastic;

import application.elastic.document.Url;
import application.fetch.url.UrlType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by J on 5/7/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlRepositoryTest {

    @Autowired
    UrlRepository urlRepository;

    @Test
    public void test(){
        Url url = new Url("http://www.baidu.com");
        url.setType(UrlType.FIX);
        urlRepository.save(url);
    }

}