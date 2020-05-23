package com.plant.database.service;

import com.alibaba.fastjson.JSONObject;
import com.plant.database.model.bean.Response;

public interface PlantDataService {

    /**
     * 将xml中数据解析到数据库中
     *
     * @return 解析是否成功
     */
    boolean transformDataToMysql();

    /**
     * 将xml中数据解析到solr中
     *
     * @return 解析是否成功
     */
    boolean transformDataToSolr();

    /**
     * 全文搜索
     * @param searchParams  参数
     * @return  响应
     */
    Response searchAll(JSONObject searchParams);

    /**
     * 通过topic查询
     * @param searchParams  参数
     * @return  响应
     */
    Response searchByTopic(JSONObject searchParams);

    /**
     * 通过field查询
     * @param searchParams  参数
     * @return  响应
     */
    Response searchByField(JSONObject searchParams);

    /**
     * 通过item_id 查询数据
     * @param itemId    item_id
     * @return  响应
     */
    Response getPlant(String itemId);
}
