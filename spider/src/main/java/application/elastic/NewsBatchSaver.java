package application.elastic;


import application.fetch.News;
import elastic.batch.BatchSaver;
import org.springframework.stereotype.Service;

/**
 * Created by huangzebin on 2017/3/3.
 */
@Service
public class NewsBatchSaver extends BatchSaver<NewsRepository, News> {
}
