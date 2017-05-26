package application.elastic.document;/**
 * Created by huangzebin on 2017/5/9.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.sql.Timestamp;

@Document(indexName = "page-link", type = "1")
public class PageLink {
    private static final Logger logger = LogManager.getLogger();

    @Id
    private String url;

    private String host;

    private String fromUrl;

    private Timestamp fetchedTime;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Timestamp getFetchedTime() {
        return fetchedTime;
    }

    public void setFetchedTime(Timestamp fetchedTime) {
        this.fetchedTime = fetchedTime;
    }

    public String getFromUrl() {
        return fromUrl;
    }

    public void setFromUrl(String fromUrl) {
        this.fromUrl = fromUrl;
    }
}
