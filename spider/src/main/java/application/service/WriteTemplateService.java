package application.service;

import application.elastic.NewsBatchSaver;
import application.elastic.document.News;
import application.fetch.request.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huangzebin on 2017/3/3.
 */
@Service
public class WriteTemplateService {

    /*@Autowired
    NewsRepository repository;*/

    @Autowired
    NewsBatchSaver saver;

    public void write(Response response){
        if (response.getBody() == null || !(response.getBody() instanceof News)){
            return;
        }

        News news = (News) response.getBody();
        saver.save(news);
    }
}
