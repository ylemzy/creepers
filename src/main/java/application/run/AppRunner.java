package application.run;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by huangzebin on 2017/3/2.
 */
@Component
public class AppRunner implements CommandLineRunner{
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    RequestQueueRunner requestQueueRunner;

    @Override
    public void run(String... strings) throws Exception {
        for (int i = 0; i < 1; ++i){
            logger.info("Runner {} is starting", i);
            requestQueueRunner.run();
            logger.info("Runner {} is started", i);
        }
    }
}
