package application.elastic.base;

import org.springframework.data.elasticsearch.core.query.SearchQuery;

/**
 * Created by huangzebin on 2017/2/20.
 */
public interface ScrollRepository {
    void scroll(SearchQuery searchQuery, long scrollTimeInMillis, boolean noFields,
                ScrollCallback scrollCallback);
}
