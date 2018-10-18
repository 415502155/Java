/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.service.clpdata;

import com.bestinfo.bean.game.GameDrawInfo;
import java.util.List;


/**
 *
 * @author Administrator
 */
public interface IGameDrawInfoSer {
//     public List<Map> getTicketBetsList(Integer game_id,Integer draw_id);
//     public List<TicketBet> getTicketInfoPage(Integer game_id,Integer draw_id,Integer ends,Integer starts);
//     public int getTicketInfoCount(Integer game_id,Integer draw_id);
//     public int getTicketBetInfoMaxSequenceId(Integer game_id,Integer draw_id);
    public List<GameDrawInfo> getGameDrawInfoList(Integer game_id,Integer draw_id);
    public int getGameDrawInfoStatus(Integer game_id,Integer draw_id);
}
