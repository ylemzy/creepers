package application.elastic.repository;

import application.elastic.base.BatchSaver;
import application.fetch.News;
import org.springframework.stereotype.Service;

/**
 * Created by huangzebin on 2017/3/3.
 */
@Service
public class NewsBatchSaver extends BatchSaver<NewsRepository, News> {
}
