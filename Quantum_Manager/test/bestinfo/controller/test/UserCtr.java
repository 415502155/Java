package bestinfo.controller.test;

import com.bestinfo.bean.business.DealerInfo;
import com.bestinfo.bean.business.DealerPrivilege;
import com.bestinfo.bean.game.GameDrawInfo;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * spring mvc与jsp通过ajax传值测试
 *
 * @author chenliping
 */
@Controller("userCtr")
@RequestMapping("testUser")
public class UserCtr {
    
//    @RequestMapping(value = "/index")
//    public String index(HttpServletRequest request, Model resModel) {
//        return "/game/gameDrawinfoAddTest";
//    }
//   
//
//    @RequestMapping(value = "/testlogin")
//    public String toLogin(HttpServletRequest request, Model resModel) {
//        return "/test/newjsp";
//    }

//    // ajax请求一
//    @RequestMapping("addUser")
//    public void addUser(User user, HttpServletRequest request, HttpServletResponse response) {
//        System.out.println("过来了");
//        String result = "{\"name\":\"" + user.getName() + "\"}";
//        PrintWriter out = null;
//        System.out.println(result);
//        response.setContentType("application/json");
//        try {
//            out = response.getWriter();
//            out.write(result);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @RequestMapping("testJson")
//    @ResponseBody
//    public Map<String, Object> testJson() {
//        System.out.println("testJson");
//        Map<String, Object> modelMap = new HashMap<String, Object>();
//        modelMap.put("name", "lfd");
//        modelMap.put("age", "20");
//        return modelMap;
//    }
    
//    @RequestMapping(value = "/modify", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> addDealerInfo(HttpServletRequest request, Model resModel, GameDrawInfo df) {
//        System.out.println("come in");
//        int re = 0;
//        Map<String, Object> resMap = new HashMap<String, Object>();
//        if (re == -1) {
//            resMap.put("result", "代销商失败");
//        } else if (re == -2) {
//            resMap.put("result", "代销商特权失败");
//        } else if (re == -3) {
//            resMap.put("result", "代销商失败");
//        } else if (re == -4) {
//            resMap.put("result", "代销商特权失败");
//        } else if (re == 0) {
//            resMap.put("result", "注册成功");
//        }
//        return resMap;
//    }
}
