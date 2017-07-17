//package com.bestinfo.controller.sysUser;
//
//import com.bestinfo.define.sysUser.UserStatic;
//import com.bestinfo.bean.sysUser.SysUser;
//import com.bestinfo.ehcache.system.DePartMentCache;
//import com.bestinfo.ehcache.UserRoleCache;
//import com.bestinfo.dao.page.Pagination;
//import com.bestinfo.service.sysUser.IUserService;
//import com.bestinfo.util.StringUtil;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import org.apache.log4j.Logger;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.ServletRequestUtils;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
///**
// *
// * @author yangyf
// */
//@Controller
//@RequestMapping(value = "/user")
//public class UserController {
//
//    private final Logger logger = Logger.getLogger(UserController.class);
//
//    @Resource
//    private IUserService userService;
//    @Resource
//    private DePartMentCache dePartMentCache;
//    @Resource
//    private UserRoleCache userRoleCache;
//
//    @RequestMapping(value = "/2login")
//    public String toLogin(HttpServletRequest request, Model resModel) {
//        return "/sysUser/login";
//    }
//
//    @RequestMapping(value = "/2add")
//    public String toAdd(HttpServletRequest request, Model resModel) {
//        return "/sysUser/user_add";
//    }
//
//    @RequestMapping(value = "/2modifypwd")
//    public String toModifyUserPwd(HttpServletRequest request, Model resModel) {
//        return "/sysUser/modify_pwd";
//    }
//
//    /**
//     * 登录
//     *
//     * @param request
//     * @param resModel
//     * @return
//     */
//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public String login(HttpServletRequest request, Model resModel) {
//        logger.info("user login");
//
//        String user_name = ServletRequestUtils.getStringParameter(request, "userName", "");
//        String user_pwd = ServletRequestUtils.getStringParameter(request, "password", "");
//        if (StringUtil.notNull(user_name) && StringUtil.notNull(user_pwd)) {
//
//            int flag = userService.userLogin(user_name, user_pwd, request);
//            if (flag == 0) {
////                return "/home/home_page";
////                return "/home/home";
//                 return "redirect:/home/index";
//            }
//            if (flag == -1) {
//                resModel.addAttribute("msg", "没有此用户！");
//            }
//            if (flag == -2) {
//                resModel.addAttribute("msg", "用户名或密码输入不正确！");
//            }
//            if (flag == -3) {
//                resModel.addAttribute("msg", "请先修改密码！");
//                return "/sysUser/modify_pwd";
//            }
//        } else {
//            resModel.addAttribute("msg", "用户名或密码不能为空！");
//        }
//         return"/sysUser/login";
////        return "index";
//    }
//
//    /**
//     * 新增用户
//     *
//     * @param request
//     * @param resModel
//     * @param user
//     * @return
//     */
//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> addUser(HttpServletRequest request, Model resModel, SysUser user) {
//        logger.info("add user");
//        Map<String, Object> resMap = new HashMap<String, Object>();
//        //获取前台参数,有些是默认值
////        String user_name = ServletRequestUtils.getStringParameter(request, "user_name", "");
////        String user_pwd = ServletRequestUtils.getStringParameter(request, "user_pwd", "123456");
////        int city_id = ServletRequestUtils.getIntParameter(request, "city_id", 0);
////        String real_name = ServletRequestUtils.getStringParameter(request, "real_name", "");
////        logger.info("real_name:" + real_name);
////        int department_id = ServletRequestUtils.getIntParameter(request, "department_id", 0);
////        int role_id = ServletRequestUtils.getIntParameter(request, "role_id", 0);
////        int work_status = ServletRequestUtils.getIntParameter(request, "work_status", 0);
//        Random random = new Random();
//        user.setUser_id(random.nextInt(100));
//        user.setForce_change_pwd(UserStatic.FORCE_CHANGE_PWD_YES);
//        user.setCity_id(0);
//        user.setRegist_date(new Date());
//        user.setRole_id(0);
//        user.setWork_status(0);
//
//        int flag = userService.addUser(user);
//        if (flag == 0) {
//            resMap.put("result", "success");
//            resMap.put("msg", "添加用户成功");
//        } else if (flag == -1) {
//            resMap.put("result", "fail");
//            resMap.put("msg", "用户已经存在！");
//        } else {
//            resMap.put("result", "fail");
//            resMap.put("msg", "添加用户错误，请重新操作！");
//        }
//        return resMap;
//    }
//
//    /**
//     * 根据user_id获取用户
//     *
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "/get", method = RequestMethod.GET)
//    @ResponseBody
//    public Map<String, Object> getUser(HttpServletRequest request) {
//        logger.info("get user by user_id");
//        Map<String, Object> resMap = new HashMap<String, Object>();
//
//        int user_id = ServletRequestUtils.getIntParameter(request, "user_id", 0);
//        if (user_id > 0) {
//            SysUser user = userService.getUserByUserId(user_id);
//            if (user != null) {
//                resMap.put("user", user);
//            }
//        }
//
//        return resMap;
//    }
//
//    /**
//     * 修改用户信息
//     *
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "/modify")
//    @ResponseBody
//    public Map<String, Object> modifyUser(HttpServletRequest request) {
//        logger.info("modify user");
//        Map<String, Object> resMap = new HashMap<String, Object>();
//
//        //获取前台参数
//        int user_id = ServletRequestUtils.getIntParameter(request, "user_id", 0);
//        String user_pwd = ServletRequestUtils.getStringParameter(request, "user_pwd", "123456");
//        int city_id = ServletRequestUtils.getIntParameter(request, "city_id", 0);
//        String real_name = ServletRequestUtils.getStringParameter(request, "real_name", "");
//        int department_id = ServletRequestUtils.getIntParameter(request, "department_id", 0);
//        int role_id = ServletRequestUtils.getIntParameter(request, "role_id", 0);
//        int work_status = ServletRequestUtils.getIntParameter(request, "work_status", 0);
//
//        if (user_id > 0) {
//            SysUser user = new SysUser();
//            user.setUser_id(user_id);
//            user.setUser_pwd(user_pwd);
//            user.setCity_id(city_id);
//            user.setReal_name(real_name);
//            user.setDepartment_id(department_id);
//            user.setRole_id(role_id);
//            user.setWork_status(work_status);
//
//            int flag = userService.updateUser(user);
//            if (flag == 0) {
//                resMap.put("result", "success");
//                resMap.put("msg", "修改用户成功！");
//            } else if (flag == -1) {
//                resMap.put("result", "fail");
//                resMap.put("msg", "用户不存在！");
//            } else {
//                resMap.put("result", "fail");
//                resMap.put("msg", "更新用户错误，请重新操作！");
//            }
//        }
//
//        return resMap;
//    }
//
//    /**
//     * 修改用户密码
//     *
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "/modifyPwd")
//    @ResponseBody
//    public Map<String, Object> modifyUserPwd(HttpServletRequest request) {
//        logger.info("modify user pwd");
//        Map<String, Object> resMap = new HashMap<String, Object>();
//
//        //获取前台参数
//        String old_user_pwd = ServletRequestUtils.getStringParameter(request, "user_pwd", "");
//        String new_user_pwd = ServletRequestUtils.getStringParameter(request, "user_pwd1", "");
//
//        if (StringUtil.notNull(old_user_pwd) && StringUtil.notNull(new_user_pwd)) {
//            //从session中获取用户
//            SysUser user = (SysUser) request.getSession().getAttribute("user");
//            if (user == null) {
//                logger.info("session is out");
//                resMap.put("result", "用户登录超时！");
//                return resMap;
//            }
//
//            int flag = userService.modifyUserPwd(user.getUser_id(), old_user_pwd, new_user_pwd);
//            if (flag == 0) {
//                resMap.put("result", "密码修改成功！");
//            } else if (flag == -1) {
//                resMap.put("result", "用户不存在！");
//            } else if (flag == -2) {
//                resMap.put("result", "旧密码不正确！");
//            } else {
//                resMap.put("result", "密码修改失败！");
//            }
//        } else {
//            logger.info("old pwd or new pwd is null");
//            resMap.put("result", "旧密码或新密码不能为空！");
//        }
//
//        return resMap;
//    }
//
//    /**
//     * 用户列表,分页
//     *
//     * @param request
//     * @param resModel
//     * @return
//     */
//    @RequestMapping(value = "/list")
//    public String userList(HttpServletRequest request, Model resModel) {
//        Pagination<SysUser> page = userService.getUserPageList(getUserListRequestParams(request));
//        resModel.addAttribute("page", page);
//        //获取部门名称的中文名称
//        String department_name;
//        //获取角色名称的中文名称
//        String role_name;
//        for (SysUser s : page.getRows()) {
//            department_name = dePartMentCache.getDePartMentById(s.getDepartment_id()).getDepartment_name();
//            role_name = userRoleCache.getUserRoleById(s.getRole_id()).getRole_name();
//            s.setDepartment_name(department_name);
//            s.setRole_name(role_name);
//            logger.info(s.getRegist_date());
//        }
//        return "/sysUser/user_list";
//    }
//
//    public String deleteUser(HttpServletRequest request, Model resModel) {
//
//        return null;
//    }
//
//    /**
//     * 根据前台请求获取查询参数列表
//     *
//     * @param request
//     * @return
//     */
//    private Map<String, Object> getUserListRequestParams(HttpServletRequest request) {
//        Map<String, Object> params = new HashMap<String, Object>();
//
//        params.put("pageNumber", ServletRequestUtils.getIntParameter(request, "pageNumber", 1));
//        params.put("pageSize", ServletRequestUtils.getIntParameter(request, "pageSize", 50));
//
//        //用户名
//        String user_name = ServletRequestUtils.getStringParameter(request, "user_name", "");
//        if (StringUtil.notNull(user_name)) {
//            params.put("user_name", user_name);
//        }
//
//        //角色id
//        int role_id = ServletRequestUtils.getIntParameter(request, "role_id", 0);
//        if (role_id > 0) {
//            params.put("role_id", role_id);
//        }
//
//        //部门id
//        int department_id = ServletRequestUtils.getIntParameter(request, "department_id", 0);
//        if (department_id > 0) {
//            params.put("department_id", department_id);
//        }
//
//        return params;
//    }
//
//    @RequestMapping(value = "/logout")
//    public String logOut(HttpServletRequest request) {
//        logger.info("user log out");
//        request.getSession().setAttribute("user", null);
//        return "/sysUser/login";
//    }
//
//}
