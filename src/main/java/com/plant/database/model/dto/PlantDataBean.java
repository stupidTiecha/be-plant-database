package com.plant.database.model.dto;

import com.alibaba.fastjson.JSONObject;

/**
 * PlantDataBean
 *
 * @author chenjingyu
 * @date 2020/5/22
 */
public class PlantDataBean {

    private String noteFormId;

    private String content;

    private String title;

    private String noteClass;

    private String itemType;

    private Integer displayOrder;

    private JSONObject noteImages;

    public String getNoteFormId() {
        return noteFormId;
    }

    public void setNoteFormId(String noteFormId) {
        this.noteFormId = noteFormId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNoteClass() {
        return noteClass;
    }

    public void setNoteClass(String noteClass) {
        this.noteClass = noteClass;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public JSONObject getNoteImages() {
        return noteImages;
    }

    public void setNoteImages(JSONObject noteImages) {
        this.noteImages = noteImages;
    }
}
