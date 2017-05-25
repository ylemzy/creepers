package application.elastic;

import application.elastic.document.PageLink;
import elastic.repository.CustomElasticsearchRepository;

/**
 * Created by huangzebin on 2017/3/3.
 */
public interface PageLinkRepository extends CustomElasticsearchRepository<PageLink, String> {
}
