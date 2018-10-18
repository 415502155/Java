//package com.bestinfo.controller.business;
//
//import bestinfo.system.WorkStatusDefine;
//import com.bestinfo.bean.business.DealerInfo;
//import com.bestinfo.bean.business.DealerPrivilege;
//import com.bestinfo.bean.encoding.BankCode;
//import com.bestinfo.bean.encoding.CityInfo;
//import com.bestinfo.bean.encoding.DealerType;
//import com.bestinfo.bean.encoding.IdType;
//import com.bestinfo.bean.sysUser.SystemInfo;
//import com.bestinfo.ehcache.BankCodeCache;
//import com.bestinfo.ehcache.CityInfoCache;
//import com.bestinfo.ehcache.DealerTypeCache;
//import com.bestinfo.ehcache.IdTypeCache;
//import com.bestinfo.ehcache.business.DealerInfoCache;
//import com.bestinfo.ehcache.business.DealerPrivilegeCache;
//import com.bestinfo.ehcache.game.GameInfoCache;
//import com.bestinfo.ehcache.system.SystemInfoCache;
//import com.bestinfo.service.business.IDealerInfoSer;
//import com.bestinfo.util.StringUtil;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//import org.apache.log4j.Logger;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.ServletRequestUtils;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
///**
// * 代销商信息
// *
// * @author hhhh
// */
//@Controller
//@RequestMapping(value = "/dealerInfo")
//public class DealerInfoCtr {
//
//    private final Logger logger = Logger.getLogger(DealerInfoCtr.class);
//
//    @Resource
//    private IDealerInfoSer dealerInfoSer;
//
//    @Resource
//    private DealerTypeCache dealerTypeCache;
//
//    @Resource
//    private SystemInfoCache systemInfoCache;
//
//    @Resource
//    private CityInfoCache cityInfoCache;
//
//    @Resource
//    private IdTypeCache idTypeCache;
//
//    @Resource
//    private BankCodeCache bankCodeCache;
//
//    @Resource
//    private DealerInfoCache dealerInfoCache;
//
//    @Resource
//    private DealerPrivilegeCache dealerPrivilegeCache;
//
//    @Resource
//    private GameInfoCache gameInfoCache;
//
//    /**
//     * 跳转到代销商注册页面
//     *
//     * @return
//     */
//    @RequestMapping(value = "/2add")
//    public String toadd() {
//        return "/businessRole/DealerRegister";
//    }
//
//    /**
//     * 注册代销商
//     *
//     * @param request
//     * @param resModel
//     * @param df
//     * @return
//     */
//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> addDealerInfo(HttpServletRequest request, Model resModel, DealerInfo df) {
//        df.setRegist_time(new Date());
//        df.setWork_status(WorkStatusDefine.work);
//
//        //代销商的特权游戏信息
//        String dealerPrivilegeStr = request.getParameter("dealerPrivilegeStr");
//        JSONArray dealerPrivilegeJson = JSONArray.fromObject(dealerPrivilegeStr);
//        List<DealerPrivilege> dpList = new ArrayList<DealerPrivilege>();
//        for (int i = 0; i < dealerPrivilegeJson.size(); i++) {
//            JSONObject obj = dealerPrivilegeJson.getJSONObject(i);
//            int game_id = Integer.parseInt((String) (obj.get("game_id")));
//            String service_proportion = (String) (obj.get("service_proportion"));
//            int game_permit = Integer.parseInt((String) (obj.get("game_permit")));
//            DealerPrivilege dp = new DealerPrivilege();
//            dp.setDealer_id(df.getDealer_id());
//            dp.setGame_id(game_id);
//            dp.setService_proportion(StringUtil.parseString(service_proportion));
//            dp.setGame_permit(game_permit);
//            dpList.add(dp);
//        }
//
//        int re = dealerInfoSer.addDealerInfo(df, dpList);
//        logger.error("dealer info re:" + re);
//        Map<String, Object> resMap = new HashMap<String, Object>();
//        if(re == -5){
//            resMap.put("result", "该代销商编号已存在");
//        } else if (re == -1) {
//            resMap.put("result", "代销商失败");
//        } else if (re == -2) {
//            resMap.put("result", "代销商特权失败");
//        } else if (re == -3) {
//            resMap.put("result", "代销商失败");
//        } else if (re == -4) {
//            resMap.put("result", "代销商特权失败");
//        } else if (re == 0) {
//            resMap.put("result", "注册成功");
//        }
//        return resMap;
//    }
//
//    /**
//     * 跳转到代销商游戏特权的查询页面
//     *
//     * @param request
//     * @param resModel
//     * @return
//     */
//    @RequestMapping(value = "/dealerPrivilegeListPage")
//    public String toDealerPrivilegePage(HttpServletRequest request, Model resModel) {
//        return "/businessRole/dealerPrivilegeList";
//    }
//
//    /**
//     * 跳转到代销商修改页面
//     *
//     * @return
//     */
//    @RequestMapping(value = "/2modify", method = RequestMethod.GET)
//    public String tomodify(HttpServletRequest request, Model resModel) {
//        String dealerId = ServletRequestUtils.getStringParameter(request, "dealerId", "");
//        DealerInfo di = dealerInfoCache.getDealerInfoById(dealerId);
//        resModel.addAttribute("dealerInfo", di);
//        List<DealerPrivilege> dpList = dealerPrivilegeCache.getDealerPrivilegeById(dealerId);
//        resModel.addAttribute("dpList", dpList);
//        return "/businessRole/DealerModify";
//    }
//
//    /**
//     * 从缓存中查询代销商信息列表数据，并返回给列表页面
//     *
//     * @param request
//     * @param resModel
//     * @return
//     */
//    @RequestMapping(value = "/select")
//    public String toSelect(HttpServletRequest request, Model resModel) {
//        List<DealerInfo> diList = dealerInfoCache.getDealerInfoList();
//        if (diList == null) {
//            logger.error("dealer info is null");
//            return "/businessRole/DealerList";
//        }
//        for (DealerInfo di : diList) {
//            di.setDealer_type_name(dealerTypeCache.getDealerTypeById(di.getDealer_type()).getDealer_type_name());
//            di.setProvince_name(systemInfoCache.getSystemInfoByid(di.getProvince_id()).getProvince_name());
//            di.setCity_name(cityInfoCache.getCityInfoByid(di.getProvince_id(), di.getCity_id()).getCity_name());
//            di.setId_type_name(idTypeCache.getIdTypeById(di.getId_type_id()).getId_type_name());
//            di.setBank_name(bankCodeCache.getBankCodeById(di.getBank_id()).getBank_name());
//        }
//        resModel.addAttribute("dealerInfoList", diList);
//        return "/businessRole/DealerList";
//    }
//
//    /**
//     * 根据代销商编号从缓存中查询出所对应的游戏特权列表
//     *
//     * @param request
//     * @param resModel
//     * @return
//     */
//    @RequestMapping(value = "/selectDealerPrivilegeList", method = RequestMethod.POST)
//    @ResponseBody
//    public List<DealerPrivilege> toSelectDealerPrivilegeList(HttpServletRequest request, Model resModel) {
//        String dealerId = ServletRequestUtils.getStringParameter(request, "dealerId", "");
//        List<DealerPrivilege> dpList = dealerPrivilegeCache.getDealerPrivilegeById(dealerId);
//        for (DealerPrivilege dp : dpList) {
//            dp.setGame_name(gameInfoCache.getGameInfoByid(dp.getGame_id()).getGame_name());
//        }
//        return dpList;
//    }
//
//    /**
//     * 修改代销商
//     *
//     * @param request
//     * @param resModel
//     * @param df
//     * @return
//     */
//    @RequestMapping(value = "/modify", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> modifyDealerTypeInfo(HttpServletRequest request, Model resModel, DealerInfo df) {
//        df.setRegist_time(new Date());
//
//        //代销商的特权游戏信息
//        String dealerPrivilegeStr = request.getParameter("dealerPrivilegeStr");
//        JSONArray dealerPrivilegeJson = JSONArray.fromObject(dealerPrivilegeStr);
//        List<DealerPrivilege> dpList = new ArrayList<DealerPrivilege>();
//        for (int i = 0; i < dealerPrivilegeJson.size(); i++) {
//            JSONObject obj = dealerPrivilegeJson.getJSONObject(i);
//            int game_id = Integer.parseInt((String) (obj.get("game_id")));
//            String service_proportion = (String) (obj.get("service_proportion"));
//            int game_permit = Integer.parseInt((String) (obj.get("game_permit")));
//            BigDecimal bd = new BigDecimal(service_proportion);
//            bd = bd.setScale(4, BigDecimal.ROUND_HALF_UP);
//            DealerPrivilege dp = new DealerPrivilege();
//            dp.setDealer_id(df.getDealer_id());
//            dp.setGame_id(game_id);
//            dp.setService_proportion(bd);
//            dp.setGame_permit(game_permit);
//            dpList.add(dp);
//        }
//
//        int re = dealerInfoSer.updateDealerInfo(df, dpList);
//        logger.error("update dealer info re:" + re);
//        Map<String, Object> resMap = new HashMap<String, Object>();
//        if (re == -1) {
//            resMap.put("result", "代销商失败");
//        } else if (re == -2) {
//            resMap.put("result", "代销商特权失败");
//        } else if (re == -3) {
//            resMap.put("result", "代销商特权失败");
//        } else if (re == -4) {
//            resMap.put("result", "修改失败");
//        } else if (re == 0) {
//            resMap.put("result", "修改成功");
//        }
//        return resMap;
//    }
//
//    /**
//     * 从缓存中获取代销商类型列表数据
//     */
//    @RequestMapping(value = "/selectDealerTypeList", method = RequestMethod.POST)
//    @ResponseBody
//    public List<DealerType> selectDealerTypeList(HttpServletRequest request, Model resModel) {
//        logger.info("select dealer type list from cache");
//        List<DealerType> list = dealerTypeCache.getDealerTypeList();
//        return list;
//    }
//
//    /**
//     * 从缓存中获取省系统参数列表数据
//     */
//    @RequestMapping(value = "/selectSystemInfoList", method = RequestMethod.POST)
//    @ResponseBody
//    public List<SystemInfo> selectSystemInfoList(HttpServletRequest request, Model resModel) {
//        logger.info("select SystemInfo list from cache");
//        List<SystemInfo> list = systemInfoCache.getSystemInfoList();
//        return list;
//    }
//
//    /**
//     * 从缓存中获取二级城市列表数据
//     */
//    @RequestMapping(value = "/selectCityInfoList", method = RequestMethod.POST)
//    @ResponseBody
//    public List<CityInfo> selectCityInfoList(HttpServletRequest request, Model resModel) {
//        logger.info("select CityInfo list from cache");
//        List<CityInfo> list = cityInfoCache.getCityInfoList();
//        return list;
//    }
//
//    /**
//     * 根据省编号从缓存中获取该省下的二级城市的列表数据
//     */
//    @RequestMapping(value = "/selectCityInfoListByProvinceId", method = RequestMethod.POST)
//    @ResponseBody
//    public List<CityInfo> selectCityInfoListByProvinceId(HttpServletRequest request, Model resModel) {
//        int provinceId = ServletRequestUtils.getIntParameter(request, "provinceId", 0);
//        logger.info("select CityInfo list from cache where provinceId = " + provinceId);
//        List<CityInfo> list = cityInfoCache.getCityInfoListByProvinceId(provinceId);
//        return list;
//    }
//
//    /**
//     * 从缓存中获取证件类型列表数据
//     */
//    @RequestMapping(value = "/selectIdTypeList", method = RequestMethod.POST)
//    @ResponseBody
//    public List<IdType> selectIdTypeList(HttpServletRequest request, Model resModel) {
//        logger.info("select IdType list from cache");
//        List<IdType> list = idTypeCache.getIdTypeList();
//        return list;
//    }
//
//    /**
//     * 从缓存中获取银行编码列表数据
//     */
//    @RequestMapping(value = "/selectBankCodeList", method = RequestMethod.POST)
//    @ResponseBody
//    public List<BankCode> selectBankCodeList(HttpServletRequest request, Model resModel) {
//        logger.info("select BankCode list from cache");
//        List<BankCode> list = bankCodeCache.getBankCodeList();
//        return list;
//    }
//
//}
