/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.util.h5.monitor;

import java.text.SimpleDateFormat;
import java.util.Date;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 *
 * @author Administrator
 */
    public class JsonDateValueProcessor implements JsonValueProcessor {

        private final String format = "yyyy-MM-dd HH:mm:ss";

        public Object processArrayValue(Object value, JsonConfig config) {
            return process(value);
        }

        public Object processObjectValue(String key, Object value, JsonConfig config) {
            return process(value);
        }

        private Object process(Object value) {
            if (value instanceof Date) {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                return sdf.format(value);
            }
            return "";
        }
    }
