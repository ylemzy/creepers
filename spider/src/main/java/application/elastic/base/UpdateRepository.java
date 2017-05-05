package application.elastic.base;

import org.springframework.data.repository.NoRepositoryBean;

/**
 * Created by huangzebin on 2017/2/20.
 */
@NoRepositoryBean
public interface UpdateRepository<T> {

    <S extends T> Iterable<S> update(Iterable<S> var1);

    <S extends T> S update(S var1);
}
