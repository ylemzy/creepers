package elastic;/**
 * Created by huangzebin on 2017/5/5.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Es5Config {
    private static final Logger logger = LogManager.getLogger();

    int  port = 9300;
    String host = "localhost";
    String clusterName;
    String xpackUser;

    public Client xpackClient() throws UnknownHostException {
        PreBuiltXPackTransportClient clientBuilder = new PreBuiltXPackTransportClient(Settings.builder()
                .put("cluster.name", "htbaobao")
                .put("xpack.security.user", xpackUser)
                .build());

        TransportClient client = clientBuilder
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));

        return client;
    }

    public ElasticsearchOperations getTemplate() throws UnknownHostException {
        return new ElasticsearchTemplate(xpackClient());
    }
}
