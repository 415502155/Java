package tk.mybatis.springboot.util;

public class Reservoir {
	private int total;

    public Reservoir(int t) 
    {
        this.total = t;
    }

    /**
     * Thread safe method
     * serialized access to Booth.total
     */
    public synchronized boolean sellTicket() 
    {
        if(this.total > 0) {
            this.total = this.total - 1;
            return true; // successfully sell one
        }
        else {
            return false; // no more tickets
        }
    }
}
