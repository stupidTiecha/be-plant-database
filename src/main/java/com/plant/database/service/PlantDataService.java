package com.plant.database.service;

public interface PlantDataService {

    /**
     * 将xml中数据解析到数据库和solr中
     *
     * @return 解析是否成功
     */
    boolean transformData();
}
