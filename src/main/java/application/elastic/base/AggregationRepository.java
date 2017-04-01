package application.elastic.base;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.Aggregations;

/**
 * Created by huangzebin on 2017/2/24.
 */
public interface AggregationRepository {

    Aggregations aggregation(QueryBuilder queryBuilder, AbstractAggregationBuilder aggregation);
}
