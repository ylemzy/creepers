package http;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

/**
 * Created by huangzebin on 2017/5/17.
 */
public class UrlMakerTest {
    private static final Logger logger = LogManager.getLogger();

    @Test
    public void getUri() throws Exception {
        String uri = UrlMaker.make("http://news.xinhuanet.com:9912/info/2014-06/19/c_133418828.htm").getUri();
        logger.info("-->> {}", uri);
    }
}