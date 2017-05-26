package application.config;


import com.google.common.base.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.JsonHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangzebin on 2017/3/8.
 */
public class ConfigLoader {
    private static final Logger logger = LogManager.getLogger();

    private static Map<Class, String> source;

    private static Map<Class, List> data;

    static {
        source = new HashMap<>();
        source.put(UserAgent.class, "/conf/user_agent");

        data = new HashMap<>();
    }

    public static List<UserAgent> loadUserAgent() throws IOException {
        InputStream ruleStream = ConfigLoader.class.getResourceAsStream("/conf/user_agent");
        String s = IOUtils.toString(ruleStream, Charsets.UTF_8);
        List<UserAgent> sourceRecommends = JsonHelper.toList(s, UserAgent.class);
        return sourceRecommends;
    }

    public static <T> List<T> loadConfig(Class<T> clazz) throws IOException{
        InputStream ruleStream = ConfigLoader.class.getResourceAsStream(source.get(clazz));
        String s = IOUtils.toString(ruleStream, Charsets.UTF_8);
        List<T> result = JsonHelper.toList(s, clazz);
        data.put(clazz, result);
        return result;
    }

    public static <T> List<T> getData(Class<T> clazz){
        List<T> list = data.get(clazz);
        if (list == null){
            try {
                list = loadConfig(clazz);
            } catch (IOException e) {
                logger.error(e, e);
            }
            data.put(clazz, list);
        }
        return list;
    }

}
