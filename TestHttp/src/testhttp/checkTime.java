package testhttp;

import com.bestinfo.arithmetic.Crypt3;
import com.bestinfo.terminal.client.protocols.CheckTime;
import com.bestinfo.terminal.client.struct.HeaderMessage;
import com.bestinfo.terminal.client.struct.Message;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import org.apache.commons.codec.binary.Hex;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import static testhttp.TestHttp.getTime;
import static testhttp.TestHttp.httpClient;

/**
 *
 * @author chenliping
 */
public class checkTime {
    
    private static final Logger logger = Logger.getLogger(checkTime.class);
    
    public static String TimerKey = "7e45569e347896dc037bd41f1e392e9a45d0c946e069e678";
    public static String TimerKeyInv = "ecd7c43d24bc5dcd";
    public static boolean a = true;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String url = "http://192.168.20.22:8600/terminal/protocol?type=1&version=1&Identity=0&length=10&action=3001";
        int rhreadNu = 2000;
        final int eachnum = 1000;
        PoolingClientConnectionManager cm = new PoolingClientConnectionManager();
        cm.setMaxTotal(20000);
        cm.setDefaultMaxPerRoute(20000);
        httpClient = new DefaultHttpClient(cm);
        try {
            final byte[] randomKey = new byte[32];
            new Random().nextBytes(randomKey);
            final Crypt3 crypt = new Crypt3();
            final CheckTime reqR = new CheckTime();
            final byte[] sendmsg = messreq(reqR, randomKey, crypt);
            if (sendmsg == null) {
                System.out.println("sendmsg error");
                return;
            }
            
            ExecutorService exec = Executors.newCachedThreadPool();//创造一个管理非固定数量的线程池,线程一旦结束一段时间,则销毁.
            final Semaphore semp = new Semaphore(rhreadNu);// threadNu个线程可以同时访问
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < rhreadNu; i++) {
                final HttpPost httppost = new HttpPost(url);
                final int th = i;
                System.out.println(i);
                Runnable run = new Runnable() {
                    @Override
                    public void run() {
                        for (int j = 0; j < eachnum; j++) {
                            int re = mess(th, httppost, reqR, randomKey, crypt, sendmsg);
                            if (re == -1) {
                                System.out.println(1);
                                return;
                            }
                        }
                    }
                };
                exec.execute(run);
            }
            exec.shutdown();;
            while (!exec.isTerminated()) {
                exec.awaitTermination(500, TimeUnit.MILLISECONDS);
            }
            long endTime = System.currentTimeMillis();
            long difference = endTime - startTime;
            System.out.println("total:" + rhreadNu * eachnum + "\ttotalTime:" + getTime(difference) + "\tsec:" + difference / 1000);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        
    }
    
    public static int mess(int th, HttpPost httppost, CheckTime reqR, byte[] randomKey, Crypt3 crypt, byte[] sendmsg) {
        ByteArrayEntity enty = new ByteArrayEntity(sendmsg);
        httppost.setEntity(enty);
        try {
            HttpResponse response = TestHttp.httpClient.execute(httppost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {                
                byte[] bytes = EntityUtils.toByteArray(entity);
                if (bytes.length == 0) {
                    System.out.println("from server is null");
                    return -2;
                }
                byte[] recive = crypt.pktDecryptTest(bytes, randomKey);
                if (recive == null) {
                    System.out.println("recive null");
//                    for (int i = 0; i < bytes.length; i++) {
//                        System.out.print(bytes[i]);
//                    }
                    System.out.println("1111111111111111111111111111\t"+new String(bytes, "UTF-8"));;
                    System.out.println("");
                    return -2;
                }
                //解析报文
                Message m = reqR.AnalysisPkg(recive);
                if (m == null) {
                    System.out.println("parser error");
                    return -3;
                }
                if (th == 0 && a) {
                    String b = "";
                    for (int i = 0; i < bytes.length; i++) {
                        b = b + String.valueOf(bytes[i]);
                    }
                    logger.error(b);
                    a = false;
                }
            }
            EntityUtils.consume(entity);
        } catch (Exception e) {
            httppost.abort();
            System.out.println(e);
            return -1;
        }
        return 0;
    }
    
    public static byte[] messreq(CheckTime reqR, byte[] randomKey, Crypt3 crypt) {
        try {
            HeaderMessage header = new HeaderMessage();
            header.setTerminalid(31010001);
            header.setPhysicsid(31010001);
            header.setSafeCard("0");
            header.setRecode(-1);
            header.setRemessage("");
            header.setSyncRoot(0);
            header.setSyncLeaf(0);
            header.setTerminalPwd("123456");
            header.setCounter(0);
            byte[] send = reqR.AssembleMsg(header, null);
            if (send == null) {
                return null;
            }
            send = crypt.pktEncryptTest(send, randomKey, Hex.decodeHex(TimerKey.toCharArray()), Hex.decodeHex(TimerKeyInv.toCharArray()));
            if (send == null) {
                return null;
            }
            return send;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    
}
