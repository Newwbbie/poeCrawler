package com.newwbbie.poeCrawler.Crawler.pipeline;

import com.newwbbie.poeCrawler.basic.dao.MainDao;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component("UuPipeline")
public class UuPipeline implements Pipeline {

    @Resource
    MainDao mainDao;

    @Override
    public void process(ResultItems resultItems, Task task) {
        if (resultItems.get("name") != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", resultItems.get("name").toString());
            map.put("price", resultItems.get("price").toString());
            map.put("date", resultItems.get("date").toString());
            mainDao.addUU(map);
        }
    }
}