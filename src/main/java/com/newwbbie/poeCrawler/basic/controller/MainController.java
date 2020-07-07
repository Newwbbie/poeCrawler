package com.newwbbie.poeCrawler.basic.controller;

import com.newwbbie.poeCrawler.Crawler.pipeline.UuPipeline;
import com.newwbbie.poeCrawler.Crawler.processor.UUPageProcessor;
import com.newwbbie.poeCrawler.basic.service.MainService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import us.codecraft.webmagic.Spider;

import javax.annotation.Resource;
import java.util.Timer;
import java.util.TimerTask;

@Controller
public class MainController {

    @Resource
    private MainService mainService;
    @Qualifier("UuPipeline")
    @Resource
    UuPipeline uuPipeline;

    @RequestMapping("/welcome")
    public String welcome () {
        return "welcome";
    }

    @ResponseBody
    @RequestMapping("/getInfo")
    public String getInfo () {
        return mainService.getInfo();
    }

    @RequestMapping("/startUU")
//    @Scheduled(cron = "0 0/1 * * * ?")
    public void startUU () {
        Spider.create(new UUPageProcessor())
                .addUrl("http://www.uu898.com/newTrade.aspx?gm=385&area=2819&srv=44292")
                .addPipeline(uuPipeline)
                .thread(10)
                .run();
    }
}
