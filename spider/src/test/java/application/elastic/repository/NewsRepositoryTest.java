package application.elastic.repository;

import application.fetch.News;
import util.JsonHelper;
import org.elasticsearch.client.Client;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by huangzebin on 2017/4/6.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NewsRepositoryTest {

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    Client client;

    @Test
    public void testSave(){
        News news = new News();
        news.setId("20170411xpack");
        news.setUrl("http://www.sina.com");
        news.setWebId("sina");

        newsRepository.save(news);

        News one = newsRepository.findOne("20170411xpack");
        System.out.println(JsonHelper.toJSON(one));
    }
}