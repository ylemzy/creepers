package elastic;/**
 * Created by huangzebin on 2017/5/5.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

public class Config {
    private static final Logger logger = LogManager.getLogger();

    public ElasticsearchOperations getTemplate() {
        return new ElasticsearchTemplate(nodeBuilder().local(true).node().client());
    }
}
