package cn.edugate.esb.web.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edugate.esb.Result;
import cn.edugate.esb.eduEnum.EnumMessage;
import cn.edugate.esb.entity.Field;
import cn.edugate.esb.params.EduJsonFilter;
import cn.edugate.esb.params.EduJsonFilters;
import cn.edugate.esb.params.filter.FieldFilter;
import cn.edugate.esb.redis.dao.RedisFieldDao;
import cn.edugate.esb.service.FieldService;
import cn.edugate.esb.util.Utils;

@Controller
@RequestMapping("/api/field")
public class FieldController {

	private RedisFieldDao redisFieldDao;
	@Autowired
	public void setRedisFieldDao(RedisFieldDao redisFieldDao) {
		this.redisFieldDao = redisFieldDao;
	}
	
	private FieldService fieldService;
	@Autowired
	public void setFieldService(FieldService fieldService) {
		this.fieldService = fieldService;
	}

	/**
	 * 创建场地
	 * @param fieldName   【必填】场地名称
	 * @param orgId       【必填】机构主键
	 * @param fieldType   【必填】场地类型
	 * @param fieldNumber 【必填】场地编号
	 * @param note        [选填] 备注说明
	 * @param request
	 * @return 创建完成的场地
	 */
	@RequestMapping(value = "/addField")
	@EduJsonFilters(value={@EduJsonFilter(mixin=FieldFilter.class, target=Field.class)})
	public @ResponseBody
	Result<String> addField(@RequestParam(value = "field_name") String fieldName,
			@RequestParam(value = "org_id") Integer orgId, @RequestParam(value = "campus_id") Integer campusID,
			@RequestParam(value = "field_type") Integer fieldType, @RequestParam(value = "field_number") String fieldNumber,
			@RequestParam(value = "field_note", defaultValue = "") String note, HttpServletRequest request) {
		
		Result<String> result = new Result<String>();
		
		Field field = new Field();
		if (StringUtils.isNotBlank(fieldName)) {
			// 场地名称不为空时创建
			
			field.setField_name(Utils.getValue(fieldName));
			field.setOrg_id(orgId);
			field.setCampus_id(campusID);
			field.setField_type(fieldType);
			field.setField_number(Utils.getValue(fieldNumber));
			field.setField_note(Utils.getValue(note));
			fieldService.add(field);
		}
		
		if (field.getField_id() != null) {
			// 返回结果
			result.setData(String.valueOf(field.getField_id()));
			result.setCode(EnumMessage.success.getCode());
			result.setMessage(EnumMessage.success.getMessage());
			result.setSuccess(true);
			return result;
		} else {
			// 返回结果
			result.setData("");
			//result.setCode(EnumMessage.success.getCode());
			//result.setMessage(EnumMessage.success.getMessage());
			result.setSuccess(false);
			return result;
		}
		
	}
	
	/**
	 * 删除场地
	 * @param fieldId 【必填】要删除的场地的主键
	 * @param request
	 * @return 删除的场地的主键
	 */
	@RequestMapping(value = "/deleteField")
	public @ResponseBody
	Result<String> deleteField(@RequestParam(value = "field_id") Integer fieldId, HttpServletRequest request) {
		Result<String> result = new Result<String>();
		try {
			Field field = redisFieldDao.getFieldById(fieldId);
			if (null == field) {
				result.setSuccess(true);
				result.setMessage(EnumMessage.success.getMessage());
				result.setCode(EnumMessage.success.getCode());
				return result;
			}
			// 删除场地
			fieldService.delete(fieldId);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(EnumMessage.fail.getMessage());
			result.setCode(EnumMessage.fail.getCode());
			return result;
		}

		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}
	
	/**
	 * 更新场地
	 * @param fieldId   【必填】场地主键
	 * @param fieldName [选填] 场地名称
	 * @param orgId     [选填] 机构主键
	 * @param fieldType [选填] 场地类型
	 * @param note      [选填] 备注
	 * @param request
	 * @return 更新后的场地
	 */
	@RequestMapping(value = "/updateField")
	@EduJsonFilters(value={@EduJsonFilter(mixin=FieldFilter.class, target=Field.class)})
	@ResponseBody
	public Result<String> updateField(@RequestParam(value = "field_id") Integer fieldId,
			@RequestParam(value = "campus_id") Integer campusId, @RequestParam(value = "field_name") String fieldName,
			@RequestParam(value = "field_type") Integer fieldType, @RequestParam(value = "field_number") String fieldNumber,
			@RequestParam(value = "field_note") String field_note, HttpServletRequest request) {

		Result<String> result = new Result<String>();
		Field field = redisFieldDao.getFieldById(fieldId);
		if (field != null) {
			// 将新值设置给查询出的实体
			field.setField_name(Utils.getValue(fieldName));
			field.setCampus_id(campusId);
			field.setField_type(fieldType);
			field.setField_number(Utils.getValue(fieldNumber));
			field.setField_note(Utils.getValue(field_note));

			fieldService.update(field);

			result.setSuccess(true);
			// result.setMessage("");
			// result.setCode("");
			return result;
		} else {
			result.setSuccess(false);
			result.setMessage(EnumMessage.field_not_found.getMessage());
			result.setCode(EnumMessage.field_not_found.getCode());
			return result;
		}
	}
	
	/**
	 * 根据场地主键获取场地
	 * @param fieldId 【必填】场地主键
	 * @param request
	 * @return 查到的场地
	 */
	@RequestMapping(value = "/getField")
	@EduJsonFilters(value={@EduJsonFilter(mixin=FieldFilter.class, target=Field.class)})
	public @ResponseBody Result<Map<String, Field>> getField(
			@RequestParam(value="field_id") Integer fieldId,
			HttpServletRequest request){
		Result<Map<String, Field>> result = new Result<Map<String, Field>>();
		Map<String, Field> data = new HashMap<String, Field>();
		//获取场地信息
		Field field = redisFieldDao.getFieldById(fieldId);
		if(null==field){
			result.setSuccess(false);
			result.setMessage(EnumMessage.field_not_found.getMessage());
			result.setCode(EnumMessage.field_not_found.getCode());
			return result;
		}
		data.put("field", field);
		result.setData(data);
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}
	
	@RequestMapping(value = "/getFieldEntity")
	@ResponseBody
	@EduJsonFilters(value={@EduJsonFilter(mixin=FieldFilter.class, target=Field.class)})
	public Result<Field> getFieldEntity(@RequestParam(value = "field_id") Integer fieldId, HttpServletRequest request) {
		Result<Field> result = new Result<Field>();
		if (fieldId != null) {
			// 获取场地信息
			Field field = redisFieldDao.getFieldById(fieldId);
			if (field != null) {
				result.setSuccess(true);
				result.setData(field);
				result.setMessage(EnumMessage.success.getMessage());
				result.setCode(EnumMessage.success.getCode());
			} else {
				result.setSuccess(false);
				result.setMessage(EnumMessage.field_not_found.getMessage());
				result.setCode(EnumMessage.field_not_found.getCode());
			}
		} else {
			result.setSuccess(false);
			result.setMessage(EnumMessage.fail.getMessage());
			result.setCode(EnumMessage.fail.getCode());
		}

		return result;
	}
	
	/**
	 * 根据查询条件获取场地，查询条件可以是任意场地合
	 * @param orgId     [选填] 机构主键
	 * @param fieldType [选填] 场地类型
	 * @param memberId  [选填,与type联合使用] 成员主键
	 * @param type      [选填,与memberId联合使用] 成员类型
	 * @param request
	 * @return 符合条件的场地的集合，带成员，带机构信息
	 */
	@RequestMapping(value = "/getFields")
	@EduJsonFilters(value={@EduJsonFilter(mixin=FieldFilter.class, target=Field.class)})
	public @ResponseBody Result<Object> getFields(
			@RequestParam(value="org_id",defaultValue="-1") Integer orgId,
			@RequestParam(value="field_type",defaultValue="-1") Integer fieldType,
			@RequestParam(value="field_name",defaultValue="") String fieldName,
			@RequestParam(value="field_number",defaultValue="") String fieldNumber,
			HttpServletRequest request){
		Result<Object> result = new Result<Object>();
		Map<String,Field> fieldsMap = new HashMap<String,Field>();
		Field field = new Field();
		if(StringUtils.isNotEmpty(fieldName)){
			field.setField_name(fieldName);
		}
		if(StringUtils.isNotEmpty(fieldNumber)){
			field.setField_number(fieldNumber);
		}
		if(!"-1".equals(fieldType.toString())){
			field.setField_type(fieldType);
		}
		if(!"-1".equals(orgId.toString())){
			field.setOrg_id(orgId);
		}
		fieldsMap = redisFieldDao.getFields(field);
		if(null==fieldsMap){
			result.setSuccess(false);
			result.setMessage(EnumMessage.field_not_found.getMessage());
			result.setCode(EnumMessage.field_not_found.getCode());
			return result;
		}
		result.setData(fieldsMap.values());
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}
	
	@RequestMapping(value="/getFieldList")
	@ResponseBody
	@EduJsonFilters(value={@EduJsonFilter(mixin=FieldFilter.class, target=Field.class)})
	public Result<List<Field>> getFieldList(@RequestParam(value = "org_id") Integer orgId,
			@RequestParam(value = "field_type", defaultValue="-1") Integer fieldType) {
		Result<List<Field>> result = new Result<List<Field>>();
		List<Field> fieldList = null;
		if (orgId != null) {
			fieldList = redisFieldDao.getFieldList(orgId, fieldType);
			result.setSuccess(true);
			result.setMessage(EnumMessage.success.getMessage());
			result.setCode(EnumMessage.success.getCode());
		} else {
			result.setSuccess(false);
			result.setMessage(EnumMessage.fail.getMessage());
			result.setCode(EnumMessage.fail.getCode());
		}
		result.setData(fieldList);

		return result;
	}
	
}
