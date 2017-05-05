package application.fetch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Dove Wang
 * 
 */
public class Category implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2408751553125190847L;

	private String name;
	private String link;
	private String webId;
	private boolean fetchable = false;
	private Map<String, Object> mapping;
	private List<Category> children = new ArrayList<Category>();
	private String parent;
	private String path;


	/*Template will keep this property as item's category while fetching item.
	* For more detail, look up template's item function and the Item class.
	* */
	private Map<String, Object> data = new HashMap<String, Object>();

	public Category() {
	}

	public Category(String name, String link, String webId){
		this.name = name;
		this.link = link;
		this.webId = webId;
	}

	public static Category make(String name, String link, Category parent){
		return new Category(name, link, parent.getWebId());
	}

	public static Category makeFetchable(String name, String link, Category parent){
		Category make = make(name, link, parent);
		make.setFetchable(true);
		return make;
	}

	/**
	 * use breadcrumbs path
	 * 
	 * @param path
	 * @param mapping
	 * @return
	 */
	public final static Map<String, Object> make(String path,
			Map<String, Object> mapping) {
		Map<String, Object> c = new HashMap<String, Object>();
		c.put("path", path);
		c.put("mapping", mapping);
		return c;
	}

	public String getWebId() {
		return webId;
	}

	public void setWebId(String webId) {
		this.webId = webId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public boolean isFetchable() {
		return fetchable;
	}

	public void setFetchable(boolean fetchable) {
		this.fetchable = fetchable;
	}

	public Map<String, Object> getMapping() {
		return mapping;
	}

	public void setMapping(Map<String, Object> mapping) {
		this.mapping = mapping;
		data.put("mapping", mapping);
	}

	public List<Category> getChildren() {
		return children;
	}

	public void setChildren(List<Category> children) {
		this.children = children;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public Category addChild(Category category) {
		children.add(category);
		category.setParent(this.getName());
		category.setPath(path == null ? category.getName() : path + "/"
				+ category.getName());
		return this;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
		data.put("path", path);
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}


	public Map<String, Object> get() {
		return data;
	}

	/*Item may belongs to multiply categories, so it has multiply path.
	Just be used for transforming an item webUrl to queue and template request.
	 */
	public void setPaths(List<String> paths){
		data.put("paths", paths);
	}

	public List<String> getPaths(){
		return (List)data.get("paths");
	}

	public boolean hasChildren() {
		return this.children.size() != 0;
	}

}
