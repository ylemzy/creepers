package application.fetch.http;/**
 * Created by huangzebin on 2017/5/26.
 */

import application.config.UserAgentHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Rest {
    private static final Logger logger = LogManager.getLogger();

    public static Document get(String url) throws IOException {
        Connection connect = Jsoup.connect(url)
                .header("User-Agent", UserAgentHelper.getUserAgent().getValue())
                .timeout(6000);
        return connect.get();
    }
}
