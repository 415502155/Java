package com.bestinfo.gambler.createBetNumber;

import com.bestinfo.gambler.all.CommTool;
import com.bestinfo.gambler.all.StaticVariable;
import com.bestinfo.gambler.entity.ControlNumber;
import com.bestinfo.gambler.all.RhClientProperties;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 *
 * @author chenliping
 */
public class BetNumberXmlControlInfo {

    private static final Logger logger = Logger.getLogger(BetNumberXmlControlInfo.class);

    /**
     * 解析xml控制文件
     *
     * @param controlfilename 带路径
     * @return
     * @throws Exception
     */
    private static ArrayList<ControlNumber> parseXML(String controlfilename) throws Exception {
        SAXReader reader = new SAXReader();
        Document doc = reader.read(new File(controlfilename));
        Element root = doc.getRootElement();  //获取根节点
        ArrayList<ControlNumber> albn = new ArrayList<ControlNumber>();
        for (Iterator iter = root.elementIterator(); iter.hasNext();) {
            Element inner = (Element) iter.next();
            String name = inner.getName();
            String gameid = inner.attribute("id").getValue();
//            System.out.println("name:" + name + "\tgameid:" + gameid);
            for (Iterator Iplaytype = inner.elementIterator(); Iplaytype.hasNext();) {
                Element Eplaytype = (Element) Iplaytype.next();
                String Eplayname = Eplaytype.getName();
                String playid = Eplaytype.attribute("id").getValue();
//                System.out.println("Eplayname:" + Eplayname + "\tplayid:" + playid);
                for (Iterator Ibetmod = Eplaytype.elementIterator(); Ibetmod.hasNext();) {
                    Element Ebetmod = (Element) Ibetmod.next();
                    String Ebetmodname = Ebetmod.getName();
                    String betmod = Ebetmod.attribute("id").getValue();
//                    System.out.println("Ebetmodname:" + Ebetmodname + "\tbetmod:" + betmod);
                    String filename = Ebetmod.attribute("filename").getValue();
                    if (filename.isEmpty()) {
                        continue;
                    }
                    String startline = Ebetmod.attribute("startline").getValue();
                    String endline = Ebetmod.attribute("endline").getValue();

//                        System.out.println("Efilename: " + Efilename + "\tfilename: " + filename+"\tstartline: "+startline+"\tendline: "+endline);
                    ControlNumber tn = new ControlNumber();
                    tn.setGameid(gameid.length() == 0 ? 0 : Integer.parseInt(gameid));
                    tn.setPlaytype(playid.length() == 0 ? 0 : Integer.parseInt(playid));
                    tn.setBetmod(betmod.length() == 0 ? 0 : Integer.parseInt(betmod));
                    tn.setFilename(filename);
                    tn.setStartline(startline.length() == 0 ? 0 : Integer.parseInt(startline));
                    tn.setEndline(endline.length() == 0 ? 0 : Integer.parseInt(endline));
                    albn.add(tn);
                }
            }
        }

//        for (ControlNumber ticketNumber : albn) {
//            System.out.println("");
//            System.out.println(ticketNumber.getGameid());
//            System.out.println(ticketNumber.getPlaytype());
//            System.out.println(ticketNumber.getBetmod());
//            System.out.println(ticketNumber.getFilename());
//            System.out.println(ticketNumber.getStartline());
//            System.out.println(ticketNumber.getEndline());
//        }
        return albn;
    }

    /**
     * 得到指定xml控制文件或者全部xml控制文件中的号码信息
     *
     * @param controlfilename 带路径
     * @return
     * @throws Exception
     */
    private ArrayList<ControlNumber> getGameControlInfo(String controlfilename) throws Exception {
        ArrayList<String> filelist = new ArrayList<String>();
        if (controlfilename == null || controlfilename.isEmpty()) {//如果没有给控制文件信息，则默认取所有控制文件中的控制信息            
            CommTool.refreshFileList(StaticVariable.NUMBERCONTROLDIRECTORY, filelist);
        } else {
            if (!new File(controlfilename).exists()) {
                logger.error("文件不存在！");
                return null;
            }
            filelist.add(controlfilename);
        }
        ArrayList<ControlNumber> altn = new ArrayList<ControlNumber>();//所有控制信息
        for (String filename : filelist) {
            ArrayList<ControlNumber> acn = parseXML(filename);
            altn.addAll(acn);
        }
        return altn;
    }

    /**
     * 获取需求的游戏,游戏玩法,游戏投注方式的号码所在地信息
     *
     * @param gameid
     * @param playid
     * @param betmod
     * @param controlfilename 带路径
     * @return
     * @throws Exception
     */
    private ArrayList<ControlNumber> getGameControlInfo(int gameid, int playid, int betmod, String controlfilename) throws Exception {
        ArrayList<ControlNumber> altn = getGameControlInfo(controlfilename);
        if (altn == null || altn.isEmpty()) {
            logger.error("没有号码信息！");
            return null;
        }
        ArrayList<ControlNumber> altn1 = new ArrayList<ControlNumber>();//存放结果控制信息
        for (ControlNumber ticketNumber : altn) {
            if (ticketNumber.getGameid() != gameid) {
                continue;
            }
            if (ticketNumber.getPlaytype() != playid) {
                continue;
            }
            if (ticketNumber.getBetmod() != betmod) {
                continue;
            }
            altn1.add(ticketNumber);
        }
        return altn1;
    }

    /**
     * 获取需求的游戏,玩法的号码所在地信息
     *
     * @param gameid
     * @param playid
     * @return
     * @throws Exception
     */
    private ArrayList<ControlNumber> getGameControlInfo(int gameid, int playid, String controlfilename) throws Exception {
        ArrayList<ControlNumber> altn = getGameControlInfo(controlfilename);
        if (altn == null || altn.isEmpty()) {
            logger.error("没有号码信息！");
            return null;
        }
        ArrayList<ControlNumber> altn1 = new ArrayList<ControlNumber>();
        for (ControlNumber ticketNumber : altn) {
            if (ticketNumber.getGameid() == gameid && ticketNumber.getPlaytype() == playid) {
                altn1.add(ticketNumber);
            }
        }
        return altn1;
    }

    /**
     * 获取需求的游戏的所有号码所在地信息
     *
     * @param gameid
     * @param playid
     * @return
     * @throws Exception
     */
    private ArrayList<ControlNumber> getGameControlInfo(int gameid, String controlfilename) throws Exception {
        ArrayList<ControlNumber> altn = getGameControlInfo(controlfilename);
        if (altn == null || altn.isEmpty()) {
            logger.error("没有号码信息！");
            return null;
        }
        ArrayList<ControlNumber> altn1 = new ArrayList<ControlNumber>();
        for (ControlNumber ticketNumber : altn) {
            if (ticketNumber.getGameid() == gameid) {
                altn1.add(ticketNumber);
            }
        }
        return altn1;
    }

    /**
     * 查询已生成的游戏号码票数的控制信息
     *
     * @param gameid
     * @param playid
     * @param betmod
     * @param controlfilename 带路径
     * @return
     * @throws Exception
     */
    public ArrayList<ControlNumber> selectticketnum(int gameid, int playid, int betmod, String controlfilename) {
        ArrayList<ControlNumber> tn = null;
        try {
            if (gameid == 0) {
                tn = getGameControlInfo(controlfilename);
            } else if (gameid != 0 && playid == 0 && betmod == 0) { //指定游戏全部玩法
                tn = getGameControlInfo(gameid, controlfilename);
            } else if (gameid != 0 && playid != 0 && betmod == 0) {//指定游戏的指定玩法
                tn = getGameControlInfo(gameid, playid, controlfilename);
            } else if (gameid != 0 && playid != 0 && betmod != 0) {//指定游戏的指定玩法指定投注方式            
                tn = getGameControlInfo(gameid, playid, betmod, controlfilename);
            }
            if (tn == null) {
                logger.error("游戏参数错误！获取不到号码控制文件信息！");
                return null;
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return tn;
    }

    // 复制模板文件为新文件
    private void copyFile(File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            logger.info("新建文件输入流并对它进行缓冲");
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(RhClientProperties.class.getResourceAsStream(StaticVariable.NUMBERMODELFILE));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
            logger.info("缓冲数组");
            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            logger.info("len");
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            logger.info("刷新此缓冲的输出流");
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            logger.info("关闭流");
            // 关闭流
            if (inBuff != null) {
                inBuff.close();
                logger.info("关闭流inBuff.close();");
            }
            if (outBuff != null) {
                outBuff.close();
                logger.info("关闭流outBuff.close();");
            }
            return;
        }
    }

    /**
     * 修改某游戏某玩法的某投注方式的文件信息,不保留原来文件信息
     *
     * @param game_id
     * @param play_id
     * @param bet_mod
     * @param file_name
     * @param start_line
     * @param end_line
     * @param controlfilename xml控制文件名，不带路径
     * @throws Exception
     */
    public synchronized void modifyxmlfile(int game_id, int play_id, int bet_mod, String file_name, int start_line, int end_line, String controlfilename) throws Exception {
        String filename = StaticVariable.NUMBERCONTROLDIRECTORY + controlfilename;
        File controlfile = new File(filename);
        if (!controlfile.exists()) {//文件不存在，则拷贝模板文件，新建文件
            logger.error("文件不存在，则拷贝模板文件，新建文件");
            copyFile(controlfile);
            logger.info("copyFile(controlfile)");
        }//控制xml文件存在，则会删除号码文件
        SAXReader reader = new SAXReader();
        Document doc = reader.read(controlfile);
        logger.info("reader.read(controlfile);");
        Element root = doc.getRootElement();  //获取根节点
        for (Iterator iter = root.elementIterator(); iter.hasNext();) {
            Element inner = (Element) iter.next();
            String name = inner.getName();
            int gameid = Integer.parseInt(inner.attribute("id").getValue());
//            System.out.println("name:" + name + "\tgameid:" + gameid);
            if (gameid != game_id) {
                continue;
            }
            logger.info("111111111111");
            for (Iterator Iplaytype = inner.elementIterator(); Iplaytype.hasNext();) {
                Element Eplaytype = (Element) Iplaytype.next();
                String Eplayname = Eplaytype.getName();
                int playid = Integer.parseInt(Eplaytype.attribute("id").getValue());
//                System.out.println("Eplayname:" + Eplayname + "\tplayid:" + playid);
                if (playid != play_id) {
                    continue;
                }
                for (Iterator Ibetmod = Eplaytype.elementIterator(); Ibetmod.hasNext();) {
                    Element Ebetmod = (Element) Ibetmod.next();
                    String Ebetmodname = Ebetmod.getName();
                    int betmod = Integer.parseInt(Ebetmod.attribute("id").getValue());
//                    System.out.println("Ebetmodname:" + Ebetmodname + "\tbetmod:" + betmod);
                    if (betmod != bet_mod) {
                        continue;
                    }
                    //删除原有的文件
                    String filepath = Ebetmod.attribute("filename").getValue();
                    File file = new File(filepath);
                    if (file.isFile() && file.exists()) {
                        file.delete();
                    }
                    Ebetmod.attribute("filename").setValue(file_name);
                    Ebetmod.attribute("startline").setValue(Integer.toString(start_line));
                    Ebetmod.attribute("endline").setValue(Integer.toString(end_line));
                }
            }
        }

        XMLWriter xmlwriter = new XMLWriter(new FileWriter(controlfile));
        xmlwriter.write(doc);
        xmlwriter.close();
    }

    public static void main(String[] args) throws Exception {
//        new BetNumberXmlControlInfo().parseXML("number\\control\\model.xml");
//        new BetNumberXmlControlInfo().modifyxmlfile(1,1,1,"4",1,10,"");
    }
}
