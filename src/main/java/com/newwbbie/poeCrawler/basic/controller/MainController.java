package com.newwbbie.poeCrawler.basic.controller;

import com.newwbbie.poeCrawler.Crawler.pipeline.UuPipeline;
import com.newwbbie.poeCrawler.Crawler.processor.UUPageProcessor;
import com.newwbbie.poeCrawler.basic.service.MainService;
import com.newwbbie.poeCrawler.util.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import us.codecraft.webmagic.Spider;

import javax.annotation.Resource;
import java.util.*;

@Controller
public class MainController {

    @Resource
    private MainService mainService;

    @Qualifier("UuPipeline")
    @Resource
    UuPipeline uuPipeline;

    @Resource
    private JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String from;

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
    @Scheduled(cron = "0 0/1 * * * ?")
    public void startUU () {
        Spider.create(new UUPageProcessor())
                .addUrl("http://www.uu898.com/newTrade.aspx?gm=385&area=2819&srv=44292")
                .addPipeline(uuPipeline)
                .thread(10)
                .run();

        List<Map<String, String>> list = mainService.getEInfo();
        float now = Float.parseFloat(list.get(0).get("price"));
        float sum = 0;
        for (Map<String, String> stringStringMap : list) {
            sum += Float.parseFloat(stringStringMap.get("price"));
        }
        System.out.println("now = " + now);
        System.out.println("avg = " + sum/10);
        if (now < sum / 10 - 0.3) {
            String to = "1098160709@qq.com";
            String title = "崇高石跳楼大甩卖";
            String context = "崇高石现价 " + now + " 元/个！(最近均价： " + sum/10 + " 元/个)\n点击下方链接购买：\nhttp://www.uu898.com/newTrade.aspx?gm=385&area=2819&srv=44292";
            MyUtil.sendEmail(sender, from, to, title, context);
        }
    }
}
