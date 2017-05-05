package application;

/**
 * Created by huangzebin on 2017/4/5.
 */
public class ElasticsearchConfigTest {

    /*@Test
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
    }*/



}