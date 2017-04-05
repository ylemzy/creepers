package application;

import application.elastic.base.CustomElasticsearchRepositoryImpl;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by huangzebin on 2017/2/13.
 */
@Configuration
@EnableElasticsearchRepositories(repositoryBaseClass = CustomElasticsearchRepositoryImpl.class)
public class Config {

    final static String port = "9300";

    @Bean
    public Client getNodeClient() {
        try {
            return new PreBuiltTransportClient(Settings.builder()
                    .build()).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.1.88"), Integer.valueOf(port)));
        } catch (UnknownHostException e) {
            throw new RuntimeException("Unable to connect to localhost cluster ar port " + port);
        }
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchTemplate(getNodeClient());
    }
}
