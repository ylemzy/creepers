package application.uil;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class JsonHelper {

	private static ObjectMapper objectMapper1;
	private static ObjectMapper objectMapper2;

	static {
		objectMapper1 = new ObjectMapper();
		objectMapper1.configure(SerializationFeature.INDENT_OUTPUT, true);
		objectMapper1.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
		objectMapper1.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false);
		objectMapper1.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		objectMapper1.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper1.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		objectMapper1.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		objectMapper1.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		objectMapper1.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		objectMapper2 = new ObjectMapper();
		objectMapper2.configure(SerializationFeature.INDENT_OUTPUT, false); // !
		objectMapper2.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
		objectMapper2.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false);
		objectMapper2.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		objectMapper2.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper2.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		objectMapper2.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		objectMapper2.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		objectMapper2.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public static String toJSON(Object value) {
		return toJSON(value, true);
	}

	public static String toJSON(Object value, boolean pretty) {
		try {
			return pretty ? objectMapper1.writeValueAsString(value) : objectMapper2.writeValueAsString(value);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static <T> List<T> toList(String json) {
		TypeReference<T> typeReference = new TypeReference<T>() {
		};
		try {
			return objectMapper1.readValue(json, typeReference);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static Map<String, Object> toMap(Object value){
		return toMap(toJSON(value));
	}

	public static <T> List<T> toList(String json, Class<T> clazz) {
		JavaType javaType = objectMapper1.getTypeFactory().constructCollectionType(List.class, clazz);
		try {
			return objectMapper1.readValue(json, javaType);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> toMap(String json) {
		try {
			return objectMapper1.readValue(json, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static <T> T toObject(String text, Class<T> clazz) {
		try {
			return objectMapper1.readValue(text, clazz);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}