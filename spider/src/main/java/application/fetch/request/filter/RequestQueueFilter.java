package application.fetch.request.filter;

import application.fetch.request.Request;
import application.kafka.ProducerPipeline;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by huangzebin on 2017/3/7.
 */
public class RequestQueueFilter extends AbstractFilter{

    private static final Logger logger = LogManager.getLogger();
    @Override
    public void filter(Request request) {
        super.filter(request);
        ProducerPipeline.get().send(request);
    }
}
