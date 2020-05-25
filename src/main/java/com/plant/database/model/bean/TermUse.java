package com.plant.database.model.bean;

/**
 * TermUse
 *
 * @author 18044703
 * @date 2020/5/15
 */
public class TermUse {

    private String termUseId;

    private String termId;

    private String itemId;

    public String getTermUseId() {
        return termUseId;
    }

    public void setTermUseId(String termUseId) {
        this.termUseId = termUseId;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
