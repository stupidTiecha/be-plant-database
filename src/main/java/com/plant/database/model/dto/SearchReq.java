package com.plant.database.model.dto;

import com.alibaba.fastjson.JSONObject;

/**
 * SearchReq
 *
 * @author chenjingyu
 * @date 2020/5/20
 */
public class SearchReq {

    private Integer searchType;

    private Integer searchScope;

    private JSONObject searchContent;

    public Integer getSearchType() {
        return searchType;
    }

    public void setSearchType(Integer searchType) {
        this.searchType = searchType;
    }

    public JSONObject getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(JSONObject searchContent) {
        this.searchContent = searchContent;
    }

    public Integer getSearchScope() {
        return searchScope;
    }

    public void setSearchScope(Integer searchScope) {
        this.searchScope = searchScope;
    }
}
