package application.service;

import application.elastic.document.Link;
import application.fetch.url.UrlDigger;
import application.fetch.url.UrlFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by J on 5/7/2017.
 */
@Service
public class DigService {

    @Autowired
    UrlFlow urlFlow;

    public void dig(String url){
        Link link = new Link(url);
        urlFlow.flow(link, false);
    }
}
