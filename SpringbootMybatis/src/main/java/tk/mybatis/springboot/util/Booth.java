package tk.mybatis.springboot.util;

public class Booth extends Thread{
	private static int threadID = 0; // owned by Class object

    private Reservoir release;      // sell this reservoir 
    private int count = 0;          // owned by this thread object
    /**
     * constructor
     */
    public Booth(Reservoir r) {
        //super("ID:" + (++threadID));
        this.release = r;          // all threads share the same reservoir
        this.start();
    }

    /**
     * convert object to string
     */
    public String toString() {
        return super.getName();
    }

    /**
     * what does the thread do?
     */
    public void run() {
        while(true) {
            if(this.release.sellTicket()) {
                this.count = this.count + 1;
                System.out.println(this.getName() + ": sell 1");
                try {
                    sleep((int) Math.random()*100);   // random intervals
                }
                catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                break;
            }
        }
        System.out.println(this.getName() + " I sold:" + count);
    }
}
