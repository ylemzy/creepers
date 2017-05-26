package application.elastic.document;/**
 * Created by huangzebin on 2017/4/19.
 */

import application.fetch.UrlType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import util.HashUtil;

@Document(indexName = "row-link", type = "1")
public class Link {
    private static final Logger logger = LogManager.getLogger();

    private String url;

    private String fromUrl;

    private UrlType type;

    @Id
    private String id;

    public Link(){}

    public Link(String url) {
        this.url = url;
        this.id = HashUtil.MD5(url);
    }

    public Link(String url, UrlType type) {
        this.url = url;
        this.type = type;
        this.id = HashUtil.MD5(url);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UrlType getType() {
        return type;
    }

    public void setType(UrlType type) {
        this.type = type;
    }

    public String getFromUrl() {
        return fromUrl;
    }

    public void setFromUrl(String fromUrl) {
        this.fromUrl = fromUrl;
    }
}
