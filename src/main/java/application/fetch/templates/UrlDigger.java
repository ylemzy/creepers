package application.fetch.templates;/**
 * Created by huangzebin on 2017/4/19.
 */

import application.fetch.filter.Url;
import application.fetch.filter.UrlFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class UrlDigger {
    private static final Logger logger = LogManager.getLogger();

    String url;

    public UrlDigger(String url){
        this.url = url;
    }

    public void dig() throws IOException {
        Document document = Jsoup.connect(url).get();
        Elements urls = document.getElementsByTag("a");
        urls.forEach(a->{
            UrlFilter.get().filter(new Url(a.absUrl("href")));
        });
    }
}
