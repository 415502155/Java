package sng.service.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import sng.service.BaseAdminService;
import sng.util.ESBPropertyReader;
import sng.util.HttpClientUtil;
import sng.util.JsonUtil;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class BaseAdminImp implements BaseAdminService {
	
	public static final String GET_uploadBase64_URL = ESBPropertyReader.getProperty("esbRequestUrl")+"api/uploadBase64";

	@Override
	public String uploadBase64(HttpServletRequest request, String string,
			Map<String, Object> params) {
		HttpSession session = request.getSession();
		String token = session.getAttribute("token").toString();
		String udid = session.getAttribute("udid").toString();
		String data = "";
		try {
			params.put("token", token);
			params.put("udid", udid);
			String documentResult = HttpClientUtil.post(GET_uploadBase64_URL, params);
			ObjectMapper objectMapper = JsonUtil.getMapperInstance();
			JsonNode jsonNode = objectMapper.readTree(documentResult);
			JsonNode dataNode = jsonNode.get("data");
			data = dataNode.asText();
		}catch (Exception e) {
		}
		
		return data;
	}



	@Override
	public String uploadBase64NoSession(HttpServletRequest request,
			String string, Map<String, Object> params) {
		String data = "";
		try {
			String documentResult = HttpClientUtil.post(GET_uploadBase64_URL, params);
			ObjectMapper objectMapper = JsonUtil.getMapperInstance();
			JsonNode jsonNode = objectMapper.readTree(documentResult);
			JsonNode dataNode = jsonNode.get("data");
			data = dataNode.asText();
		}catch (Exception e) {
		}
		return data;
	}
		

}
