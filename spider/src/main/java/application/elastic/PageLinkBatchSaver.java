package application.elastic;


import application.elastic.document.PageLink;
import elastic.batch.BatchSaver;
import org.springframework.stereotype.Service;

/**
 * Created by huangzebin on 2017/3/3.
 */
@Service
public class PageLinkBatchSaver extends BatchSaver<PageLinkRepository, PageLink> {

}
