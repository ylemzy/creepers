package application;

import util.JsonHelper;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.health.ClusterIndexHealth;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * Created by huangzebin on 2017/4/5.
 */
public class ElasticsearchConfigTest {

    @Test
    public void testClient() throws UnknownHostException {
        ClusterHealthResponse clusterHealthResponse =
                getClient().admin().cluster().health(new ClusterHealthRequest("")).actionGet();
        Map<String, ClusterIndexHealth> indices = clusterHealthResponse.getIndices();
        System.out.println(JsonHelper.toJSON(indices));
    }

    public static TransportClient getClient() throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name", "htbaobao").build();
        return new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.1.88"), 9300));
    }



}