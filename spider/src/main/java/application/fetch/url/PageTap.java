package application.fetch.url;/**
 * Created by huangzebin on 2017/5/9.
 */

import application.elastic.PageLinkBatchSaver;
import application.elastic.document.Link;
import application.elastic.document.PageLink;
import application.fetch.RxUrlBase;
import http.UrlMaker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;

@Component
public class PageTap extends RxUrlBase{
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    PageLinkBatchSaver pageLinkBatchSaver;

    public Link savePageLink(Link link) throws MalformedURLException {
        PageLink pageLink = new PageLink();
        pageLink.setUrl(link.getUrl());
        pageLink.setFromUrl(link.getFromUrl());
        pageLinkBatchSaver.save(pageLink);
        return link;
    }
}
