/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.protocols.xml.client;

import com.bestinfo.protocols.util.AppMessageUtil;

/**
 * Title:协议解析组装类实现的工厂接口<br>
 * Description:根据协议分类选用解析组装工具类.通过各类协议接口获取该类的方法的定义，然后
 * 调用具体实现类，可以有多个具体实现类<br>
 * Copyright:Copyright(c) 2015<br>
 *
 * @version 1.0
 * @author zhen
 */
public interface XmlParserFactory {
    
    public AppMessageUtil getAppClientXF(String className);//以名称通过反射生成
    public AppMessageUtil getAppClientXF(Class clazz);//以类通过反射来生成
    public AppMessageUtil getAppClientXF(int actionId);//以编号来区分请求的xml
}

