package application.elastic;

import application.fetch.News;
import elastic.repository.CustomElasticsearchRepository;

/**
 * Created by huangzebin on 2017/3/3.
 */
public interface NewsRepository extends CustomElasticsearchRepository<News, String> {
}
