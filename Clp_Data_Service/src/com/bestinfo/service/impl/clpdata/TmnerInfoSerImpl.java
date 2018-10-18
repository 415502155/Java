/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.service.impl.clpdata;

import com.bestinfo.bean.business.TmnInfo;
import com.bestinfo.dao.clpdata.ITmnerInfoDao;
import com.bestinfo.service.clpdata.ITmnerInfoSer;
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
public class TmnerInfoSerImpl implements ITmnerInfoSer{
    private static final Logger logger = Logger.getLogger(TmnerInfoSerImpl.class);
    @Resource
    private JdbcTemplate termJdbcTemplate;
    @Resource
    private JdbcTemplate metaJdbcTemplate;
    @Resource
    private ITmnerInfoDao itmn;

    @Override
    public List<TmnInfo> getITmnInfoList() {
            return itmn.getTmnInfoList(termJdbcTemplate);
    }

    @Override
    public int getITmnInfoSum() {
        return itmn.getTmnInfoSum(termJdbcTemplate);
    }
 
}
