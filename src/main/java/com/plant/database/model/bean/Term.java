package com.plant.database.model.bean;

/**
 * Term
 *
 * @author 18044703
 * @date 2020/5/15
 */
public class Term {

    private String termId;

    private String vocabularyId;

    private String label;

    /**
     * -1 = null
     */
    private Integer displayOrder;

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getVocabularyId() {
        return vocabularyId;
    }

    public void setVocabularyId(String vocabularyId) {
        this.vocabularyId = vocabularyId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getDisplayOrder() {
        return displayOrder == null ? -1 : displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        if (null == displayOrder) {
            this.displayOrder = -1;
            return;
        }
        this.displayOrder = displayOrder;
    }
}
