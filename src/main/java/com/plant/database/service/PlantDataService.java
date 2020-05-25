package com.plant.database.service;

import com.alibaba.fastjson.JSONObject;
import com.plant.database.model.bean.Response;

/**
 *
 * @author 18044703
 */
public interface PlantDataService {

    /**
     * transform data to mysql from xml
     *
     * @return isSuccess
     */
    boolean transformDataToMysql();

    /**
     *  transform data to solr from xml
     *
     * @return isSuccess
     */
    boolean transformDataToSolr();

    /**
     * search by text
     * @param searchParams  param
     * @return
     */
    Response searchAll(JSONObject searchParams);

    /**
     * search by topic
     * @param searchParams  param
     * @return
     */
    Response searchByTopic(JSONObject searchParams);

    /**
     * search by field
     * @param searchParams  param
     * @return
     */
    Response searchByField(JSONObject searchParams);

    /**
     * get detail by item_id
     * @param itemId    item_id
     * @return
     */
    Response getPlant(String itemId);
}
