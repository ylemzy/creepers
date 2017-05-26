package application.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import util.JsonHelper;

/**
 * Created by huangzebin on 2017/5/26.
 */


public class UserAgentHelperTest {
    @Test
    public void getUserAgent() throws Exception {

        for (int i = 0;  i < 3; ++i){
            UserAgent userAgent = UserAgentHelper.getUserAgent();
            logger.info(JsonHelper.toJSON(userAgent));
        }


    }

    private static final Logger logger = LogManager.getLogger();


}