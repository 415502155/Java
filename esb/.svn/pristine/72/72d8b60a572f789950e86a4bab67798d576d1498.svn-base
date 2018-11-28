package cn.edugate.esb.util;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class OperatorJsonSerializer extends JsonSerializer<Operator>
{
	public OperatorJsonSerializer()
	{
		
	}
	
	@Override
	public void serialize(Operator value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		// TODO Auto-generated method stub
		jgen.writeString(value.getValue());
	}
	
}