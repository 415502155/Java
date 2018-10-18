package com.bestinfo.controller.bank;

import com.bestinfo.bean.account.AccountBankfileDetail;
import com.bestinfo.bean.account.AccountBankfileSummary;
import com.bestinfo.controller.city.EBCityInfoController;
import com.bestinfo.dao.account.IAccountBankfileDetailDao;
import com.bestinfo.dao.account.IAccountBankfileSummaryDao;
import com.bestinfo.define.common.CodeCommon;
import com.bestinfo.util.TimeUtil;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author chenliping
 */
@Controller
@RequestMapping(value = "/BankFileFix")
public class BankFileFixController {

    private final Logger logger = Logger.getLogger(EBCityInfoController.class);

    @Resource
    private JdbcTemplate termJdbcTemplate;

    @Resource
    private IAccountBankfileSummaryDao accountBankfileSummaryDao;

    @Resource
    private IAccountBankfileDetailDao accountBankfileDetailDao;

    @RequestMapping(value = "/fixBank")
    @ResponseBody
    public Map<String, Object> FixBankFile(HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("code", "0");
        resMap.put("msg", "ok");
        String filename = request.getParameter("filename");
        String signFilename = request.getParameter("signFileName");
        File file1 = new File(filename);
        if (!file1.exists()) {
            resMap.put("code", "-1");
            resMap.put("msg", "对帐文件不存在！");
            return resMap;
        }
        File file2 = new File(signFilename);
        if (!file2.exists()) {
            resMap.put("code", "-2");
            resMap.put("msg", "签名文件不存在！");
            return resMap;
        }
        try {
            List<String> datContent = FileUtils.readLines(file1, "utf-8");
            String signContent = FileUtils.readFileToString(file2, "utf-8");
            String signStr = StringUtils.join(datContent.toArray(), "\r\n");
            if (!signStr.endsWith("\r\n")) {//如果不是以\r\n结尾则加上\r\n，用于做签名校验
                signStr += "\r\n";
            }
            //校验对账签名
            if (!checkSign(signContent, signStr, datContent.get(0))) {
                logger.error("sign check error, not equals.");
                resMap.put("code", "-3");
                resMap.put("msg", "签名验证错误！");
                return resMap;
            }

            String successTotalNum_ = datContent.get(0).substring(16, 26); //成功总笔数
            //判断成功总笔数和记录数量是否一致
            if (datContent.size() - 1 != Integer.parseInt(successTotalNum_.trim())) {
                logger.error(".dat file upload, total number and record number different");
                resMap.put("code", "-4");
                resMap.put("msg", "文件内容错误！");
            }
            AccountBankfileSummary abs = insertBankfileSummary(datContent.get(0), signContent);
            if (abs == null) {
                resMap.put("code", "-5");
                resMap.put("msg", "异常!");
                return resMap;
            }
            int checkMark = accountBankfileSummaryDao.selectCheckMark(termJdbcTemplate, abs.getBank_id(), abs.getAccount_date());
            if(checkMark == CodeCommon.yes){
                resMap.put("code", "-8");
                resMap.put("msg", "已对帐!");
                return resMap;
            }
            
            accountBankfileSummaryDao.deleteAccountBankfileSummary(termJdbcTemplate, abs);
            accountBankfileSummaryDao.insertAccountBankfileSummary(termJdbcTemplate, abs);

            String bankCode_ = datContent.get(0).substring(0, 8);     //银行代码
            List<AccountBankfileDetail> abds = new ArrayList<AccountBankfileDetail>();
            for (int i = 1; i < datContent.size(); i++) {
                String abdStr = datContent.get(i);
                AccountBankfileDetail abd = insertBankfileDetail(abdStr, bankCode_);
                if (abd == null) {
                    resMap.put("code", "-6");
                    resMap.put("msg", "异常!");
                    return resMap;
                }
                abds.add(abd);
            }
            accountBankfileDetailDao.insert_updateAccountBankfileDetail(termJdbcTemplate, abds);
//            accountBankfileDetailDao.insertAccountBankfileDetail(termJdbcTemplate, abds);

        } catch (Exception e) {
            logger.error("",e);
            resMap.put("code", "-7");
            resMap.put("msg", "异常!");
        }
        return resMap;
    }

    public boolean checkSign(String sign, String content, String topinfo) throws UnsupportedEncodingException {
        String successTotalTime_ = topinfo.substring(41, 55); //生成时间
        String signMd5 = DigestUtils.sha256Hex((content + successTotalTime_).getBytes("UTF-8")).toUpperCase();
//        logger.info(content + successTotalTime_);
//        logger.info("signMd5 sign: " + signMd5);
//        logger.info("signStr sign: " + sign);
        if (signMd5.equals(sign.toUpperCase())) {
            return true;
        }
        return false;
    }

    private AccountBankfileSummary insertBankfileSummary(String datContent, String signStr) {
        try {
            String bankCode_ = datContent.substring(0, 8).trim();     //银行代码
            String accountDate_ = datContent.substring(8, 16); //账务日期
            String successTotalNum_ = datContent.substring(16, 26); //成功总笔数
            String successTotalMoney_ = datContent.substring(26, 41); //成功总金额
            BigDecimal topMoney = new BigDecimal(successTotalMoney_.trim());
            topMoney = topMoney.divide(new BigDecimal(100));  //分转成元
            AccountBankfileSummary abs = new AccountBankfileSummary();
            abs.setAccount_date(TimeUtil.parseDate_YMD8(accountDate_.trim()));
            abs.setBank_id(bankCode_);
            abs.setCheck_mark(0);
            abs.setFile_sign(signStr);
            abs.setTotal_money(topMoney);
            abs.setTotal_record(Integer.parseInt(successTotalNum_.trim()));
            return abs;
        } catch (NumberFormatException e) {
            logger.error("", e);
            return null;
        } catch (ParseException e) {
            logger.error("", e);
            return null;
        }
    }

    private AccountBankfileDetail insertBankfileDetail(String abdStr, String bankCode) {
        try {
            AccountBankfileDetail abd = new AccountBankfileDetail();
            BigDecimal pay_money = new BigDecimal(abdStr.substring(8, 23).trim());
            pay_money = pay_money.divide(new BigDecimal(100));//分转成元        
            abd.setPay_money(pay_money);//缴款金额
            abd.setPay_time(TimeUtil.parseDate_YMD14(abdStr.substring(23, 37).trim()));//缴款时间
            abd.setAccount_date(TimeUtil.parseDate_YMD8(abdStr.substring(37, 45).trim()));//账务日期
            abd.setAccount_no(abdStr.substring(45, 80).trim()); //缴款账号
            abd.setPay_type(Integer.parseInt(abdStr.substring(80, 82).trim())); //缴款类型
            abd.setBank_serial_no(abdStr.substring(82, 110).trim());//银行流水号
            abd.setBank_id(bankCode.trim());
            abd.setAccount_id("0");
            abd.setTerminal_id(Integer.parseInt(abdStr.substring(0, 8).trim()));
            abd.setRefile_time(new Date());
            return abd;
        } catch (NumberFormatException e) {
            logger.error("", e);
            return null;
        } catch (ParseException e) {
            logger.error("", e);
            return null;
        }
    }

}
