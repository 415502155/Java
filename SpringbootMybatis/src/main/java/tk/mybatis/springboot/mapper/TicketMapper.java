package tk.mybatis.springboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import tk.mybatis.springboot.model.TicketInfo;

@Mapper
public interface TicketMapper {
	//select * from t_ticket_info a , t_sale_ticket_info b where a.t_id = b.t_id
	//SELECT * FROM t_ticket_info WHERE ticket_no = (select min(ticket_no) from t_ticket_info where ticket_type = 0)
	public int addTicket(@Param("tlist")List<TicketInfo> tlist) ;
	
	public int saleTicketByType();
	
	public int saleTicketByNum();
	
	public int countTicket();

}
