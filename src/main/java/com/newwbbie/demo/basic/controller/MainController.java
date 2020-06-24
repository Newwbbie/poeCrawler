package com.newwbbie.demo.basic.controller;

import com.newwbbie.demo.basic.service.MainService;
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

    @ResponseBody
    @RequestMapping("/getInfo")
    public String getInfo () {
        return mainService.getInfo();
    }
}
