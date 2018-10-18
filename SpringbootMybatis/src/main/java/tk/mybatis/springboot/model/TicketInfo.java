package tk.mybatis.springboot.model;

public class TicketInfo {
	
	private int t_id;
	private int ticket_num;
	private int ticket_money;
	private String ticket_name;
	private int ticket_no;//primary id
	private int ticket_type;
	public int getTicket_type() {
		return ticket_type;
	}
	public void setTicket_type(int ticket_type) {
		this.ticket_type = ticket_type;
	}
	public int getT_id() {
		return t_id;
	}
	public void setT_id(int t_id) {
		this.t_id = t_id;
	}
	public int getTicket_num() {
		return ticket_num;
	}
	public void setTicket_num(int ticket_num) {
		this.ticket_num = ticket_num;
	}
	public int getTicket_money() {
		return ticket_money;
	}
	public void setTicket_money(int ticket_money) {
		this.ticket_money = ticket_money;
	}
	public String getTicket_name() {
		return ticket_name;
	}
	public void setTicket_name(String ticket_name) {
		this.ticket_name = ticket_name;
	}
	public int getTicket_no() {
		return ticket_no;
	}
	public void setTicket_no(int ticket_no) {
		this.ticket_no = ticket_no;
	}
	
	
}
