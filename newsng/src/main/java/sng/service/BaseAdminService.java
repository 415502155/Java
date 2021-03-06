package sng.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface BaseAdminService {

	String uploadBase64(HttpServletRequest request, String string,
			Map<String, Object> map);


	String uploadBase64NoSession(HttpServletRequest request, String string,
			Map<String, Object> map);	
	
}
