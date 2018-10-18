/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.controller.qrcode;

import com.bestinfo.bean.business.TmnInfo;
import com.bestinfo.bean.clpdata.TmnClpKey;
import com.bestinfo.bean.sysUser.SystemInfo;
import static com.bestinfo.controller.qrcode.RsaExample.getSignature;
import static com.bestinfo.controller.qrcode.UkeyAliasId.getKey;
import com.bestinfo.service.clpdata.ISystemInfoSer;
import com.bestinfo.service.clpdata.ITmnClpKeySer;
import com.bestinfo.service.clpdata.ITmnerInfoSer;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.ParseException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Administrator
 */
@Controller
@RequestMapping(value = "/test")
public class Test {
    private final Logger logger = Logger.getLogger(Test.class);
    @RequestMapping(value = "/demo")
    @ResponseBody
    public String DownFiles(){
        logger.info("**********************************************************");
    return "success";
    }
}
