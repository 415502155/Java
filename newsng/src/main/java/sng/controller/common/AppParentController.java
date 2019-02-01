package sng.controller.common;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;

import sng.constant.Consts;
import sng.entity.UserRegister;
import sng.pojo.Result;
import sng.service.AppParentService;
import sng.service.UserRegisterService;
import sng.util.Constant;
import sng.util.HttpClientUtil;
import sng.util.JsonUtil;
import sng.util.Paging;
import sng.util.Utils;

/**
 * @author magq
 * @version 1.0
 * @desc 家长端--用户注册
 * @data 2018-12-11
 */
@Controller
@RequestMapping("/mobile/parentApp")
public class AppParentController extends BaseAction {

    @Autowired
    private AppParentService appParentService;

    @Autowired
    private UserRegisterService userRegisterService;

    /**
     * 用户注册
     *
     * @param org_id         机构ID
     * @param mobile         电话号码
     * @param validateCode   验证码
     * @param password       密码
     * @param repassword确认密码
     * @param openId         微信信息ID
     * @return
     * @throws Exception
     */
    @RequestMapping("/userRegister.json")
    @ResponseBody
    public Result userRegister(Integer org_id, String mobile, String validateCode, String password, String repassword,
                               String open_id, String nick_name, String head_url) throws Exception {
        Result result = new Result();
        // 参数验证
        if (StringUtils.isAnyBlank(mobile)) {
            result.setMessage("手机号不能为空");
            result.setSuccess(false);
            return result;
        }
        if (StringUtils.isAnyBlank(validateCode)) {

            result.setMessage("验证码不能为空！");
            result.setSuccess(false);
            return result;
        }
        if (StringUtils.isAnyBlank(password)) {

            result.setMessage("密码不能为空！");
            result.setSuccess(false);
            return result;
        }
        if (StringUtils.isAnyBlank(repassword)) {
            result.setMessage("确认密码不能为空！");
            result.setSuccess(false);
            return result;
        }
        if (StringUtils.isAnyBlank(open_id)) {
            result.setMessage("用户的微信信息ID为空！");
            result.setSuccess(false);
            return result;
        }

        if (!password.equals(repassword)) {
            result.setMessage("两次密码输入的不一致");
            result.setSuccess(false);
            return result;
        }

        // 当前电话号码已经注册
        result = appParentService.isTelExist(org_id, mobile);
        if (!result.isSuccess()) {
            return result;
        }

        // 验证验证码
        String validResult = HttpClientUtil.post(ESB_REQUEST_URL + "validCode",
                "phone=" + mobile + "&code=" + validateCode, "application/x-www-form-urlencoded");
        Result<String> vResult = JsonUtil.getObjectFromJson(validResult, new TypeReference<Result<String>>() {
        });
        if (200 != vResult.getCode()) {
            result.setSuccess(false);
            result.setMessage("验证码错误！");
            return result;
        }
        // 保存到用户注册表
        UserRegister userAuth = new UserRegister();
        userAuth.setTelephone(mobile);
        userAuth.setLoginName(mobile);
        userAuth.setOrgId(org_id);
        userAuth.setAuthStatus(0); // 未认证
        userAuth.setIsDel(0);
        String user_salt = Utils.makecode(); // 密码
        userAuth.setLoginSalt(user_salt);
        userAuth.setInsertTime(new Date());
        userAuth.setOpen_id(open_id);
        userAuth.setHead_url(head_url);
        userAuth.setNick_name(nick_name);
        userAuth.setLoginPassword(Utils.MD5(user_salt + ":" + password));
        userAuth.setRelation(Constant.DEFAULT_RELATION);
        result = appParentService.isOrgIdAndOpenIdExist(org_id, open_id);
        if (!result.isSuccess()) {
            userRegisterService.save(userAuth);
            result.setMessage("注册成功");
            result.setSuccess(true);
        } else {
            result.setMessage("该手机在此少年宫中已注册");
            result.setSuccess(false);
        }

        return result;
    }

    /**
     * 跟新用户信息---手机号码
     *
     * @param request
     * @param response
     * @param user_Id         用户ID
     * @param org_id          机构ID
     * @param mobile          手机号（新手机号）
     * @param validateCode验证码
     * @param password密码
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateCustomInfoTelNum.json")
    @ResponseBody
    public Result updateCustomInfoTelNum(HttpServletRequest request, HttpServletResponse response, Integer org_id,
                                         String mobile, String validateCode, String password, Integer identity, String old_mobile) throws Exception {
        Result result = new Result();
        // 参数验证
        if (StringUtils.isBlank(old_mobile)) {
            result.setMessage("原手机号不能为空");
            result.setSuccess(false);
            return result;
        }
        if (StringUtils.isAnyBlank(mobile)) {
            result.setMessage("新手机号不能为空");
            result.setSuccess(false);
            return result;
        }
        if (StringUtils.isAnyBlank(validateCode)) {
            result.setMessage("验证码不能为空！");
            result.setSuccess(false);
            return result;
        }

        if (org_id == null) {
            result.setMessage("机构id不为空！");
            result.setSuccess(false);
            return result;
        }

        if (identity == null) {
            result.setMessage("登录端类型ID不能为空，0家长端，1教师端！");
            result.setSuccess(false);
            return result;
        }

        if (Consts.IDENTITY_PARENT == identity) {
            // 1:先判断新手机号是否在注册表中存在
            result = appParentService.isTelExist(org_id, mobile);
            if (!result.isSuccess()) {
                return result;
            }
        }


        // 2:验证验证码（老师和家长都要验证）
        String validResult = HttpClientUtil.post(ESB_REQUEST_URL + "validCode",
                "phone=" + mobile + "&code=" + validateCode, "application/x-www-form-urlencoded");
        Result<String> vResult = JsonUtil.getObjectFromJson(validResult, new TypeReference<Result<String>>() {
        });
        if (200 != vResult.getCode()) {
            result.setSuccess(false);
            result.setMessage("验证码错误！");
            return result;
        }


        Integer user_register_id = 0;
        String salt = "";
        String pass = "";
        Integer auth_status = 0;
        if (Consts.IDENTITY_PARENT == identity) {
            // 根据老的电话号码和机构ID获取注册信息
            result = appParentService.isTelExist(org_id, old_mobile);
            Paging paging = (Paging) result.getData();
            if (paging != null) {
                if (paging.getData() != null && !paging.getData().isEmpty()) {
                    Map<String, Object> map = (Map<String, Object>) paging.getData().get(0);
                    user_register_id = (Integer) map.get("user_register_id");
                    salt = (String) map.get("login_salt");
                    pass = (String) map.get("login_password");
                    auth_status = (Integer) map.get("auth_status");
                }
            }
            if (StringUtils.isNotBlank(password)) {
                // 3:验证密码是否正确
                boolean flag = this.validatePassWord(salt, pass, password);
                if (!flag) {
                    result.setSuccess(false);
                    result.setMessage("输入的密码错误！");
                    return result;
                }
            }
            // 4:①更新当前用户ID对应的注册表信息中的手机信息(newsng数据中的)
            result = appParentService.updateCustomInfo(user_register_id, org_id, mobile, null, null);
        }

        if (result.isSuccess() && (auth_status == Consts.AUTH_STATUS_OK || Consts.IDENTITY_TEACHER == identity)) {
            // ②更新base数据库中的
            Map<String, Object> param = new HashMap<>();
            param.put("new_user_mobile", mobile);
            param.put("identity", identity);
            param.put("user_mobile", old_mobile);
            param.put("org_id", org_id);
            String updateSngMobile = this.callPostUrl(request, "/api/student/updateSngMobile", param);
            result = JsonUtil.getObjectFromJson(updateSngMobile, new TypeReference<Result<String>>() {
            });

        }
        return result;
    }

    /**
     * 验证密码是否正确
     *
     * @param salt
     * @param pass
     * @param paramPassWord
     * @return
     */
    private boolean validatePassWord(String salt, String pass, String paramPassWord) {
        boolean flag = false;
        if (Utils.MD5(salt + ":" + paramPassWord).equals(pass)) {
            flag = true;
        }
        return flag;
    }

    /**
     * 跟新用户信息---用户密码(老师端修改密码、家长端修改密码、忘记密码)
     *
     * @param request
     * @param response
     * @param org_id          机构ID
     * @param mobile          登录电话号码
     * @param old_password    原密码
     * @param password        新密码
     * @param repassword确认新密码
     * @param identity客户端登录类型 教师端 家长端
     * @param validateCode    验证码(忘记密码的时候要使用验证码)
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateCustomInfoPassWord.json")
    @ResponseBody
    public Result updateCustomInfoPassWord(HttpServletRequest request, HttpServletResponse response, Integer org_id,
                                           String mobile, String old_password, String password, String repassword, Integer identity,
                                           String validateCode) throws Exception {
        Result result = new Result();
        // 参数验证
        if (StringUtils.isAnyBlank(mobile)) {
            result.setMessage("手机号不能为空");
            result.setSuccess(false);
            return result;
        }

        if (StringUtils.isAnyBlank(password)) {

            result.setMessage("新密码不能为空！");
            result.setSuccess(false);
            return result;
        }
        if (StringUtils.isAnyBlank(repassword)) {
            result.setMessage("确认新密码不能为空！");
            result.setSuccess(false);
            return result;
        }
        if (org_id == null) {
            result.setMessage("机构id不能为空！");
            result.setSuccess(false);
            return result;
        }
        if (identity == null) {
            result.setMessage("客户端类型不能为空！");
            result.setSuccess(false);
            return result;
        }
        if (!password.equals(repassword)) {
            result.setMessage("两次密码输入的不一致");
            result.setSuccess(false);
            return result;
        }

        Integer user_register_id = 0;
        String salt = "";
        String pass = "";
        Integer auth_status = 0;
        if (Consts.IDENTITY_PARENT == identity) {
            // 根据老的电话号码和机构ID获取注册信息
            result = appParentService.isTelExist(org_id, mobile);
            Paging paging = (Paging) result.getData();
            if (paging != null) {
                if (paging.getData() != null && !paging.getData().isEmpty()) {
                    Map<String, Object> map = (Map<String, Object>) paging.getData().get(0);
                    user_register_id = (Integer) map.get("user_register_id");
                    salt = (String) map.get("login_salt");
                    pass = (String) map.get("login_password");
                    auth_status = (Integer) map.get("auth_status");
                }
            }
        }

        //验证码不为空说明是点击了忘记密码操作
        if (StringUtils.isNotBlank(validateCode)) {
            // 验证验证码
            String validResult = HttpClientUtil.post(ESB_REQUEST_URL + "validCode",
                    "phone=" + mobile + "&code=" + validateCode, "application/x-www-form-urlencoded");
            Result<String> vResult = JsonUtil.getObjectFromJson(validResult, new TypeReference<Result<String>>() {
            });
            if (200 != vResult.getCode()) {
                result.setSuccess(false);
                result.setMessage("验证码错误！");
                return result;
            }

        } else {
            if (StringUtils.isAnyBlank(old_password)) {
                result.setMessage("原密码不能为空！");
                result.setSuccess(false);
                return result;
            }
        }


        if (Consts.IDENTITY_PARENT == identity) {
            String temp_password = Utils.MD5(salt + ":" + password);
            // 原密码验证密码是否正确
            boolean flag = this.validatePassWord(salt, pass, old_password);
            if (!flag) {
                result.setSuccess(false);
                result.setMessage("输入的原密码错误！");
                return result;
            }
            // 更新当前用户ID对应的注册表信息中的手机信息(newsng数据中的)
            result = appParentService.updateCustomInfo(user_register_id, org_id, null, temp_password, null);
        }

        if ((result.isSuccess() && auth_status == Consts.AUTH_STATUS_OK) || Consts.IDENTITY_TEACHER == identity) {
            // 更新base数据库中的
            Map<String, Object> param = new HashMap<>();
            param.put("password", password);
            param.put("identity", identity);
            param.put("user_mobile", mobile);
            param.put("org_id", org_id);
            param.put("oldpassword", old_password);
            String updateSngPassword = this.callPostUrl(request, "/no/updateSngPassword", param);
            result = JsonUtil.getObjectFromJson(updateSngPassword, new TypeReference<Result<String>>() {
            });
        }
        return result;
    }

    /**
     * 跟新用户信息---父母与孩子关系
     *
     * @param request
     * @param response
     * @param parent_id   家长主键
     * @param stud_id     学生主键
     * @param relation    家长学生关系
     * @param org_id机构ID
     * @param mobile登录手机号
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateCustomInfoRelation.json")
    @ResponseBody
    public Result updateCustomInfoRelation(HttpServletRequest request, HttpServletResponse response, Integer parent_id,
                                           Integer stud_id, Integer relation, Integer org_id, String mobile) throws Exception {
        Result result = new Result();
        // 参数验证
        if (StringUtils.isAnyBlank(mobile)) {
            result.setMessage("手机号不能为空");
            result.setSuccess(false);
            return result;
        }
        if (org_id == null) {
            result.setMessage("机构id不能为空！");
            result.setSuccess(false);
            return result;
        }
        if (relation == null) {
            result.setMessage("家长学生关系ID不能为空！");
            result.setSuccess(false);
            return result;
        }

        // 根据老的电话号码和机构ID获取注册信息
        result = appParentService.isTelExist(org_id, mobile);
        Paging paging = (Paging) result.getData();
        Integer user_register_id = 0;
        Integer auth_status = 0;
        if (paging != null) {
            if (paging.getData() != null && !paging.getData().isEmpty()) {
                Map<String, Object> map = (Map<String, Object>) paging.getData().get(0);
                user_register_id = (Integer) map.get("user_register_id");
                auth_status = (Integer) map.get("auth_status");
            }
        }
        // 更新当前用户ID对应的注册表信息中的手机信息(newsng数据中的)
        result = appParentService.updateCustomInfo(user_register_id, org_id, null, null, relation);
        if (result.isSuccess() && auth_status == Consts.AUTH_STATUS_OK) {
            // 更新base数据库中的
            Map<String, Object> param = new HashMap<>();
            param.put("parent_id", parent_id);
            param.put("stud_id", stud_id);
            param.put("relation", relation);
            param.put("org_id", org_id);
            String updateSngRelation = this.callPostUrl(request, "/api/student/updateSngRelation", param);
            result = JsonUtil.getObjectFromJson(updateSngRelation, new TypeReference<Result<String>>() {
            });
        }
        return result;
    }

}
