package application.http.options;

/**
 * The authorization proxy line format is [host]:[port]:[username]:[password]
 * @author Horace
 *
 */
public class AuthSupportedProxyLoader {
	
	/*private static final String PROXIES_PATH = "auth_proxies.properties";
	private static final List<String> PROXIES = new ArrayList<>();
	public static final Map<String, Object> PROP = new HashMap<>();
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthSupportedProxyLoader.class);
	
	private AuthSupportedProxyLoader() {
	}

	static {
		List<String> lines = null;
		try (InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(PROXIES_PATH)) {
			lines = IOUtils.readLines(resourceAsStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		for (String line : lines) {
			if(!line.trim().equals("") && !line.trim().startsWith("#")) {
				if(line.matches("[\\w\\.]+[=]{1}[\\w\\.]*")) {
					String[] str = line.split("=");
					PROP.put(str[0], str[1]);
				} else {
					PROXIES.add(line);
				}
			}
		}
		LOGGER.info("Properties loaded: " + PROP);
	}
	
	public static List<String> getProxies(){
		return PROXIES;
	}*/

}
