package com.bestinfo.util;

import com.bestinfo.bean.game.GameInfo;
import java.util.Comparator;

/**
 * 游戏信息比较器，按游戏ID排序
 *
 * @author hhhh
 */
public class ComparatorGameInfo implements Comparator {

    public int compare(Object arg0, Object arg1) {
        GameInfo gi0 = (GameInfo) arg0;
        GameInfo gi1 = (GameInfo) arg1;

        return gi0.getGame_id().compareTo(gi1.getGame_id());
    }

}
