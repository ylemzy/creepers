package elastic.repository;

import util.JsonHelper;
import elastic.batch.ScrollCallback;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.Aggregations;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ScrolledPage;
import org.springframework.data.elasticsearch.core.mapping.ElasticsearchPersistentEntity;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.repository.support.AbstractElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchEntityInformation;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by huangzebin on 2017/2/20.
 */
public class CustomElasticsearchRepositoryImpl<T> extends AbstractElasticsearchRepository<T, String>
        implements CustomElasticsearchRepository<T, String> {

    public CustomElasticsearchRepositoryImpl() {
        super();
    }

    public CustomElasticsearchRepositoryImpl(ElasticsearchOperations elasticsearchOperations) {
        super(elasticsearchOperations);
    }

    public CustomElasticsearchRepositoryImpl(ElasticsearchEntityInformation<T, String> metadata, ElasticsearchOperations elasticsearchOperations) {
        super(metadata, elasticsearchOperations);

    }

    @Override
    protected String stringIdRepresentation(String id) {
        return id;
    }


    @Override
    public <S extends T> Iterable<S> update(Iterable<S> entities) {
        Assert.notNull(entities, "Cannot insert \'null\' as a List.");
        ArrayList queries = new ArrayList();
        Iterator var3 = entities.iterator();

        while(var3.hasNext()) {
            Object s = var3.next();
            queries.add(this.createUpdateQuery((T)s));
        }

        this.elasticsearchOperations.bulkUpdate(queries);
        this.elasticsearchOperations.refresh(this.entityInformation.getIndexName());
        return entities;
    }

    @Override
    public <S extends T> S update(S entity) {
        Assert.notNull(entity, "Cannot save \'null\' entity.");
        this.elasticsearchOperations.update(this.createUpdateQuery(entity));
        this.elasticsearchOperations.refresh(this.entityInformation.getIndexName());
        return entity;
    }


    private UpdateQuery createUpdateQuery(T entity) {
        UpdateQuery query = new UpdateQuery();
        query.setDoUpsert(true);
        query.setId(this.stringIdRepresentation(this.extractIdFromBean(entity)));

        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.docAsUpsert(true);
        updateRequest.doc(JsonHelper.toJSON(entity));

        query.setClazz(entity.getClass());
        query.setUpdateRequest(updateRequest);
        return query;
    }


    @Override
    public void scroll(SearchQuery searchQuery, long scrollTimeInMillis, boolean noFields, ScrollCallback scrollCallback){
        Assert.notNull(scrollCallback, "No call back function defined for Scroll");

        ElasticsearchPersistentEntity persistentEntity = elasticsearchOperations.getPersistentEntityFor(this.getEntityClass());
        searchQuery.addIndices(persistentEntity.getIndexName());
        searchQuery.addTypes(persistentEntity.getIndexType());
        ScrolledPage page = (ScrolledPage)elasticsearchOperations.startScroll(scrollTimeInMillis, searchQuery, this.getEntityClass());
        while (page.hasContent()) {
            scrollCallback.call(page);
            page = (ScrolledPage)elasticsearchOperations.continueScroll(page.getScrollId(), scrollTimeInMillis, persistentEntity.getType());
        }
        elasticsearchOperations.clearScroll(page.getScrollId());
    }

    @Override
    public Aggregations aggregation(QueryBuilder queryBuilder, AbstractAggregationBuilder aggregation){
        ElasticsearchPersistentEntity persistentEntity = elasticsearchOperations.getPersistentEntityFor(this.getEntityClass());
        SearchResponse searchResponse = elasticsearchOperations.getClient()
                .prepareSearch(persistentEntity.getIndexName())
                .setTypes(persistentEntity.getIndexType())
                .setQuery(queryBuilder)
                .addAggregation(aggregation)
                .execute().actionGet();
        return searchResponse.getAggregations();
    }
}
