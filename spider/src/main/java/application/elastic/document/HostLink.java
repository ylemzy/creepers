package application.elastic.document;/**
 * Created by huangzebin on 2017/5/9.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
@Document(indexName = "host-link")
public class HostLink {
    private static final Logger logger = LogManager.getLogger();

    @Id
    private String host;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
