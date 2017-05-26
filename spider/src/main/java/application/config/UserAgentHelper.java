package application.config;/**
 * Created by huangzebin on 2017/5/26.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Random;

public class UserAgentHelper {
    private static final Logger logger = LogManager.getLogger();

    private static List<UserAgent> data;
    private static Random random = new Random();

    public static UserAgent getUserAgent(){
        if (data == null){
            data = ConfigLoader.getData(UserAgent.class);
        }

        return data.get(random.nextInt(data.size()));
    }
}
