package util;/**
 * Created by huangzebin on 2017/5/22.
 */

import org.apache.commons.lang.StringUtils;
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

        public static class ModuleValue {
            int totalLeftNode;
            int totalRightNode;
            int totalLeftEmptyNode;
            int totalRightEmptyNode;
            int totalDiffTagNode;
            boolean emptyLeftRoot = false;
            boolean emptyRightRoot = false;

            public ModuleValue() {
            }

            public void add(ModuleValue moduleValue) {
                this.totalLeftNode += moduleValue.totalLeftNode;
                this.totalRightNode += moduleValue.totalRightNode;
                this.totalLeftEmptyNode += moduleValue.totalLeftEmptyNode;
                this.totalRightEmptyNode += moduleValue.totalRightEmptyNode;
                this.totalDiffTagNode += moduleValue.totalDiffTagNode;
            }

            public boolean similar() {
                return totalDiffTagNode == 0;
            }

            public double similar(double total, double empty, double diff) {
                return empty / total + diff / total;
            }

            public int getTotalLeftNode() {
                return totalLeftNode;
            }

            public void setTotalLeftNode(int totalLeftNode) {
                this.totalLeftNode = totalLeftNode;
            }

            public int getTotalRightNode() {
                return totalRightNode;
            }

            public void setTotalRightNode(int totalRightNode) {
                this.totalRightNode = totalRightNode;
            }

            public int getTotalLeftEmptyNode() {
                return totalLeftEmptyNode;
            }

            public void setTotalLeftEmptyNode(int totalLeftEmptyNode) {
                this.totalLeftEmptyNode = totalLeftEmptyNode;
            }

            public int getTotalRightEmptyNode() {
                return totalRightEmptyNode;
            }

            public void setTotalRightEmptyNode(int totalRightEmptyNode) {
                this.totalRightEmptyNode = totalRightEmptyNode;
            }

            public int getTotalDiffTagNode() {
                return totalDiffTagNode;
            }

            public void setTotalDiffTagNode(int totalDiffTagNode) {
                this.totalDiffTagNode = totalDiffTagNode;
            }

            public boolean isEmptyLeftRoot() {
                return emptyLeftRoot;
            }

            public void setEmptyLeftRoot(boolean emptyLeftRoot) {
                this.emptyLeftRoot = emptyLeftRoot;
            }

            public boolean isEmptyRightRoot() {
                return emptyRightRoot;
            }

            public void setEmptyRightRoot(boolean emptyRightRoot) {
                this.emptyRightRoot = emptyRightRoot;
            }
        }

        public static boolean isEmpty(Element node) {
            return node == null || StringUtils.isEmpty(node.text());
        }

        public static ModuleValue blockSimilar(Element left, Element right) {

            logger.debug("compare {}>{}-{}, {}>{}-{}", left.tagName(), left.classNames().toString(), left.text(),
                    right.tagName(), right.classNames().toString(), right.text());
            ModuleValue moduleValue = new ModuleValue();

            if (isEmpty(left) && isEmpty(right)) {
                moduleValue.emptyLeftRoot = true;
                moduleValue.emptyRightRoot = true;
                return moduleValue;
            }

            if (isEmpty(left) && !isEmpty(right)) {
                moduleValue.totalLeftEmptyNode = 1;
                moduleValue.totalRightNode = 1;
                moduleValue.emptyLeftRoot = true;
                return moduleValue;
            } else if (!isEmpty(left) && isEmpty(right)) {
                moduleValue.totalRightEmptyNode = 1;
                moduleValue.totalLeftNode = 1;
                moduleValue.emptyRightRoot = true;
                return moduleValue;
            }

            moduleValue.totalLeftNode = 1;
            moduleValue.totalRightNode = 1;

            if (!left.tagName().equals(right.tagName())) {
                moduleValue.totalLeftNode = 1;
                moduleValue.totalRightNode = 1;
                moduleValue.totalDiffTagNode = 1;
                return moduleValue;
            }


            Elements leftChildren = left.children();
            Elements rightChildren = right.children();

            for (int i = 0, j = 0; i < leftChildren.size() && j < rightChildren.size(); ) {
                ModuleValue childModule = blockSimilar(leftChildren.get(i), rightChildren.get(j));

                if (childModule.emptyLeftRoot == false && childModule.emptyRightRoot == false) {
                    ++i;
                    ++j;
                }

                if (childModule.emptyLeftRoot) {
                    ++i;
                }

                if (childModule.emptyRightRoot) {
                    ++j;
                }
                //logger.info("value = {}", JsonHelper.toJSON(childModule));

                moduleValue.add(childModule);
            }

            return moduleValue;
        }

        public static boolean isChildrenSimilar(Element element) {

            Elements children = new Elements();
            element.children().forEach(item -> {
                if (!isEmpty(item)) {
                    children.add(item);
                }
            });

            //Elements children = element.children();

            Element first = children.first();
            Element last = children.last();

            for (int i = 1; i < children.size(); ++i) {
                if (!first.tagName().equals(children.get(i).tagName())) {
                    return false;
                }
            }

            ModuleValue moduleValue = blockSimilar(first, last);
            logger.debug("similar = {}", JsonHelper.toJSON(moduleValue));
            if (moduleValue.similar()) {
                if (moduleValue.isEmptyLeftRoot()) {
                    return blockSimilar(last, children.get(1)).similar();
                } else if (moduleValue.isEmptyRightRoot()) {
                    return blockSimilar(first, children.get(1)).similar();
                }
                return true;
            }
            return false;
        }


        public static boolean sameBlock(Element left, Element right) {
            if (!left.tagName().equals(right.tagName()))
                return false;

            Elements leftChildren = left.children();
            Elements rightChildren = right.children();

            if (leftChildren.size() != rightChildren.size()) {


                for (int i = 0; i < leftChildren.size(); ++i) {
                    if (leftChildren.get(i).text().length() <= 0) {
                        leftChildren.get(i).remove();
                    }
                }

                for (int i = 0; i < rightChildren.size(); ++i) {
                    if (rightChildren.get(i).text().length() <= 0) {
                        rightChildren.get(i).remove();
                    }
                }

                if (leftChildren.size() != rightChildren.size())
                    return false;
            }

            for (int i = 0; i < leftChildren.size(); ++i) {
                if (!sameBlock(leftChildren.get(i), rightChildren.get(i))) {
                    return false;
                }
            }

            return true;
        }

        public static boolean isModule(Element element) {
            Elements children = element.children();
            if (children.size() < 3)
                return false;

            Element first = children.first();
            Element last = children.last();

            for (int i = 1; i < children.size(); ++i) {
                if (!first.tagName().equals(children.get(i).tagName())) {
                    return false;
                }
            }

            if (sameBlock(first, last) && sameBlock(first, children.get(1))) {
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

        public static Map<Element, Set<Element>> aggModuleInElement(Elements elements) {
            if (elements.size() < 3) {
                return Collections.emptyMap();
            }

            Map<Element, Set<Element>> agg = new HashMap<>();
            Map<Element, Set<Element>> resultAgg = new HashMap<>();


            Element commonParent1 = ElementUtil.findCommonParent(elements.get(0), elements.get(1));
            for (int i = 1; i + 1 < elements.size(); ++i) {
                Element commonParent2 = ElementUtil.findCommonParent(elements.get(i), elements.get(i + 1));
                if (commonParent1 == commonParent2) {
                    Set set = agg.get(commonParent1);
                    if (set == null) {
                        set = new HashSet();
                    }
                    set.add(elements.get(i - 1));
                    set.add(elements.get(i));
                    set.add(elements.get(i + 1));
                    agg.put(commonParent1, set);
                }
                commonParent1 = commonParent2;
            }


            agg.forEach((k, v) -> {
                v.forEach(item -> {
                    logger.debug("set : {}", item.outerHtml());
                });

                logger.debug("Check {},{} -> {}, size = {}", k.tag().toString(), k.classNames().toString(), k.text(), v.size());
                if (v.size() > 1 && isChildrenSimilar(k)) {
                    resultAgg.put(k, v);
                    logger.info("Save +++++++++++++++++++ ");
                    k.children().forEach(item -> {
                        if (!isEmpty(item))
                            logger.info("++ {}", item.text());
                    });
                } else {
                    logger.debug("Remove ------------------------ {}", k.text());
                }
            });

            return resultAgg;
        }


        public static Map<Element, Set<Element>> aggModuleInElement(Elements elements, Map<Element, Set<Element>> firstAgg){
            Set<Element> firstLevel = new HashSet<>();
            firstAgg.forEach((k, v) -> {
                firstLevel.addAll(v);
            });

            Elements secondLevelElements = new Elements();
            for (Element element : elements) {
                if (!firstLevel.contains(element)){
                    secondLevelElements.add(element);
                }
            }
            Map<Element, Set<Element>> resultAgg = new HashMap<>();
            for (int i = 1; i < secondLevelElements.size(); ++i){
                Element commonParent = ElementUtil.findCommonParent(elements.get(i), elements.get(i - 1));
                Set<Element> set = resultAgg.get(commonParent);
                if (set == null) {
                    set = new HashSet();
                }
                set.add(elements.get(i - 1));
                set.add(elements.get(i));
                resultAgg.put(commonParent, set);
            }

            resultAgg.forEach((k, v) -> {

                v.forEach(item -> {
                    logger.debug("set : {}", item.outerHtml());
                });

                logger.debug("Check {},{} -> {}, size = {}", k.tag().toString(), k.classNames().toString(), k.text(), v.size());
                if (v.size() > 1 && isChildrenSimilar(k)) {
                    resultAgg.put(k, v);
                    logger.info("Save +++++++++++++++++++ ");
                    k.children().forEach(item -> {
                        if (!isEmpty(item))
                            logger.info("++ {}", item.text());
                    });
                } else {
                    logger.debug("Remove ------------------------ {}", k.text());
                }
            });

            return resultAgg;
        }
    }



    public static class PageLogic {
        public static boolean isPage(Map<Element, Set<Element>> module) {

            int totalCount = 0;
            for (Element element : module.keySet()) {
                Elements elementsMatchingOwnText = element.getElementsMatchingOwnText(DateTimeUtil.pattern);
                int size = elementsMatchingOwnText.size();

                if (size > 0){
                    int fadeCount = 0;
                    for (Element item : elementsMatchingOwnText) {
                        String date = DateTimeUtil.findDate(item.ownText());
                        if (date.length() / item.text().length() < 0.3){
                            ++fadeCount;
                        }
                        logger.info("{}", item.text());
                    }
                    totalCount += size - fadeCount;

                    logger.info("time count = {}, set size = {}, fade = {}",
                            size, module.get(element).size(), fadeCount);
                }

            }
            return totalCount > 20;
        }

        public static Map<Element, Set<Element>> filterCategory(Map<Element, Set<Element>> module) {

            Map<Element, Set<Element>> shortModule = new HashMap<>();
            module.forEach((k, v) -> {
                if (isShortModule(k, v)) {
                    shortModule.put(k, v);
                }
            });
            return shortModule;
        }

        public static boolean isShortModule(Element node, Set<Element> elements) {
            for (Element element : node.children()) {
                if (element.text().length() > 6) {
                    return false;
                }
            }
            return true;
        }
    }
}
