package com.plant.database.model.bean;

import java.util.Date;

/**
 * Note
 *
 * @author 18044703
 * @date 2020/5/15
 */
public class Note {

    private String noteId;

    private String itemId;

    private String noteTypeId;

    private Date addedDate;

    /**
     * -1 = null
     */
    private Integer sequence;

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getNoteTypeId() {
        return noteTypeId;
    }

    public void setNoteTypeId(String noteTypeId) {
        this.noteTypeId = noteTypeId;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public Integer getSequence() {
        return sequence == null ? -1 : sequence;
    }

    public void setSequence(Integer sequence) {
        if (null == sequence) {
            this.sequence = -1;
            return;
        }
        this.sequence = sequence;
    }
}
