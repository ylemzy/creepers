package application.elastic.repository;


import application.elastic.base.CustomElasticsearchRepository;
import application.elastic.document.RuleProduct;

/**
 * Created by huangzebin on 2017/3/8.
 */
public interface EsRuleProductRepository extends CustomElasticsearchRepository<RuleProduct, String> {
}
