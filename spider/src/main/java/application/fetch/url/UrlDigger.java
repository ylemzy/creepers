package application.fetch.url;/**
 * Created by huangzebin on 2017/4/19.
 */

import application.elastic.document.Link;
import io.reactivex.Observable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class UrlDigger {
    private static final Logger logger = LogManager.getLogger();

    String url;



    public UrlDigger(String url) {
        this.url = url;
    }

    public void dig() {

    }

}
