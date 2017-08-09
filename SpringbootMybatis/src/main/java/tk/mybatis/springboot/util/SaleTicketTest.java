package tk.mybatis.springboot.util;

public class SaleTicketTest {
    public static void main(String[] args)
    {
        
    	Reservoir r = new Reservoir(100);
        Booth b1 = new Booth(r);
        Booth b2 = new Booth(r);
        Booth b3 = new Booth(r);
    }
}
