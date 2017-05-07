package application.elastic;


import application.elastic.document.Url;
import elastic.batch.BatchSaver;
import org.springframework.stereotype.Service;

/**
 * Created by huangzebin on 2017/3/3.
 */
@Service
public class UrlBatchSaver extends BatchSaver<UrlRepository, Url> {

}
