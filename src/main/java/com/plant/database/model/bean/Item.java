package com.plant.database.model.bean;

import java.util.Date;

/**
 * Item
 *
 * @author 18044703
 * @date 2020/5/15
 */
public class Item {

    private String itemId;

    private String itemTypeId;

    private String displayTitle;

    private String externalId;

    private Date addedDate;

    private Date updatedDate;

    /**
     * -1 = null
     */
    private Integer isSuppressed;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(String itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public String getDisplayTitle() {
        return displayTitle;
    }

    public void setDisplayTitle(String displayTitle) {
        this.displayTitle = displayTitle;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getIsSuppressed() {
        return isSuppressed == null ? -1 : isSuppressed;
    }

    public void setIsSuppressed(Integer isSuppressed) {
        if (null == isSuppressed) {
            this.isSuppressed = -1;
            return;
        }
        this.isSuppressed = isSuppressed;
    }
}
