package application.elastic;

import application.elastic.document.Link;
import elastic.repository.CustomElasticsearchRepository;

/**
 * Created by huangzebin on 2017/3/3.
 */
public interface UrlRepository extends CustomElasticsearchRepository<Link, String> {
}
