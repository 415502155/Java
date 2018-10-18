//package com.bestinfo.controller.business;
//
//import bestinfo.system.WorkStatusDefine;
//import bestinfo.terminal.TmnGameStop;
//import bestinfo.terminal.TmnInitTime;
//import com.bestinfo.bean.account.AccountInfo;
//import com.bestinfo.bean.business.DealerInfo;
//import com.bestinfo.bean.business.TmnInfo;
//import com.bestinfo.bean.business.TmnPrivilege;
//import com.bestinfo.bean.encoding.DistrictInfo;
//import com.bestinfo.bean.encoding.TerminalSoftware;
//import com.bestinfo.bean.game.GameInfo;
//import com.bestinfo.dao.page.Pagination;
//import com.bestinfo.ehcache.CityInfoCache;
//import com.bestinfo.ehcache.DistrictInfoCache;
//import com.bestinfo.ehcache.TerminalSoftwareCache;
////import com.bestinfo.ehcache.business.DealerInfoCache;
//import com.bestinfo.ehcache.game.GameInfoCache;
//import com.bestinfo.ehcache.system.SystemInfoCache;
//import com.bestinfo.redis.business.TmnInfoRedisCache;
//import com.bestinfo.redis.business.TmnPrivilegeRedisCache;
//import com.bestinfo.service.account.IAccountInfoSer;
//import com.bestinfo.service.business.ITmnInfoSer;
//import com.bestinfo.util.StringUtil;
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
// * 投注机信息
// *
// * @author hhhh
// */
//@Controller
//@RequestMapping(value = "/tmninfo")
//public class TmnInfoCtr {
//
//    private final Logger logger = Logger.getLogger(TmnInfoCtr.class);
//
//    @Resource
//    private ITmnInfoSer tinfo;
//
////    @Resource
////    private DealerInfoCache dealerInfoCache;
//
//    @Resource
//    private DistrictInfoCache districtInfoCache;
//
////    @Resource
////    private TmnInfoCache tmnInfoCache;
//    @Resource
//    private TmnInfoRedisCache tmnInfoRedisCache;
//
//    @Resource
//    private SystemInfoCache systemInfoCache;
//
//    @Resource
//    private CityInfoCache cityInfoCache;
//
//    @Resource
//    private GameInfoCache gameInfoCache;
//
//    @Resource
//    private TerminalSoftwareCache softwareCache;
//
//    @Resource
//    private IAccountInfoSer accountInfoSer;
//
//    @Resource
//    private TmnPrivilegeRedisCache tmnPrivilegeRedisCache;
//    
//    @RequestMapping(value = "/2add")
//    public String toadd(HttpServletRequest request, Model resModel) {
//        return "/businessRole/tmnRegister";
//    }
//
//    @RequestMapping(value = "/2privilegeAdd")
//    public String toPrivilegeAdd(HttpServletRequest request, Model resModel) {
//        int terminalId = ServletRequestUtils.getIntParameter(request, "terminalId", 0);
//        resModel.addAttribute("terminalId", terminalId);
//        List<GameInfo> giList = tinfo.resetGameInfoList();
//        resModel.addAttribute("giList", giList);
//        return "/businessRole/tmnPrivilege";
//    }
//
//    @RequestMapping(value = "/2modifyPrivilege")
//    public String toPrivilegeModify(HttpServletRequest request, Model resModel) {
//        int terminalId = ServletRequestUtils.getIntParameter(request, "tmnId", 0);
//        resModel.addAttribute("terminalId", terminalId);
//        List<GameInfo> giList = gameInfoCache.getGameInfoListFrmCache();
//        List<TmnPrivilege> tpList = tmnPrivilegeRedisCache.getTmnPrivilegeList(terminalId);
//        Map<Integer, Object> map = new HashMap<Integer, Object>();
//        for (TmnPrivilege tmnPrivilege : tpList) {
//            map.put(tmnPrivilege.getGame_id(), tmnPrivilege);
//        }
//
//        List<TmnPrivilege> list = new ArrayList<TmnPrivilege>();
//        for (GameInfo gameInfo : giList) {
//            int gameId = gameInfo.getGame_id();
//            String gameName = gameInfo.getGame_name();
//            TmnPrivilege tp = new TmnPrivilege();
//            tp.setGame_id(gameId);
//            tp.setGame_name(gameName);
//            tp.setMax_bet_money(gameInfo.getTerminal_bet_money());
////            tp.setTerminal_id(terminalId);
//            if (map.get(gameId) != null) {
//                //回显
//                TmnPrivilege tp1 = (TmnPrivilege) map.get(gameId);
//                tp.setIsCheck(0);
//                tp.setSale_permit(tp1.getSale_permit());
//                tp.setCash_permit(tp1.getCash_permit());
//                tp.setGame_permit(tp1.getGame_permit());
//                tp.setUndo_permit(tp1.getUndo_permit());
//                tp.setPresell_permit(tp1.getPresell_permit());
//                tp.setGame_stop(tp1.getGame_stop());
//                tp.setAgent_fee_rate(tp1.getAgent_fee_rate());
//                tp.setCur_agent_rate(tp1.getCur_agent_rate());
//                tp.setMin_bet_money(tp1.getMin_bet_money());
//                tp.setMax_bet_money(tp1.getMax_bet_money());
//                tp.setMax_sales_money(tp1.getMax_sales_money());
//                tp.setCash_fee_rate(tp1.getCash_fee_rate());
//            } else {
//                tp.setIsCheck(1);
//            }
//
//            list.add(tp);
//        }
//        resModel.addAttribute("list", list);
//        return "/businessRole/tmnPrivilegeModify";
//    }
//
//    @RequestMapping(value = "/2modify", method = RequestMethod.GET)
//    public String toModify(HttpServletRequest request, Model resModel) {
//        int tmnId = ServletRequestUtils.getIntParameter(request, "tmnId", 0);
//        TmnInfo ti = tmnInfoRedisCache.getTmnInfoById(tmnId);
//        ti.setTerminal_value_str(ti.getTerminal_value().toString());
//        resModel.addAttribute("tmnInfo", ti);
//        int province_id = systemInfoCache.getSystemInfoList().get(0).getProvince_id();
//        resModel.addAttribute("province_id", province_id);
//        return "/businessRole/tmnModify";
//    }
//
//    /**
//     * 终端信息登记
//     *
//     * @param request
//     * @param resModel
//     * @param tin
//     * @return
//     */
//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> addTmnInfo(HttpServletRequest request, Model resModel, TmnInfo tin) {
//        Map<String, Object> resMap = new HashMap<String, Object>();
//        //看所填账户在账户信息表中存不存在
//        String account_id = tin.getAccount_id();
//        AccountInfo accountInfo = accountInfoSer.getAccountInfoByAccountId(account_id);
//        if (accountInfo == null) {
//            logger.error("the account is not exist where account_id = " + account_id);
//            resMap.put("code", -1);
//            resMap.put("result", "所填默认扣款账户不存在");
//            return resMap;
//        }
//
//        int proid = ServletRequestUtils.getIntParameter(request, "province_id", 0);
//        tin.setTerminal_syn_no(1);//登记时终端同步字设置为1
//        tin.setUpgrade_mark(WorkStatusDefine.work);//升级标志
//        tin.setRegist_date(new Date());
//        tin.setTerminal_initial_time(TmnInitTime.NOTREGISTERED);//终端初始化时间初始化为"未注册"
//        tin.setSafe_card_id("0");//加密芯片设置初始值
//        tin.setTerminal_value(StringUtil.parseString(tin.getTerminal_value_str()));
//        tin.setPublic_key("0");
//        int re = tinfo.addTmnInfo(tin, proid);
//        logger.info("insert tmn info into DB and cache re:" + re);
//
//        if (re < 0) {
//            resMap.put("code", -2);
//            resMap.put("result", "注册失败");
//        } else {
//            resMap.put("code", 0);
//            resMap.put("result", "注册成功");
//            resMap.put("terminalId", tin.getTerminal_id());
//        }
//        return resMap;
//    }
//
//    @RequestMapping(value = "/update", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> updateTmnInfo(HttpServletRequest request, Model resModel, TmnInfo tin) {
//        Map<String, Object> resMap = new HashMap<String, Object>();
//        //看所填账户在账户信息表中存不存在
//        String account_id = tin.getAccount_id();
//        AccountInfo accountInfo = accountInfoSer.getAccountInfoByAccountId(account_id);
//        if (accountInfo == null) {
//            logger.error("the account is not exist where account_id = " + account_id);
//            resMap.put("code", -1);
//            resMap.put("result", "所填默认扣款账户不存在");
//            return resMap;
//        }
//
//        tin.setTerminal_syn_no(1);//登记时终端同步字设置为1
//        tin.setUpgrade_mark(WorkStatusDefine.work);//升级标志
//        tin.setRegist_date(new Date());
//        tin.setTerminal_initial_time(TmnInitTime.NOTREGISTERED);//终端初始化时间初始化为"未注册"
//        tin.setSafe_card_id("0");//加密芯片设置初始值
//        tin.setPublic_key("0");
//
//        int re = tinfo.updateTmnInfo(tin);
//        logger.info("update tmn info in DB and cache re:" + re);
//
//        if (re < 0) {
//            resMap.put("code", -2);
//            resMap.put("result", "修改失败");
//        } else {
//            resMap.put("code", 0);
//            resMap.put("result", "修改成功");
//        }
//        return resMap;
//    }
//
//    @RequestMapping(value = "/addTmnPrivilege", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> addTmnPrivilege(HttpServletRequest request, Model resModel) {
//        String tmnPrivilegeStr = request.getParameter("tmnPrivilegeStr");
//        JSONArray tmnPrivilegeJson = JSONArray.fromObject(tmnPrivilegeStr);
//        List<TmnPrivilege> tpList = new ArrayList<TmnPrivilege>();
//        for (int i = 0; i < tmnPrivilegeJson.size(); i++) {
//            JSONObject obj = tmnPrivilegeJson.getJSONObject(i);
//            TmnPrivilege tp = new TmnPrivilege();
//            tp.setTerminal_id(Integer.parseInt((String) obj.get("terminalId")));
//            tp.setGame_id(Integer.parseInt((String) obj.get("game_id")));
//            tp.setCur_draw_id(0);//当前期号暂时设置为0
//            tp.setSale_permit(Integer.parseInt((String) obj.get("sale_permit")));
//            tp.setCash_permit(Integer.parseInt((String) obj.get("cash_permit")));
//            tp.setGame_permit(Integer.parseInt((String) obj.get("game_permit")));
//            tp.setUndo_permit(Integer.parseInt((String) obj.get("undo_permit")));
//            tp.setPresell_permit(Integer.parseInt((String) obj.get("presell_permit")));
//            tp.setGame_stop(TmnGameStop.gamestopno);//默认开机
//            tp.setAgent_fee_rate(StringUtil.parseString((String) obj.get("agent_fee_rate")));
//            tp.setCur_agent_rate(StringUtil.parseString((String) obj.get("cur_agent_rate")));
//            tp.setMin_bet_money(StringUtil.parseString((String) obj.get("min_bet_money")));
//            tp.setMax_bet_money(StringUtil.parseString((String) obj.get("max_bet_money")));
//            tp.setMax_sales_money(StringUtil.parseString((String) obj.get("max_sales_money")));
//            tp.setCash_fee_rate(StringUtil.parseString((String) obj.get("cash_fee_rate")));
//            tpList.add(tp);
//        }
//        int re = tinfo.addTmnPrivilege(tpList);
//        logger.info("insert tmn privilege list into DB and cache re:" + re);
//        Map<String, Object> resMap = new HashMap<String, Object>();
//        if (re < 0) {
//            resMap.put("result", "注册失败");
//        } else {
//            resMap.put("result", "注册成功");
//        }
//        return resMap;
//    }
//
//    @RequestMapping(value = "/modifyTmnPrivilege", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> modifyTmnPrivilege(HttpServletRequest request, Model resModel) {
//        String tmnPrivilegeStr = request.getParameter("tmnPrivilegeStr");
////        logger.info("tmnPrivilegeStr: "+tmnPrivilegeStr);
//        String terminalId = request.getParameter("terminalId");
//        JSONArray tmnPrivilegeJson = JSONArray.fromObject(tmnPrivilegeStr);
//        List<TmnPrivilege> tpList = new ArrayList<TmnPrivilege>();
//        for (int i = 0; i < tmnPrivilegeJson.size(); i++) {
//            JSONObject obj = tmnPrivilegeJson.getJSONObject(i);
//            TmnPrivilege tp = new TmnPrivilege();
//            tp.setTerminal_id(Integer.parseInt((String) obj.get("terminalId")));
//            tp.setGame_id(Integer.parseInt((String) obj.get("game_id")));
//            tp.setCur_draw_id(0);//当前期号暂时设置为0
//            tp.setSale_permit(Integer.parseInt((String) obj.get("sale_permit")));
//            tp.setCash_permit(Integer.parseInt((String) obj.get("cash_permit")));
//            tp.setGame_permit(Integer.parseInt((String) obj.get("game_permit")));
//            tp.setUndo_permit(Integer.parseInt((String) obj.get("undo_permit")));
//            tp.setPresell_permit(Integer.parseInt((String) obj.get("presell_permit")));
//            tp.setGame_stop(TmnGameStop.gamestopno);//默认开机
//            tp.setAgent_fee_rate(StringUtil.parseString((String) obj.get("agent_fee_rate")));
//            tp.setCur_agent_rate(StringUtil.parseString((String) obj.get("cur_agent_rate")));
//            tp.setMin_bet_money(StringUtil.parseString((String) obj.get("min_bet_money")));
//            tp.setMax_bet_money(StringUtil.parseString((String) obj.get("max_bet_money")));
//            tp.setMax_sales_money(StringUtil.parseString((String) obj.get("max_sales_money")));
//            tp.setCash_fee_rate(StringUtil.parseString((String) obj.get("cash_fee_rate")));
//            tpList.add(tp);
//        }
//        int re = tinfo.modifyTmnPrivilege(tpList, Integer.parseInt(terminalId));
//        logger.info("update tmn privilege list from DB and cache re:" + re);
//        Map<String, Object> resMap = new HashMap<String, Object>();
//        if (re < 0) {
//            resMap.put("result", "修改失败");
//        } else {
//            resMap.put("result", "修改成功");
//        }
//        return resMap;
//    }
//
//    @RequestMapping(value = "/select")
//    public String getTmnInfo(HttpServletRequest request, Model resModel) {
//        int terid = ServletRequestUtils.getIntParameter(request, "terid", 0);
//        int terphyid = ServletRequestUtils.getIntParameter(request, "terphyid", 0);
//        int cityid = ServletRequestUtils.getIntParameter(request, "cityid", 0);
//        String stationName = ServletRequestUtils.getStringParameter(request, "statname", null);
//        String ownName = ServletRequestUtils.getStringParameter(request, "ownname", null);
//        int dealid = ServletRequestUtils.getIntParameter(request, "dealid", 0);
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("pageNumber", ServletRequestUtils.getIntParameter(request, "pageNumber", 1));
//        params.put("pageSize", ServletRequestUtils.getIntParameter(request, "pageSize", 10));
//
//        if (terid != 0) {
//            params.put("terminal_id", terid);
//        }
//        if (terphyid != 0) {
//            params.put("terminal_phy_id", terphyid);
//        }
//        if (cityid != 0) {
//            params.put("city_id", cityid);
//        }
//        if (StringUtil.notNull(stationName)) {
//            params.put("station_name", stationName);
//        }
//        if (StringUtil.notNull(ownName)) {
//            params.put("owner_name", ownName);
//        }
//        if (dealid != 0) {
//            params.put("dealer_id", dealid);
//        }
//        Pagination<TmnInfo> page = tinfo.getTmnInfoPageList(params);
//        if (null == page) {
//
//        }
//        resModel.addAttribute("page", page);
//
//        return "/businessRole/tmninfoSelect";
//    }
//
//    /**
//     * 从缓存中查询终端信息列表数据，并返回给列表页面
//     *
//     * @param request
//     * @param resModel
//     * @return
//     */
//    @RequestMapping(value = "/selectAll")
//    public String selectAllList(HttpServletRequest request, Model resModel) {
//        List<TmnInfo> tiList = tinfo.selectTmnInfoList();
//        if (tiList == null || tiList.isEmpty()) {
//            logger.error("there is no data in t_tmn_info");
//            //给前端返回提示
//        }
//        int province_id = systemInfoCache.getSystemInfoList().get(0).getProvince_id();
//        for (TmnInfo ti : tiList) {
//            ti.setCity_name(cityInfoCache.getCityInfoByid(province_id, ti.getCity_id()).getCity_name());
//            ti.setDistrict_name(districtInfoCache.getDistrictInfoByid(province_id, ti.getCity_id(), ti.getDistrict_id()).getDistrict_name());
//            ti.setSoftware_name(softwareCache.getTerminalSoftwareByid(ti.getSoftware_id()).getSoftware_name());
//        }
//        resModel.addAttribute("tmnInfoList", tiList);
//        return "/businessRole/tmnInfoList";
//    }
//
//    /**
//     * 根据地市、终端机号、代销商编号查询终端信息列表
//     *
//     * @param request
//     * @param resModel
//     * @return
//     */
//    @RequestMapping(value = "/selectByCondition", method = RequestMethod.GET)
//    public String selectListByCondition(HttpServletRequest request, Model resModel) {
//        int cityId = ServletRequestUtils.getIntParameter(request, "cityId", 0);
//        int tmnId = ServletRequestUtils.getIntParameter(request, "tmnId", 0);
//        String dealerId = ServletRequestUtils.getStringParameter(request, "dealerId", "");
//        List<TmnInfo> tiList = tinfo.getTmnListByCondition(cityId, tmnId, dealerId);
//        int province_id = systemInfoCache.getSystemInfoList().get(0).getProvince_id();
//        for (TmnInfo ti : tiList) {
//            ti.setCity_name(cityInfoCache.getCityInfoByid(province_id, ti.getCity_id()).getCity_name());
//            ti.setDistrict_name(districtInfoCache.getDistrictInfoByid(province_id, ti.getCity_id(), ti.getDistrict_id()).getDistrict_name());
//        }
//        resModel.addAttribute("tmnInfoList", tiList);
//        return "/businessRole/tmnInfoList";
//    }
//
//    /**
//     * 从缓存中获取代销商信息列表数据
//     */
//    @RequestMapping(value = "/selectDealerInfoList", method = RequestMethod.POST)
//    @ResponseBody
//    public List<DealerInfo> selectDealerInfoList(HttpServletRequest request, Model resModel) {
//        logger.info("select dealer info list from cache");
//        List<DealerInfo> list = dealerInfoCache.getDealerInfoList();
//        return list;
//    }
//
//    /**
//     * 根据代销商编号从缓存中获取代销商信息数据
//     */
//    @RequestMapping(value = "/selectDealerInfoById", method = RequestMethod.POST)
//    @ResponseBody
//    public DealerInfo selectDealerInfoById(HttpServletRequest request, Model resModel) {
//        logger.info("select dealer info from cache by dealer id");
//        String dealerId = ServletRequestUtils.getStringParameter(request, "dealerId", "");
//        DealerInfo di = dealerInfoCache.getDealerInfoById(dealerId);
//        return di;
//    }
//
//    /**
//     * 从缓存中根据省市编号获取三级区县列表数据
//     */
//    @RequestMapping(value = "/getDistrictInfoListByProvinceIdAndCityId", method = RequestMethod.POST)
//    @ResponseBody
//    public List<DistrictInfo> getDistrictInfoListByProvinceIdAndCityId(HttpServletRequest request, Model resModel) {
//        logger.info("select district info list from cache by province id and city id");
//        int provinceId = ServletRequestUtils.getIntParameter(request, "province_id", 0);
//        int cityId = ServletRequestUtils.getIntParameter(request, "city_id", 0);
//        List<DistrictInfo> list = districtInfoCache.getDistrictInfoListByProvinceIdAndCityId(provinceId, cityId);
//        return list;
//    }
//
//    /**
//     * 从缓存中获取软件类型列表数据
//     */
//    @RequestMapping(value = "/selectSoftwareTypeList", method = RequestMethod.POST)
//    @ResponseBody
//    public List<TerminalSoftware> selectSoftwareTypeList(HttpServletRequest request, Model resModel) {
//        logger.info("select TerminalSoftware list from cache");
//        List<TerminalSoftware> list = softwareCache.getTerminalSoftwareList();
//        return list;
//    }
//
//}
