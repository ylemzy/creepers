package application.elastic;


import application.elastic.document.RuleProduct;
import elastic.repository.CustomElasticsearchRepository;

/**
 * Created by huangzebin on 2017/3/8.
 */
public interface EsRuleProductRepository extends CustomElasticsearchRepository<RuleProduct, String> {
}
