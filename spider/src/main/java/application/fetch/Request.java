package application.fetch;

import java.util.HashMap;
import java.util.Map;


public class Request implements java.io.Serializable {

	private static final long serialVersionUID = -1523215337635892080L;

	public enum Type {
		CATEGORY, PAGE, ITEM
	}

	public enum Method {
		POST, GET
	}

	private String URL;

	private Type type;

	private Method method = Method.GET;

	private String webId;

	private Map<String, Object> headers = new HashMap<String, Object>();
	private Map<String, Object> parameters = new HashMap<String, Object>();
	private Map<String, Object> attributes = new HashMap<String, Object>();

	private int priority = Integer.MAX_VALUE;

	protected Request(){}

	public Request(String URL, Type type) {
		this.URL = URL;
		this.type = type;
	}

	public Request(String URL, Type type, String name, Object value,
			Object... pairs) {
		this.URL = URL;
		this.type = type;
		this.attr(name, value);
		if (pairs != null) {
			if (pairs.length % 2 != 0) {
				throw new IllegalArgumentException("pairs arguments error");
			}
			for (int i = 0; i < pairs.length; i = i + 2) {
				this.attr(String.valueOf(pairs[i]), pairs[i + 1]);
			}
		}
	}

	public String getURL() {
		return URL;
	}

	public Request header(String name, Object value) {
		headers.put(name, value);
		return this;
	}

	@SuppressWarnings("unchecked")
	public <T> T header(String name) {
		return (T) headers.get(name);
	}

	public void setURL(String URL) {
		this.URL = URL;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}


	@SuppressWarnings("unchecked")
	public <T> T attr(String name) {
		return (T) attributes.get(name);
	}

	public Request attr(String name, Object value) {
		attributes.put(name, value);
		return this;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public String getWebId() {
		return webId;
	}

	public Request setWebId(String webId) {
		this.webId = webId;
		return this;
	}

	public Request method(Method method) {
		this.method = method;
		return this;
	}

	public Method method() {
		return method;
	}

	public Map<String, Object> parameters() {
		return parameters;
	}

	public Request parameter(String name, Object value) {
		parameters.put(name, value);
		return this;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

}
