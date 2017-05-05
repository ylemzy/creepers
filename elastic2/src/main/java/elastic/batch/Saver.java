package elastic.batch;

import elastic.repository.CustomElasticsearchRepository;

/**
 * Created by huangzebin on 2017/3/3.
 */
public interface Saver<R extends CustomElasticsearchRepository, T> {

    void save(T data);

    void finish();
}
