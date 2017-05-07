package application.kafka;

import application.elastic.document.Url;
import application.fetch.request.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JsonHelper;

import javax.annotation.PostConstruct;

/**
 * Created by J on 5/7/2017.
 */
@Component
public class ProducerPipeline {
    private static final Logger logger = LogManager.getLogger();
    private static ProducerPipeline producerPipeline;

    @PostConstruct
    public void init(){
        producerPipeline = this;
    }

    public static ProducerPipeline get(){
        return producerPipeline;
    }

    @Autowired
    RequestProducer requestProducer;

    @Autowired
    UrlProducer urlProducer;

    private void log(String type, Object sendObject){
        //logger.info("Send {}:{} -> {}", type, Thread.currentThread().getName(), JsonHelper.toJSON(sendObject));
    }


    public void send(Request request){
        requestProducer.sendRequest(request);
        log("request",request);
    }

    public void send(Url url){
        urlProducer.sendUrl(url);
        log("url", url);
    }

}
