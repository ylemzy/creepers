package application.elastic;


import application.elastic.document.HostLink;
import application.elastic.document.Link;
import elastic.batch.BatchSaver;
import org.springframework.stereotype.Service;

/**
 * Created by huangzebin on 2017/3/3.
 */
@Service
public class HostLinkBatchSaver extends BatchSaver<HostLinkRepository, HostLink> {

}
