package sng.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CustomJsonDateDeserializer extends JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		
		String date = jp.getText();
		if (StringUtils.isNotBlank(date)) {
			if (NumberUtils.isParsable(date)) {
				return new Date(Long.valueOf(date));
			} else {
				try {
					SimpleDateFormat format = null;
					if (date.length() <= 10) {
						format = new SimpleDateFormat("yyyy-MM-dd");
					} else {
						format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					}
					
					return format.parse(date);
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
			}
		} else {
			return null;
		}
	}

}
