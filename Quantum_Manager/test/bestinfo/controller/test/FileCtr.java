//package bestinfo.controller.test;
//
//import com.bestinfo.define.filepath.FilePath;
//import javax.servlet.http.HttpServletRequest;
//import org.apache.log4j.Logger;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//
///**
// *
// * @author chenliping
// */
//@Controller
//@RequestMapping("/filepath")
//public class FileCtr {
//     private final Logger logger = Logger.getLogger(FileCtr.class);
//
////    @Autowired
////    private Filepath file;
//
//    @RequestMapping(value = "/index")
//    public void index(HttpServletRequest request, Model resModel) {
//        logger.info("上传中彩数据路径："+FilePath.getZCDataPath());
//        logger.info("游戏参数路径："+FilePath.getGameParaFileName(1));
//        logger.info("省通知路径："+FilePath.getProvinceOrCityNoticeFileName(0,"2014", "20140901", 1, "通知1"));
//        logger.info("地市通知路径："+FilePath.getProvinceOrCityNoticeFileName(1, "2014", "20140901", 1, "通知1"));
//        logger.info("终端通知路径："+FilePath.getTmnNoticeFileName(31071122, "2014", "20140901", 1, "通知1"));
//        logger.info("开奖号码路径："+FilePath.getOpenNumFileName(1, 1, 1, 1, 2, 88));
//        logger.info("开奖公告路径："+FilePath.getOpenNumFileName(2, 1, 1, 1, 2, 88));
//        logger.info("投注机软件包路径："+FilePath.getTmnPkg(2, 11, "11.00"));
//        logger.info("彩票公告路径："+FilePath.getLotteryBulletinPath());
//    }
//
//}
