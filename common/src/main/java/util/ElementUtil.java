package util;/**
 * Created by huangzebin on 2017/5/22.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.Assert;

import java.util.*;

public class ElementUtil {
    private static final Logger logger = LogManager.getLogger();

    public static Element findCommonParent(Element a, Element b) {
        Assert.notNull(a);
        Assert.notNull(b);

        Element parent = a;
        while (parent != null) {
            Element bParent = b;
            while (bParent != null) {
                if (bParent == parent)
                    return parent;
                bParent = bParent.parent();
            }

            parent = parent.parent();
        }
        return null;
    }

    public static class CostFinder {

        static class ElementCost {
            List<Integer> siblings = new ArrayList<>();
            Element element;

            public ElementCost(Element element) {
                this.element = element;
            }

            public List<Integer> getSiblings() {
                return siblings;
            }

            public void setSiblings(List<Integer> siblings) {
                this.siblings = siblings;
            }

            public Element getElement() {
                return element;
            }

            public void setElement(Element element) {
                this.element = element;
            }

            public void addSibling(int sibling) {
                siblings.add(sibling);
            }

            public int compare(ElementCost elementCost) {
                List<Integer> other = elementCost.getSiblings();
                for (int i = 0; i < this.siblings.size() && i < other.size(); ++i) {
                    int c = this.siblings.get(i) - other.get(i);
                    if (c != 0) {
                        logger.info("{} - {} = {} ", this.siblings, other, c);
                        return c;
                    }
                }
                return 0;
            }
        }

        public static Element maxCost(Elements elements) {
            ElementCost min = null;
            for (Element element : elements) {
                ElementCost cost = toCost(findParentPath(element));
                if (min == null) {
                    min = cost;
                } else if (min.compare(cost) > 0) {
                    min = cost;
                }
            }
            if (min == null)
                return null;
            return min.getElement();
        }

        public static Elements findParentPath(Element element) {
            Elements elements = new Elements();
            Element tmp = element;
            while (tmp != null) {
                elements.add(tmp);
                tmp = tmp.parent();
            }
            return elements;
        }

        public static ElementCost toCost(Elements elements) {
            ElementCost elementCost = new ElementCost(elements.first());
            for (int i = elements.size() - 1; i >= 0; --i) {
                elementCost.addSibling(elements.get(i).elementSiblingIndex());
            }

            return elementCost;
        }
    }

    public static class ModuleFinder {

        public static boolean sameBlock(Element left, Element right) {
            if (!left.tagName().equals(right.tagName()))
                return false;

            Elements leftChildren = left.children();
            Elements rightChildren = right.children();

            if (leftChildren.size() != rightChildren.size())
                return false;

            for (int i = 0; i < leftChildren.size(); ++i) {
                if (!sameBlock(leftChildren.get(i), rightChildren.get(i))) {
                    return false;
                }
            }

            return true;
        }

        public static boolean isModule(Element element){
            Elements children = element.children();
            if (children.size() < 3)
                return false;

            Element first = children.first();
            Element last = children.last();

            for (int i = 1; i < children.size(); ++i){
                if (!first.tagName().equals(children.get(i).tagName())){
                    return false;
                }
            }

            if (sameBlock(first, last)) {
                return true;
            }

            return false;
        }

        public static Element findModuleFromNode(Element left, Element right) {
            Element commonParent = ElementUtil.findCommonParent(left, right);

            if (isModule(commonParent))
                return commonParent;
            return null;
        }

        public static Map<Element, Set<Element>> aggModuleInElement(Elements elements){

            Map<Element, Set<Element>> agg = new HashMap<>();

            Map<Element, Set<Element>> resultAgg = new HashMap<>();
            for (int i = 1; i < elements.size(); ++i) {
                Element commonParent = ElementUtil.findCommonParent(elements.get(i - 1), elements.get(i));
                Set set = agg.get(commonParent);
                if (set == null) {
                    set = new HashSet();
                }
                set.add(elements.get(i - 1));
                set.add(elements.get(i));
                agg.put(commonParent, set);
            }

            agg.forEach( (k, v) ->{

                if (v.size() >= 3 && isModule(k)){
                    resultAgg.put(k, v);
                }
            });

            return resultAgg;
        }
    }

    public static class PageLogic{
        public boolean isPage(Map<Element, Set<Element>> module){
            for (Set<Element> elements : module.values()) {
                if (elements.size() > 20){
                    return true;
                }
            }
            return false;
        }
    }
}
