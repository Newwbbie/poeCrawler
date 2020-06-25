package com.newwbbie.poeCrawler.basic.dao;

import com.newwbbie.poeCrawler.Crawler.model.Stone;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface MainDao {

    String getInfo();

    int addUU(Map<String, Object> map);
}
