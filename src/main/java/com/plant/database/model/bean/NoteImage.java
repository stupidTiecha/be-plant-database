package com.plant.database.model.bean;

import java.util.Date;

/**
 * NoteImage
 *
 * @author 18044703
 * @date 2020/5/15
 */
public class NoteImage {

    private String noteImageId;

    private String noteId;

    private String externalId;

    private Date addedDate;

    public String getNoteImageId() {
        return noteImageId;
    }

    public void setNoteImageId(String noteImageId) {
        this.noteImageId = noteImageId;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
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
}
