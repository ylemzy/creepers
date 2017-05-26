package application.service;

import application.elastic.HostLinkRepository;
import application.elastic.document.HostLink;
import application.elastic.document.Link;
import application.fetch.UrlType;
import application.fetch.url.CategoryFlow;
import application.fetch.url.RawUrlFlow;
import application.kafka.ProducerPipeline;
import elastic.batch.ScrollCallback;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

/**
 * Created by J on 5/7/2017.
 */
@Service
public class DigService {

    @Autowired
    RawUrlFlow rawUrlFlow;

    @Autowired
    ProducerPipeline producerPipeline;

    @Autowired
    HostLinkRepository hostLinkRepository;

    public void dig(String url){
        Link link = new Link(url);
        rawUrlFlow.flow(link, false);
    }

    public void fetchCategory(){
        NativeSearchQuery build = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchAllQuery())
                .build();
        hostLinkRepository.scroll(build, 5000L, false, new ScrollCallback<HostLink>() {
            @Override
            public void call(Page<HostLink> page) {
                page.forEach(item ->{
                    Link link = new Link(item.getHost(), UrlType.CATEGORY);
                    producerPipeline.send(link);
                });
            }
        });
    }

}
