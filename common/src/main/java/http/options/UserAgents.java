package http.options;

/**
 * Created by ylemzy on 11/27/2015.
 */
public class UserAgents {
 /*   private static List<String> userAgentList = null;
    private static Random random;

    public UserAgents() {
        super();
    }

    public static final String TYPE_FLAG = "b:";
    public static final String COMMENT_FLAG = "#";

    static {
        loadDefaultUserAgent();
    }

    private static void loadDefaultUserAgent() {
        InputStream read = ConfigHelper.read("/user-agent.txt");

        InputStreamReader reader = new InputStreamReader(read, Charsets.toCharset(Charset.defaultCharset()));
        BufferedReader bufferedReader = IOUtils.toBufferedReader(reader);
        userAgentList = new ArrayList<>();
        try {
            for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                if (line.startsWith(TYPE_FLAG) || line.startsWith(COMMENT_FLAG) || Strings.isBlank(line)) {
                    continue;
                }
                userAgentList.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        random = new Random();
    }

    public static final List<String> getUserAgentList() {
        return userAgentList;
    }

    public static String getRandomAgent() {
        if (loopAgent == true){
            return getLoopAgent();
        }
        return userAgentList.get(random.nextInt(userAgentList.size()));
    }


    public static boolean loopAgent = false;
    public static void setLoopAgent(boolean loopAgent){
        UserAgents.loopAgent = loopAgent;
    }

    public static int index = 0;
    public static String getLoopAgent(){
        index = (index + 1) % userAgentList.size();
        String s = userAgentList.get(index);
        return s;
    }*/
}
