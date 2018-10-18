//package bestinfo.controller.test;
//
//import com.bestinfo.fsclient.protocols.body.syn.SyncGameInfoImpl1;
//import com.bestinfo.fsclient.protocols.struct.MsgHead;
//import com.bestinfo.socketpool.SockIOPool;
//import java.io.IOException;
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import org.apache.log4j.Logger;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.ServletRequestUtils;
//import org.springframework.web.bind.annotation.RequestMapping;
//
///**
// *
// * @author yangyuefu
// */
//@Controller
//@RequestMapping("/socket")
//public class SocketController {
//
//    Logger logger = Logger.getLogger(SocketController.class);
//
//    @Resource
//    private SockIOPool socketpool;
//
//    @RequestMapping(value = "send")
//    public void testSocket(HttpServletRequest request) {
//        logger.info("test socket");
//        String key = ServletRequestUtils.getStringParameter(request, "key", "");
//        String content = ServletRequestUtils.getStringParameter(request, "content", "default") + "\r\n";
//        //SockIOPool.SockIO sock = SockIOPool.getInstance().getSock(key);
//        SockIOPool.SockIO sock = socketpool.getSock(key);
//        try {
//            logger.info(sock);
//            sock.write((content).getBytes());
//            sock.flush();
//            logger.info("client received: " + sock.readLine());
//        } catch (IOException ioe) {
//            logger.error("", ioe);
//        }
//        sock.close();
//    }
//
//    @RequestMapping(value = "fsclient")
//    public void fsClient(HttpServletRequest request) {
//        logger.info("test fsclient");
//        String key = ServletRequestUtils.getStringParameter(request, "key", "");
//        MsgHead mHead = new MsgHead();
//        mHead.setPkt_len(1);
//        mHead.setPkt_type(1);//报文分类
//        mHead.setMajor_ver_num(0);//协议主版本号
//        mHead.setMinor_ver_num(0);//协议子版本号
//        mHead.setIdt_type(0);//身份类型
//        mHead.setIdt_id(0);//身份编号
//        mHead.setCred_id(0);//证书编号
//        mHead.setResp_code(0);//返回代码
//        mHead.setReserve(new byte[1]);//保留域
//        SyncGameInfoImpl1 client = new SyncGameInfoImpl1();
//        client.call(mHead,"socketpool1",key);
//    }
//}
