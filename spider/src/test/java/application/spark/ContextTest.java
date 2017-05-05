package application.spark;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

/**
 * Created by huangzebin on 2017/4/12.
 */
public class ContextTest {

    private static final Logger logger = LogManager.getLogger();
    Context context = new Context();

    @Test
    public void parallelizedCollection(){
        Integer integer = context.conf().parallelizedCollection();
        logger.info("parallelizedCollection = {}", integer);
    }

    @Test
    public void externalDatasets(){
        Integer integer = context.conf().externalDatasets();
        logger.info("parallelizedCollection = {}", integer);//55135757
    }

}