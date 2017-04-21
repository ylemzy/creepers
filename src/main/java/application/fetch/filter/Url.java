package application.fetch.filter;/**
 * Created by huangzebin on 2017/4/19.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Url {
    private static final Logger logger = LogManager.getLogger();

    private String url;

    public Url(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
