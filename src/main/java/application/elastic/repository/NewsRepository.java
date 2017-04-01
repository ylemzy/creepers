package application.elastic.repository;

import application.elastic.base.CustomElasticsearchRepository;
import application.fetch.News;

/**
 * Created by huangzebin on 2017/3/3.
 */
public interface NewsRepository extends CustomElasticsearchRepository<News, String> {
}
