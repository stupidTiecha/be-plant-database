package com.plant.database.model.bean;


/**
 * NoteForm
 *
 * @author chenjingyu
 * @date 2020/5/15
 */
public class NoteForm {

    private String noteFormId;

    private String noteId;

    private String content;

    public String getNoteFormId() {
        return noteFormId;
    }

    public void setNoteFormId(String noteFormId) {
        this.noteFormId = noteFormId;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
