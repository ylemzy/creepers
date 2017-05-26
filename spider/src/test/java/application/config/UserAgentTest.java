package application.config;

import org.apache.catalina.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import util.JsonHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangzebin on 2017/5/26.
 */


public class UserAgentTest {
    private static final Logger logger = LogManager.getLogger();

    @Test
    public void getUserAgent() throws IOException {
        Document document = Jsoup.connect("http://useragentstring.com/pages/useragentstring.php?name=Chrome")
                .timeout(9000).get();

        Elements a = document.getElementsByTag("a");

        List<UserAgent> userAgents = new ArrayList<>();
        a.forEach(item ->{
            String href = item.absUrl("href");
            if(href.contains("/index.php?")){
                try {
                    UserAgent okUserAgent = getOkUserAgent(href);
                    if (okUserAgent != null){
                        userAgents.add(okUserAgent);
                    }
                    //logger.info("-->> {}", getOkUserAgent(href));
                } catch (IOException e) {
                    logger.error(e, e);
                }
            }
        });
        logger.info(JsonHelper.toJSON(userAgents));
    }

    public UserAgent getOkUserAgent(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        Elements elementsContainingOwnText = document.getElementsContainingOwnText("Operating System");
        if (elementsContainingOwnText != null){
            Elements a = elementsContainingOwnText.first().getElementsByTag("a");
            Elements elementsByTag = document.getElementsByTag("textarea ");
            UserAgent userAgent = new UserAgent();
            userAgent.setValue(elementsByTag.text());
            userAgent.setSystem(a.text());
            return userAgent;
        }
        return null;
    }

}