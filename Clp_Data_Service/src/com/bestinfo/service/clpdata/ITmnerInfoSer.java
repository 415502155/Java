/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.service.clpdata;

import com.bestinfo.bean.business.TmnInfo;
import java.util.List;
/**
 *
 * @author Administrator
 */
public interface ITmnerInfoSer {
     public List<TmnInfo> getITmnInfoList();
     public int getITmnInfoSum();
}
