package cn.edugate.esb.util;

import java.io.IOException;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

public class OperatorJsonDeserializer extends JsonDeserializer<Operator>
{

	@Override
	public Operator deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		Operator o=getNullValue();
		JsonToken t = jp.getCurrentToken();
	
	    if (t == JsonToken.VALUE_STRING) {	    	
	    	o= Operator.getOperator(jp.getText().trim());
	    }
		return o;
	}
	
	public Operator getNullValue()
	{
		return Operator.anywhere;
	}
	
}
