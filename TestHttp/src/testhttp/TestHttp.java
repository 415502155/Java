package testhttp;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author chenliping
 */
public class TestHttp {

    public static HttpClient httpClient;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            PoolingClientConnectionManager cm = new PoolingClientConnectionManager();
            cm.setMaxTotal(20000);
            cm.setDefaultMaxPerRoute(20000);
            httpClient = new DefaultHttpClient(cm);

            String url = "http://192.168.20.33:8609/";
            int rhreadNu = 20000;
            final int eachnum = 1000;

            ExecutorService exec = Executors.newCachedThreadPool();//创造一个管理非固定数量的线程池,线程一旦结束一段时间,则销毁.
            final Semaphore semp = new Semaphore(rhreadNu);// threadNu个线程可以同时访问
            final Count c = new Count();
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < rhreadNu; i++) {
                final HttpGet httpget = new HttpGet(url);
                Runnable run = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            semp.acquire();
                        } catch (InterruptedException ex) {
                            c.addfailcount();
                            System.out.println(ex);
                            return;
                        }
                        for (int j = 0; j < eachnum; j++) {
                            try {
                                HttpResponse response = TestHttp.httpClient.execute(httpget, new BasicHttpContext());
                                HttpEntity entity = response.getEntity();
                                EntityUtils.consume(entity);
                            } catch (IOException e) {
                                httpget.abort();
                                c.addfailcount();
                                System.out.println(e);
                                return;
                            }
                        }
                        semp.release();
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
            System.out.println("total:" + rhreadNu * eachnum + "\tsuccessful:" + c.getCount() + "\tfailed:" + c.getFailcount() + "\ttotalTime:" + getTime(difference) + "\tsec:" + difference / 1000);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
    }

    /**
     * 计算时间差
     *
     * @param tm
     * @return
     */
    public static String getTime(long tm) {
        int ms = (int) (tm % 1000);
        tm /= 1000;
        int sc = (int) (tm % 60);
        tm /= 60;
        int mn = (int) (tm % 60);
        tm /= 60;
        int hr = (int) (tm % 24);
        long dy = tm / 24;
        return dy + " days " + hr + " hours " + mn + " minutes " + sc + "." + ms + " sec ";
    }
}
