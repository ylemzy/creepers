package application.elastic.base;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Created by huangzebin on 2017/2/20.
 */
@NoRepositoryBean
public interface CustomElasticsearchRepository<T, ID extends Serializable>
        extends ElasticsearchRepository<T, ID>,
        UpdateRepository<T>,
        ScrollRepository,
        AggregationRepository{
}
