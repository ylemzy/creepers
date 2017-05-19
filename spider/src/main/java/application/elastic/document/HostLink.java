package application.elastic.document;/**
 * Created by huangzebin on 2017/5/9.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.sql.Timestamp;

@Document(indexName = "host-link")
public class HostLink {
    private static final Logger logger = LogManager.getLogger();

    @Id
    private String host;

    private boolean forbid;

    private String forbidReason;

    private String forbidSelectorScript;

    private Timestamp fetchedTime;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public boolean isForbid() {
        return forbid;
    }

    public void setForbid(boolean forbid) {
        this.forbid = forbid;
    }

    public String getForbidReason() {
        return forbidReason;
    }

    public void setForbidReason(String forbidReason) {
        this.forbidReason = forbidReason;
    }

    public String getForbidSelectorScript() {
        return forbidSelectorScript;
    }

    public void setForbidSelectorScript(String forbidSelectorScript) {
        this.forbidSelectorScript = forbidSelectorScript;
    }

    public Timestamp getFetchedTime() {
        return fetchedTime;
    }

    public void setFetchedTime(Timestamp fetchedTime) {
        this.fetchedTime = fetchedTime;
    }
}
