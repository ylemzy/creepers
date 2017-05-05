package application.elastic.base;

import org.springframework.data.domain.Page;

/**
 * Created by huangzebin on 2017/2/23.
 */
public interface ScrollCallback<R> {
    void call(Page<R> page);
}
