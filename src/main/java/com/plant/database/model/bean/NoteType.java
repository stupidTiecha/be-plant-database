package com.plant.database.model.bean;

/**
 * NoteType
 *
 * @author chenjingyu
 * @date 2020/5/15
 */
public class NoteType {

    private String noteTypeId;

    private String itemTypeId;

    private String parentNoteTypeId;

    private String noteClassId;

    private String title;

    /**
     * -1代表null
     */
    private Integer displayOrder;

    public String getNoteTypeId() {
        return noteTypeId;
    }

    public void setNoteTypeId(String noteTypeId) {
        this.noteTypeId = noteTypeId;
    }

    public String getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(String itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public String getParentNoteTypeId() {
        return parentNoteTypeId;
    }

    public void setParentNoteTypeId(String parentNoteTypeId) {
        this.parentNoteTypeId = parentNoteTypeId;
    }

    public String getNoteClassId() {
        return noteClassId;
    }

    public void setNoteClassId(String noteClassId) {
        this.noteClassId = noteClassId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
