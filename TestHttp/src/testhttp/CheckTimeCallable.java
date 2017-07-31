package testhttp;

import java.util.concurrent.Callable;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;
import com.bestinfo.arithmetic.Crypt3;
import com.bestinfo.terminal.client.protocols.CheckTime;
import com.bestinfo.terminal.client.struct.HeaderMessage;
import com.bestinfo.terminal.client.struct.Message;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.util.EntityUtils;
import static testhttp.CheckTime2.TimerKey;
import static testhttp.CheckTime2.TimerKeyInv;
import static testhttp.CheckTime2.messreq;

public class CheckTimeCallable implements Callable<Object> {

    private Logger logger = Logger.getLogger(CheckTimeCallable.class);
    private CheckTime reqR = null;
    private HttpPost httpPost = null;
    private byte[] randomKey;
    private Crypt3 crypt = new Crypt3();
//    private byte[] sendmsg = messreq(reqR, randomKey, crypt);
     private byte[] sendmsg = null;

    @SuppressWarnings("unused")
    CheckTimeCallable() {
    }

    public CheckTimeCallable(HttpPost httpPost, CheckTime reqR, byte[] randomKey, Crypt3 crypt, byte[] sendmsg) {
        this.httpPost = httpPost;
        this.reqR = reqR;
        this.randomKey = randomKey;
        this.crypt = crypt;
        this.sendmsg = sendmsg;
    }

    @Override
    public Object call() throws Exception {
        return execCheckTime(httpPost, reqR, randomKey, crypt, sendmsg);
    }

    private String execCheckTime(HttpPost httpPost, CheckTime reqR, byte[] randomKey, Crypt3 crypt, byte[] sendmsg) throws DecoderException {
        mess(httpPost, reqR, randomKey, crypt, sendmsg);
        return String.valueOf(0);
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
                    return -2;
                }
                //解析报文
                Message m = reqR.AnalysisPkg(recive);
                if (m == null) {
                    return -3;
                }
//                System.out.println("=============="+m.getHead().getRecode());
            }
            EntityUtils.consume(entity);
        } catch (Exception e) {
            httppost.abort();
            System.out.println("e"+e);
            return -1;
        }
        return 0;
    }

}
