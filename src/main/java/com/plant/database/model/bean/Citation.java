package com.plant.database.model.bean;

/**
 * Citation
 *
 * @author 18044703
 * @date 2020/5/15
 */
public class Citation {

    private String citationId;

    private String noteFormId;

    private String linkClass;

    /**
     * -1 = null
     */
    private Integer brokenLink;

    public String getCitationId() {
        return citationId;
    }

    public void setCitationId(String citationId) {
        this.citationId = citationId;
    }

    public String getNoteFormId() {
        return noteFormId;
    }

    public void setNoteFormId(String noteFormId) {
        this.noteFormId = noteFormId;
    }

    public String getLinkClass() {
        return linkClass;
    }

    public void setLinkClass(String linkClass) {
        this.linkClass = linkClass;
    }

    public Integer getBrokenLink() {

        return brokenLink == null ? -1 : brokenLink;
    }

    public void setBrokenLink(Integer brokenLink) {
        if (null == brokenLink){
            this.brokenLink = -1;
            return;
        }
        this.brokenLink = brokenLink;
    }
}
