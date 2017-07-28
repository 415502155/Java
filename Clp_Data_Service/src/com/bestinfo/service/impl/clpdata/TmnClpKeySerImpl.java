/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.service.impl.clpdata;

import com.bestinfo.bean.clpdata.TmnClpKey;
import com.bestinfo.dao.clpdata.ITmnClpKeyDao;
import com.bestinfo.service.clpdata.ITmnClpKeySer;
import java.util.Date;
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
public class TmnClpKeySerImpl implements ITmnClpKeySer{
    private static final Logger logger = Logger.getLogger(TmnClpKeySerImpl.class);
    @Resource
    private JdbcTemplate termJdbcTemplate;
    @Resource
    private JdbcTemplate metaJdbcTemplate;
    @Resource
    private ITmnClpKeyDao itck;

    @Override
    public TmnClpKey getClpKey(Integer terminalId) {
        return itck.getTmnClpKey(termJdbcTemplate,terminalId);
    }

    @Override
    public int getTnmClpKeyCount() {
        return itck.getTmnClpKeyCount(termJdbcTemplate);
    }

    @Override
    public List<TmnClpKey> tmnClpkeyList() {
        return itck.getTmnClpkeyList(termJdbcTemplate);
    }

    @Override
    public List<TmnClpKey> tmnClpkeyListByDate(String time) {
        return itck.getTmnClpkeyListByDate(termJdbcTemplate,time);
    }

    @Override
    public int getAllTnmClpKeyCount() {
        return itck.getAllTnmClpKeyCount(termJdbcTemplate);
    }

    @Override
    public int getOneDayTnmClpKeyCount(String time) {
        return itck.getOneDayTnmClpKeyCount(termJdbcTemplate,time);
    }

    @Override
    public List<TmnClpKey> tmnClpkeyList(Integer start, Integer end) {
        return itck.getTmnClpkeyList(termJdbcTemplate, start, end);
    }

    @Override
    public List<TmnClpKey> getTmnClpkeyListByTms(Integer start, Integer end) {
        return itck.getTmnClpkeyListByTms(termJdbcTemplate, start, end);
    }

 
}
