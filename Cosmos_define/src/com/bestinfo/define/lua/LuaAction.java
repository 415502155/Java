package com.bestinfo.define.lua;

import java.util.HashMap;
import java.util.Map;

public class LuaAction {

    //需要lua放行的业务，即可以忽略业务报文头里的交易时间和计数器
    public static Map<String, String> LUA_ACTION_MAP = new HashMap<String, String>();
}
