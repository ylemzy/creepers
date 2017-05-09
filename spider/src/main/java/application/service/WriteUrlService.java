package application.service;

import application.elastic.UrlBatchSaver;
import application.elastic.document.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huangzebin on 2017/3/3.
 */
@Service
public class WriteUrlService {

    @Autowired
    UrlBatchSaver saver;

    public void write(Link link){
        saver.save(link);
    }
}
