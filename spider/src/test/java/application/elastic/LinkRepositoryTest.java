package application.elastic;

import application.elastic.document.Link;
import application.fetch.url.UrlType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by J on 5/7/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LinkRepositoryTest {

    @Autowired
    UrlRepository urlRepository;

    @Test
    public void test(){
        Link link = new Link("http://www.baidu.com");
        link.setType(UrlType.FIX);
        urlRepository.save(link);
    }

}