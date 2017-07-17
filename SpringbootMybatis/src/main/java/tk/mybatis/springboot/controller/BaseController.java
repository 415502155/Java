package tk.mybatis.springboot.controller;

import com.alibaba.fastjson.JSONObject;

public abstract class BaseController {

    public abstract JSONObject excute(JSONObject json);
}
