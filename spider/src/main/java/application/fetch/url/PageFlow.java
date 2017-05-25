package application.fetch.url;/**
 * Created by huangzebin on 2017/4/19.
 */

import application.elastic.document.Link;
import application.fetch.UrlFlow;
import io.reactivex.Observable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PageFlow implements UrlFlow{
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private PageTap rxPage;

    @Override
    public void flow(Link url, boolean wait) {
        Observable.just(url)
                .subscribe(link -> rxPage.savePageLink(link),
                        throwable -> rxPage.error(url, throwable));
    }
}
