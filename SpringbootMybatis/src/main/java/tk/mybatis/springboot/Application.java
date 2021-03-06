package tk.mybatis.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
/***
 * 
 * @author Administrator
 *
 */
@Controller
@EnableWebMvc
@SpringBootApplication
@MapperScan(basePackages = {"tk.mybatis.springboot.mapper","tk.mybatis.springboot.dao.gamb","tk.mybatis.springboot.dao.term","tk.mybatis.springboot.dao.vani"})
public class Application extends WebMvcConfigurerAdapter {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping("/")
    String home() {
        return "redirect:countries";
    }
}
