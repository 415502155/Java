package cn.edugate.esb.util;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.TypeReference;


public class JsonUtil {

	private static ObjectMapper MAPPER;

	static {
		MAPPER = generateMapper(JsonSerialize.Inclusion.NON_EMPTY);
	}

	private JsonUtil() {
	}

	/**
	 * 将json通过类型转换成对象
	 * 
	 * <pre>
	 *     {@link JsonUtil JsonUtil}.fromJson(\"{\\"username\\":\\"username\\", \\"password\\":\\"password\\"}\", User.class);
	 * </pre>
	 * 
	 * @param json json字符串
	 * @param clazz 泛型类型
	 * @return 返回对象
	 * @throws IOException
	 */
	public static <T> T getObjectFromJson(String json, Class<T> clazz) throws IOException {
		return clazz.equals(String.class) ? (T) json : MAPPER.readValue(json, clazz);
	}

	/**
	 * 将json通过类型转换成对象
	 * 
	 * <pre>
	 *     {@link JsonUtil JsonUtil}.fromJson(\"[{\\"username\\":\\"username\\", \\"password\\":\\"password\\"}, {\\"username\\":\\"username\\", \\"password\\":\\"password\\"}]\", new TypeReference&lt;List&lt;User&gt;&gt;);
	 * </pre>
	 * 
	 * @param json json字符串
	 * @param typeReference 引用类型
	 * @return 返回对象
	 * @throws IOException
	 */
	public static <T> T getObjectFromJson(String json, TypeReference<?> typeReference) throws IOException {
		return (T) (typeReference.getType().equals(String.class) ? json : MAPPER.readValue(json, typeReference));
	}

	/**
	 * 将对象转换成json
	 * 
	 * <pre>
	 *     {@link JsonUtil JsonUtil}.toJson(user);
	 * </pre>
	 * 
	 * @param object 对象
	 * @return 返回json字符串
	 * @throws IOException
	 */
	public static <T> String toJson(T object) throws IOException {
		return object instanceof String ? (String) object : MAPPER.writeValueAsString(object);
	}

	/**
	 * 将对象转换成json, 可以设置输出属性
	 * 
	 * <pre>
	 *     {@link JsonUtil JsonUtil}.toJson(user, {@link Inclusion Inclusion.ALWAYS});
	 * </pre>
	 * 
	 * {@link Inclusion Inclusion 对象枚举}
	 * <ul>
	 * <li>{@link Inclusion Inclusion.ALWAYS 全部列入}</li>
	 * <li>{@link Inclusion Inclusion.NON_DEFAULT 字段和对象默认值相同的时候不会列入}</li>
	 * <li>{@link Inclusion Inclusion.NON_EMPTY 字段为NULL或者\"\"的时候不会列入}</li>
	 * <li>{@link Inclusion Inclusion.NON_NULL 字段为NULL时候不会列入}</li>
	 * </ul>
	 * 
	 * @param object 对象
	 * @param inclusion 传入一个枚举值, 设置输出属性
	 * @return 返回json字符串
	 * @throws IOException
	 */
	public static <T> String toJson(T object, JsonSerialize.Inclusion inclusion) throws IOException {
		if (object instanceof String) {
			return (String) object;
		} else {
			ObjectMapper customMapper = generateMapper(inclusion);
			return customMapper.writeValueAsString(object);
		}
	}

	/**
	 * 将对象转换成json, 传入配置对象
	 * 
	 * <pre>
	 *     {@link ObjectMapper ObjectMapper} mapper = new ObjectMapper();
	 *     mapper.setSerializationInclusion({@link Inclusion Inclusion.ALWAYS});
	 *     mapper.configure({@link Feature Feature.FAIL_ON_UNKNOWN_PROPERTIES}, false);
	 *     mapper.configure({@link Feature Feature.FAIL_ON_NUMBERS_FOR_ENUMS}, true);
	 *     mapper.setDateFormat(new {@link SimpleDateFormat SimpleDateFormat}(\"yyyy-MM-dd HH:mm:ss\"));
	 *     {@link JsonUtil JsonUtil}.toJson(user, mapper);
	 * </pre>
	 * 
	 * {@link ObjectMapper ObjectMapper}
	 * 
	 * @see ObjectMapper
	 * 
	 * @param src 对象
	 * @param mapper 配置对象
	 * @return 返回json字符串
	 * @throws IOException
	 */
	public static <T> String toJson(T src, ObjectMapper mapper) throws IOException {
		if (null != mapper) {
			if (src instanceof String) {
				return (String) src;
			} else {
				return mapper.writeValueAsString(src);
			}
		} else {
			return null;
		}
	}

	/**
	 * 返回{@link ObjectMapper ObjectMapper}对象, 用于定制性的操作
	 * 
	 * @return {@link ObjectMapper ObjectMapper}对象
	 */
	public static ObjectMapper getMapperInstance() {
		return MAPPER;
	}

	/**
	 * 通过Inclusion创建ObjectMapper对象
	 * 
	 * {@link Inclusion Inclusion 对象枚举}
	 * <ul>
	 * <li>{@link Inclusion Inclusion.ALWAYS 全部列入}</li>
	 * <li>{@link Inclusion Inclusion.NON_DEFAULT 字段和对象默认值相同的时候不会列入}</li>
	 * <li>{@link Inclusion Inclusion.NON_EMPTY 字段为NULL或者\"\"的时候不会列入}</li>
	 * <li>{@link Inclusion Inclusion.NON_NULL 字段为NULL时候不会列入}</li>
	 * </ul>
	 * 
	 * @param inclusion 传入一个枚举值, 设置输出属性
	 * @return 返回ObjectMapper对象
	 */
	private static ObjectMapper generateMapper(JsonSerialize.Inclusion inclusion) {

		ObjectMapper customMapper = new ObjectMapper();

		// 设置输出时包含属性的风格
		customMapper.setSerializationInclusion(inclusion);

		// 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
		// customMapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		// 禁止使用int代表Enum的order()來反序列化Enum,非常危險
		// customMapper.configure(Feature.FAIL_ON_NUMBERS_FOR_ENUMS, true);

		// 所有日期格式都统一为以下样式
		customMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

		return customMapper;
	}
	
	/*public static void main(String[] args) {
		String teacherJsonStr = "{\"tech_id\": 15936, \"birthday\": \"1992-05-31\",  \"user_mobile\": \"15102263071\", \"role_name\": null }";
		try {
			Teacher t = new ObjectMapper().setDateFormat(new SimpleDateFormat("yyyy-MM-dd")).readValue(teacherJsonStr, Teacher.class);
			System.out.println(t);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}