package application.http.utils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import java.util.Map;


public class ScriptLoader {



    /*script cache*/

    private String script;

    private Map<String, Object> arguments;

    private ScriptLoader(String script, Map<String, Object> arguments) {
        this.script = script;
        this.arguments = arguments;
    }

    public String getScript() {
        return script;
    }

    public Map<String, Object> getArguments() {
        return arguments;
    }

    /**
     * @param path
     * @param key
     * @param value
     * @return
     */
    public static ScriptLoader valueOf(String path, String key, Object value) {
        Map<String, Object> map = Maps.newHashMap();
        map.put(key, value);
        return valueOf(path, map);
    }

    /**
     * @param path
     * @param keyValues key,value,key,value
     * @return
     */
    public static ScriptLoader valueOf(String path, Object... keyValues) {
        //check argument
        Preconditions.checkArgument(keyValues == null || keyValues.length % 2 != 0, "arguments don't match.");

        Map<String, Object> map = Maps.newHashMap();
        for (int i = 0; i < keyValues.length; i = i + 2) {
            map.put(String.valueOf(keyValues[i]), keyValues[i + 1]);
        }
        return valueOf(path, map);
    }

    /**
     * @param path
     * @param arguments
     * @return
     */
    public static ScriptLoader valueOf(String path,
                                       Map<String, Object> arguments) {
        return new ScriptLoader(path, arguments);
    }

}
