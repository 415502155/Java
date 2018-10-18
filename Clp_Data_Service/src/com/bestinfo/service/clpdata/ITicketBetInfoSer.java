/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.service.clpdata;
import com.bestinfo.bean.ticket.TicketBet;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public interface ITicketBetInfoSer {
     public List<TicketBet> getTicketBetsList(Integer game_id,Integer draw_id);
     public List<TicketBet> getTicketInfoPage(Integer game_id,Integer draw_id,Integer ends,Integer starts);
     public int getTicketInfoCount(Integer game_id,Integer draw_id);
     public int getTicketBetInfoMaxSequenceId(Integer game_id,Integer draw_id);
     
      public List<TicketBet> getTicketInfos(Integer start,Integer rownum);
     
}
