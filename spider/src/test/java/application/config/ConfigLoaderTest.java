package application.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import util.JsonHelper;

import java.util.List;

/**
 * Created by huangzebin on 2017/5/26.
 */

/*@RunWith(SpringRunner.class)
@SpringBootTest*/
public class ConfigLoaderTest {

    @Test
    public void loadUserAgent() throws Exception {
        List<UserAgent> userAgents = ConfigLoader.loadUserAgent();
        logger.info("{}", JsonHelper.toJSON(userAgents));
    }

    @Test
    public void loadConfig() throws Exception {
        List<UserAgent> userAgents = ConfigLoader.loadConfig(UserAgent.class);
        logger.info("{}", JsonHelper.toJSON(userAgents));
    }

    private static final Logger logger = LogManager.getLogger();


}