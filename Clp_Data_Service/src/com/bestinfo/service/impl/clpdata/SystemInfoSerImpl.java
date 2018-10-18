/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.service.impl.clpdata;

import com.bestinfo.bean.sysUser.SystemInfo;
import com.bestinfo.dao.clpdata.ISystemInfoDao;
import com.bestinfo.service.clpdata.ISystemInfoSer;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class SystemInfoSerImpl implements ISystemInfoSer{
    private static final Logger logger = Logger.getLogger(SystemInfoSerImpl.class);
    @Resource
    private JdbcTemplate termJdbcTemplate;
    @Resource
    private ISystemInfoDao is;
    
    @Override
    public List<SystemInfo> getISystemInfoList() {
        List<SystemInfo> list =is.getSystemInfoList(termJdbcTemplate);
        return list;
    }
 
}
