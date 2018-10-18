package testhttp;

import com.bestinfo.arithmetic.Crypt3;
import com.bestinfo.terminal.client.protocols.CheckTime;
import com.bestinfo.terminal.client.struct.HeaderMessage;
import com.bestinfo.terminal.client.struct.Message;
import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.commons.codec.binary.Hex;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.util.EntityUtils;
import static testhttp.TestHttp.getTime;
import static testhttp.TestHttp.httpClient;

/**
 *
 *
 * @author chenliping
 */
public class CheckTime2 {

    public static String TimerKey = "7e45569e347896dc037bd41f1e392e9a45d0c946e069e678";
    public static String TimerKeyInv = "ecd7c43d24bc5dcd";
    private final ExecutorService executorService = Executors.newCachedThreadPool();
//    private List<Future<Object>> tasks = new ArrayList<Future<Object>>();

    private static String readString6(String prompt) {
        Console console = System.console();
        if (console == null) {
            throw new IllegalStateException("不能使用控制台");
        }
        return console.readLine(prompt);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        String str = readString6("thread num:");
//        String str1 = readString6("each num:");
//        String url = readString6("url:");
        String str = "20000";
        String str1 = "1000";
        String url = "http://192.168.20.31:8600/terminal/protocol?type=1&version=1&Identity=0&length=10&action=3001";
        
        System.out.println(str + "\n" + str1 + "\n" + url);
        List<Future<Object>> tasks = new ArrayList<Future<Object>>();
        int rhreadNu = Integer.parseInt(str);
        final int eachnum = Integer.parseInt(str1);
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
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < rhreadNu; i++) {
                System.out.println(i);
                HttpPost httppost = new HttpPost(url);
                for (int j = 0; j < eachnum; j++) {
                    CheckTimeCallable task = new CheckTimeCallable(httppost, reqR, randomKey, crypt, sendmsg);
                    Future<Object> future = new CheckTime2().executorService.submit(task);
                    tasks.add(future);
                }
            }

            for (Future<Object> future : tasks) {
                try {
                    while (true) {
                        if (future.isDone() && !future.isCancelled()) {
                            break;
                        } else {
                            Thread.sleep(10);
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e);
                    e.printStackTrace();
                }
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

    public static int mess(HttpPost httppost, CheckTime reqR, byte[] randomKey, Crypt3 crypt, byte[] sendmsg) {
        ByteArrayEntity enty = new ByteArrayEntity(sendmsg);
        httppost.setEntity(enty);
        try {
            HttpResponse response = TestHttp.httpClient.execute(httppost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                byte[] bytes = EntityUtils.toByteArray(entity);
                byte[] recive = crypt.pktDecryptTest(bytes, randomKey);
                if (recive == null) {
                    System.out.println("recive error");
                    return -2;
                }
                //解析报文
                Message m = reqR.AnalysisPkg(recive);
                if (m == null) {
                    System.out.println("parser error");
                    return -3;
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
