package application.service;

import application.elastic.NewsRepository;
import application.elastic.document.News;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Created by huangzebin on 2017/3/3.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NewsServiceTest {

    @Autowired
    NewsRepository newsRepository;

    @Test
    public void test(){
        News news = new News();
        news.setWebId("11ffddfdfdfdf_test");
        newsRepository.save(news);
    }

}