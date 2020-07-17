package com.newwbbie.poeCrawler.basic.service.serviceImpl;

import com.newwbbie.poeCrawler.basic.dao.MainDao;
import com.newwbbie.poeCrawler.basic.service.MainService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("MainService")
public class MainServiceImpl implements MainService {

    @Resource
    private MainDao mainDao;

    @Override
    public String getInfo() {
        return mainDao.getInfo();
    }

    @Override
    public List<Map<String, String>> getEInfo() {
        return mainDao.getEInfo();
    }
}
