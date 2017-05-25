package application.service;

import application.elastic.document.Link;
import application.fetch.url.RawUrlFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by J on 5/7/2017.
 */
@Service
public class DigService {

    @Autowired
    RawUrlFlow rawUrlFlow;

    public void dig(String url){
        Link link = new Link(url);
        rawUrlFlow.flow(link, false);
    }
}
