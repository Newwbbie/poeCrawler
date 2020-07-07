package com.newwbbie.poeCrawler.Crawler.processor;

import com.newwbbie.poeCrawler.Crawler.selenium.SeleniumDownloader;
import com.newwbbie.poeCrawler.Crawler.selenium.WebDriverPool;
import com.newwbbie.poeCrawler.basic.dao.MainDao;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.jsoup.Jsoup;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class YDPageProcessor implements PageProcessor {

    private String url = "https://b2b.10086.cn/b2b/main/searchNotice.html";
    private Site site;
    @Resource
    private MainDao mainDao;

    @Override
    public void process(Page page) {

        Selectable info = page.getHtml().xpath("//*[@id=\"searchResult\"]/table/tbody");

        String html = info.toString();
        if (html == null) {
            page.setSkip(true);
        } else {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-M-d");
            String date = df.format(new Date());
            JSONArray array = new JSONArray();

            for (int i = 0; i < info.$("tr").all().size(); i++) {
                String comTD = info.$("tr:nth-child(" + i + ") > td:nth-child(1)").toString();
                String typeTD = info.$("tr:nth-child(" + i + ") > td:nth-child(2)").toString();
                String titleTD = info.$("tr:nth-child(" + i + ") > td:nth-child(3)").toString();
                String timeTD = info.$("tr:nth-child(" + i + ") > td:nth-child(4)").toString();

                if (timeTD != null) {
                    String com = Jsoup.parse(comTD).text();
                    String type = Jsoup.parse(typeTD).text();
                    String title = Jsoup.parse(titleTD).text();
                    String time = Jsoup.parse(timeTD).text();
                    if (time.equals(date)) {
                        JSONObject object = new JSONObject();
                        object.put("采购需求单位", com);
                        object.put("公告类型", type);
                        object.put("标题", title);
                        object.put("时间", time);
                        array.add(object);
                    }
                }
            }
            page.putField("结果", array);
        }
    }

    @Override
    public Site getSite() {
        if (site == null) {
            site = Site.me()
                    .setDomain("b2b.10086.cn")
                    .setSleepTime(1000)
                    .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36");
        }
        return site;
    }

    public static void main(String[] args) {

        Spider.create(new YDPageProcessor()).thread(5)
                .addUrl("https://b2b.10086.cn/b2b/main/searchNotice.html")
                .addPipeline(new ConsolePipeline())
                .setDownloader(new SeleniumDownloader("C:\\Windows\\System32\\chromedriver.exe").setSleepTime(1000))
                .run();
    }
}
