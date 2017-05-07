package application.kafka;

import application.fetch.request.AbstractTemplate;
import application.fetch.request.Request;
import application.fetch.request.Response;
import application.fetch.request.TemplateLoader;
import application.service.WriteTemplateService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import util.JsonHelper;

/**
 * Created by J on 5/7/2017.
 */
@Component
public class RequestConsumer {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    WriteTemplateService writeTemplateService;

    @KafkaListener(topics = "request")
    public void processMessage(String content) {
        Request request = JsonHelper.toObject(content, Request.class);

        if (request == null){
            return;
        }

        try {
            AbstractTemplate template = (AbstractTemplate) TemplateLoader.getTemplate(request.getWebId());
            Response response = template.process(request);
            writeTemplateService.write(response);
        } catch (Exception e) {
            logger.error(e, e);
        }
    }
}
