package cn.edugate.esb.params;

import java.io.IOException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.SerializerProvider;

public class ObjectMappingCustomer extends ObjectMapper {
	public ObjectMappingCustomer()  
    {  
		super();
        // 空值处理为空串  
        this.getSerializerProvider().setNullValueSerializer(
			new JsonSerializer<Object>() {
				@Override
				public void serialize(Object value, JsonGenerator jgen,
						SerializerProvider provider) throws IOException,
						JsonProcessingException {
					jgen.writeString("");
				}
		});
        
    }
}
