package com.plant.database.model.bean;

/**
 * NoteFormNoteImage
 *
 * @author chenjingyu
 * @date 2020/5/15
 */
public class NoteFormNoteImage {

    private String noteFormNoteImageId;

    private String noteFormId;

    private String noteImageId;

    private String caption;

    /**
     * -1代表null
     */
    private Integer displayOrder;

    public String getNoteFormNoteImageId() {
        return noteFormNoteImageId;
    }

    public void setNoteFormNoteImageId(String noteFormNoteImageId) {
        this.noteFormNoteImageId = noteFormNoteImageId;
    }

    public String getNoteFormId() {
        return noteFormId;
    }

    public void setNoteFormId(String noteFormId) {
        this.noteFormId = noteFormId;
    }

    public String getNoteImageId() {
        return noteImageId;
    }

    public void setNoteImageId(String noteImageId) {
        this.noteImageId = noteImageId;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
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
