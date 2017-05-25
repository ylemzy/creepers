package application.fetch;/**
 * Created by huangzebin on 2017/5/25.
 */

import application.elastic.document.Link;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface UrlFlow {
    void flow(Link url, boolean wait);
}
