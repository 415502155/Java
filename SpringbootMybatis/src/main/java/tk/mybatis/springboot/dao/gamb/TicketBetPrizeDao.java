package tk.mybatis.springboot.dao.gamb;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import tk.mybatis.springboot.model.TicketBetPrize;

@Mapper
public interface TicketBetPrizeDao {
	@Select("select a.bet_time,a.terminal_id,a.bet_money,a.bet_line,b.prize_detail,c.draw_name,b.common_prize \r\n" + 
			"from t_ticket_bet a ,t_ticket_prize b,t_game_draw_info c where a.cipher=b.cipher and a.game_id=c.game_id and a.draw_id=c.draw_id \r\n" + 
			"and a.cipher = #{cipher} ")
	public TicketBetPrize getTicketBetPrizeByCipher(@Param("cipher")String cipher);
	
	public List<TicketBetPrize> getTicketBetPrizeByTmnid(@Param("terminalId")int terminalId);

}
