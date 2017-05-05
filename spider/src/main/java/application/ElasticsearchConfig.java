package application;

import elastic.Config;
import elastic.repository.CustomElasticsearchRepositoryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

/**
 * Created by huangzebin on 2017/2/13.
 */
@Configuration
@EnableElasticsearchRepositories(repositoryBaseClass = CustomElasticsearchRepositoryImpl.class)
public class ElasticsearchConfig {
    private static final Logger logger = LogManager.getLogger();
/*    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchTemplate(nodeBuilder().local(true).node().client());
    }   */

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new Config().getTemplate();
    }


   /* final int  port = 9300;
    final String host = "localhost";

    @Value("${xpack.security.user}")
    String xpackUser;

    @Bean
    public Client xpackClient() throws UnknownHostException {
        PreBuiltXPackTransportClient clientBuilder = new PreBuiltXPackTransportClient(Settings.builder()
                .put("cluster.name", "htbaobao")
                .put("xpack.security.user", xpackUser)
                .build());

        TransportClient client = clientBuilder
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));

        return client;
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() throws UnknownHostException {
        return new ElasticsearchTemplate(xpackClient());
    }*/
}
