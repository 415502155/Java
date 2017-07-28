/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c;

import com.bestinfo.protocols.message.AppHeader;
import com.bestinfo.protocols.util.AppHeaderUtil;
import com.bestinfo.service.imple.FileDownServiceImpl;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 *
 * @author jone
 */
public class NewClass {

    private static final Logger logger = Logger.getLogger(NewClass.class);

    public static void main(String[] arg) {
        String s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><openprize><isKeno>0</isKeno><gameId>1</gameId><gameName>南粤风采36选7</gameName><gameCode>C736</gameCode><drawId>87</drawId><drawName>2015087</drawName><kdrawId>0</kdrawId><kdrawName>0</kdrawName><cashBegin>20151218</cashBegin><cashEnd>20160217</cashEnd><betTotalMoney>0</betTotalMoney><betLotteryMoney>0</betLotteryMoney><openId id=\"1\"><baseNo>02 20 21 23 27 04</baseNo><specialNo>05</specialNo><luckyTotalMoney>0</luckyTotalMoney><luckyList><lucky classId=\"1\" className=\"一等奖\" totalNum=\"0\" lotteryNum=\"0\" stakePrize=\"5000\"/><lucky classId=\"2\" className=\"二等奖\" totalNum=\"0\" lotteryNum=\"0\" stakePrize=\"520\"/><lucky classId=\"3\" className=\"三等奖\" totalNum=\"0\" lotteryNum=\"0\" stakePrize=\"510\"/><lucky classId=\"4\" className=\"四等奖\" totalNum=\"0\" lotteryNum=\"0\" stakePrize=\"500\"/><lucky classId=\"5\" className=\"五等奖\" totalNum=\"0\" lotteryNum=\"0\" stakePrize=\"100\"/><lucky classId=\"6\" className=\"六等奖\" totalNum=\"0\" lotteryNum=\"0\" stakePrize=\"10\"/></luckyList></openId><endPool>0</endPool><cashPeriodDay>60</cashPeriodDay></openprize>";
        String b = generateXML1(null, s);
        System.out.println("+++" + b);

    }

    public static String generateXML1(AppHeader header, String resXml) {

        Document doc = null;
        Element root = null, resultRes = null;
        Element pkgH = null, pkgC = null;
        try {
            if (resXml == null) {
                return null;

            } else {
                doc = DocumentHelper.createDocument();
                root = doc.addElement("pkg");
                if (header != null) {//有的不要头
                    pkgH = AppHeaderUtil.toXML(header);
                    root.add(pkgH);
                }
                pkgC = root.addElement("pkgC");

                Document doc1 = DocumentHelper.parseText(resXml);
                Element root1 = doc1.getRootElement();
                Element openPrize = pkgC.addElement("openPrize");
                Element prize = pkgC.addElement("prize ");

                // List<Element> list = classed.elements();
                Element iters = root1;
                for (Iterator iter = iters.elementIterator(); iter.hasNext();) {
                    Element el = (Element) iter.next();
                    String name = el.getName();
                    if (name.equals("gameName")) {
                        openPrize.addAttribute("gameName", el.getTextTrim());
                    } else if (name.equals("drawName")) {
                        openPrize.addAttribute("drawName", el.getTextTrim());
                    } else if (name.equals("kdrawName")) {
                        openPrize.addAttribute("kdrawName", el.getTextTrim());
                    } else if (name.equals("cashBegin")) {
                        openPrize.addAttribute("startTime", el.getTextTrim());
                    } else if (name.equals("cashEnd")) {
                        openPrize.addAttribute("stopTime", el.getTextTrim());
                    } else if (name.equals("betTotalMoney")) {
                        openPrize.addAttribute("totalMoney", el.getTextTrim());
                    } else if (name.equals("endPool")) {
                        openPrize.addAttribute("jackpot", el.getTextTrim());
                    } else if (name.equals("openId")) {
                        String text = el.attribute("id").getText();
                        prize.addAttribute("totalTimes", text);

                        Element openId = root1.element("openId");
                        List<Element> openList = openId.elements();
                        logger.info("openList is: "+openList.size());
                        for (int i = 1; i <= Integer.valueOf(text); i++) {
                            Element prizeItem = prize.addElement("prizeItem");
                            for (Iterator iterss = openId.elementIterator(); iterss.hasNext();) {
                                logger.info("+++++++++++++++++");
                                Element ele = (Element) iterss.next();
                                String names = ele.getName();

                                prizeItem.addAttribute("times", i + "");
                                if (names.equals("baseNo")) {
                                    prizeItem.addAttribute("bonusCode", ele.getTextTrim());
                                } else if (names.equals("specialNo")) {
                                    prizeItem.addAttribute("classNum", ele.getTextTrim());
                                } else if (names.equals("luckyList")) {
                               // prizeItem.addAttribute("classNum", ele.getTextTrim());

                                    //  prizeItem.addAttribute("times", ele.getTextTrim());
                                    Element luckyList = openId.element("luckyList");
                                    List<Element> list1 = luckyList.elements();
                                    logger.info("*******" + list1.size());
                                    for (Element eles : list1) {
                                       // logger.info("........." + eles.attributeValue("className"));

//                                    String namesd = eles.getName();
//                                    if (namesd.equals("lucky")) {
                                        String className = eles.attribute("className").getText();
                                       // logger.info("classNameclassNameclassName " + className);
                                        String totalNum = eles.attribute("totalNum").getText();
                                        String stakePrize = eles.attribute("stakePrize").getText();
                                        Element classed = prizeItem.addElement("class");
                                        classed.addAttribute("className", className);
                                        classed.addAttribute("bonusMoney", Integer.valueOf(stakePrize) * Integer.valueOf(totalNum) + "");
                                        classed.addAttribute("bonusNum", totalNum);
                                        //  }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            logger.info(("doc.asXML() is: " + doc.asXML()));
            return doc.asXML();
        } catch (Exception ex) {
            logger.error("generate xml error:", ex);
            return null;
        }
    }

}
