package com.bestinfo.define.game;

import java.util.HashMap;
import java.util.Map;

public class ClpGameCode {

    public static final Map<Integer, String> gameCodeMap = new HashMap<Integer, String>();

    public static final Map<String, Integer> codeGameMap = new HashMap<String, Integer>();

    static {
        gameCodeMap.put(1, "10001");//双色球
        gameCodeMap.put(2, "10002");//3D
        gameCodeMap.put(3, "31006");//快三
        gameCodeMap.put(4, "10003");//七乐彩
        gameCodeMap.put(5, "31002");//天天彩选4
        gameCodeMap.put(6, "90015");//15选5
        gameCodeMap.put(7, "90016");//东方6+1
        gameCodeMap.put(8, "31003");//时时乐
        gameCodeMap.put(9, "31004");//基诺

        codeGameMap.put("10001", 1);
        codeGameMap.put("10002", 2);
        codeGameMap.put("31006", 3);
        codeGameMap.put("10003", 4);
        codeGameMap.put("31002", 5);
        codeGameMap.put("90015", 6);
        codeGameMap.put("90016", 7);
        codeGameMap.put("31003", 8);
        codeGameMap.put("31004", 9);
    }

}
