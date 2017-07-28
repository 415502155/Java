/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.service.clpdata;

import com.bestinfo.bean.clpdata.TmnClpKey;
import java.util.Date;
import java.util.List;
/**
 *
 * @author Administrator
 */
public interface ITmnClpKeySer {
     
     public List<TmnClpKey> tmnClpkeyList();
     public List<TmnClpKey> tmnClpkeyListByDate(String time);
     public int getOneDayTnmClpKeyCount(String time);
     public TmnClpKey getClpKey(Integer terminalId);
     public int getTnmClpKeyCount();
     public int getAllTnmClpKeyCount();
     
      public List<TmnClpKey> tmnClpkeyList(Integer start,Integer end);
      public List<TmnClpKey> getTmnClpkeyListByTms(Integer start,Integer end);
}
