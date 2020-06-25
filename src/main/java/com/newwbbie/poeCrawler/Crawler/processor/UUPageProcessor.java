package com.newwbbie.poeCrawler.Crawler.processor;

import com.newwbbie.poeCrawler.Crawler.model.Stone;
import com.newwbbie.poeCrawler.Crawler.pipeline.UuPipeline;
import com.newwbbie.poeCrawler.basic.dao.MainDao;
import org.jsoup.Jsoup;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UUPageProcessor implements PageProcessor {

    // UU S12赛季
    private String url = "http://www.uu898.com/newTrade.aspx?gm=385&area=2819&srv=44292";
    private Site site = Site.me()
            .setDomain("uu898.com")
            .setSleepTime(100)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
    @Resource
    private MainDao mainDao;

    @Override
    public void process(Page page) {

        if (!page.getUrl().toString().equals(url)) {
            Selectable stone = page.getHtml().$("li:nth-child(4) > h6:nth-child(1) > span:nth-child(2)");
            String html = stone.toString();
            if (html == null) {
                page.setSkip(true);
            } else {
                String text = Jsoup.parse(html).text();
                page.putField("name", text.split("/")[1]);
                page.putField("price", text.split("/")[0].replace("元", ""));
                Date date = new Date();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                page.putField("date", df.format(date));
                if (page.getResultItems().get("name") == null) {
                    page.setSkip(true);
                }
            }
        } else {
            List<String> list = page.getHtml().$(".fl_list > li > a").all();
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).indexOf("混沌石") > 0 ||
                        list.get(i).indexOf("崇高石") > 0 ||
                        list.get(i).indexOf("链接石") > 0 ||
                        list.get(i).indexOf("幻色石") > 0 ||
                        list.get(i).indexOf("工匠石") > 0 ||
                        list.get(i).indexOf("普兰德斯金币") > 0 ||
                        list.get(i).indexOf("宝石匠的棱镜") > 0 ||
                        list.get(i).indexOf("玻璃弹珠") > 0 ||
                        list.get(i).indexOf("点金石") > 0 ||
                        list.get(i).indexOf("富豪石") > 0 ||
                        list.get(i).indexOf("改造石") > 0 ||
                        list.get(i).indexOf("后悔石") > 0 ||
                        list.get(i).indexOf("机会石") > 0 ||
                        list.get(i).indexOf("卡兰德的魔镜") > 0 ||
                        list.get(i).indexOf("磨刀石") > 0 ||
                        list.get(i).indexOf("神圣石") > 0 ||
                        list.get(i).indexOf("蜕变石") > 0 ||
                        list.get(i).indexOf("瓦尔宝珠") > 0 ||
                        list.get(i).indexOf("增幅石") > 0 ||
                        list.get(i).indexOf("制图钉") > 0 ||
                        list.get(i).indexOf("重铸石") > 0 ||
                        list.get(i).indexOf("祝福石") > 0 ||
                        list.get(i).indexOf("护甲片") > 0 ||
                        list.get(i).indexOf("先驱石") > 0 ||
                        list.get(i).indexOf("制箱岩") > 0 ||
                        list.get(i).indexOf("远古石") > 0 ||
                        list.get(i).indexOf("六分仪") > 0 ||
                        list.get(i).indexOf("高阶点金石") > 0 ||
                        list.get(i).indexOf("银币") > 0 ||
                        list.get(i).indexOf("卡兰德的魔镜碎片") > 0 ||
                        list.get(i).indexOf("平行石") > 0 ||
                        list.get(i).indexOf("剥离石") > 0
                    ) {
                        String url = list.get(i).split("\"")[1];
                        page.addTargetRequest(url);
                    }
                }
            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {

        Spider.create(new UUPageProcessor())
                .addUrl("http://www.uu898.com/newTrade.aspx?gm=385&area=2819&srv=44292")
                .addPipeline(new ConsolePipeline())
                .thread(5)
                .run();
    }
}
