package com.newwbbie.demo.Controller;

import com.newwbbie.demo.Service.MainService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class MainController {

    @Resource
    private MainService mainService;

    @RequestMapping("/welcome")
    public String welcome () {
        return "welcome";
    }
}
